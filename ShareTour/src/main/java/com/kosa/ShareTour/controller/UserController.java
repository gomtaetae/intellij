package com.kosa.ShareTour.controller;

import com.kosa.ShareTour.dto.MemberFormDto;
import com.kosa.ShareTour.entity.Member;
import com.kosa.ShareTour.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @Autowired
    public UserController(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder =passwordEncoder;
    }

    @GetMapping("/profile/edit")
    public String editUserProfile(Model model) {
        // 현재 사용자 정보 가져오는 코드 (Spring Security를 통해)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Member member = memberService.findByEmail(email);
        var memberFormDto = MemberFormDto.of(member);
        model.addAttribute("memberFormDto", memberFormDto);

        return "member/memberProfile";
    }

    @PostMapping("/profile/edit")
    public String updateUserProfile(@ModelAttribute MemberFormDto memberFormDto, Model model) {

        String newPassword = memberFormDto.getPassword();
        if(newPassword != null && !newPassword.isEmpty()) {
            newPassword = passwordEncoder.encode(newPassword);
        }

        memberFormDto.setPassword(newPassword);

        Member updatedMember = memberService.updateMemberProfile(memberFormDto);

        if (updatedMember != null) {
            model.addAttribute("successMessage", "프로필이 성공적으로 업데이트되었습니다.");
            return "redirect:/";
        }

        model.addAttribute("errorMessage", "프로필 업데이트 중에 문제가 발생했습니다.");

        return "redirect:/user/profile/edit";
    }

}