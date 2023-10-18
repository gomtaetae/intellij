package com.kosa.ShareTour.controller;
import com.kosa.ShareTour.constant.Role;
import com.kosa.ShareTour.dto.RoleUpdateDto;
import com.kosa.ShareTour.dto.UserListDto;
import com.kosa.ShareTour.entity.Member;
import com.kosa.ShareTour.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminController {
    private final MemberService memberService;

    @GetMapping
    public String showUserList(Model model) {

        List<UserListDto> users = memberService.getOtherUsersForAdmin();
        model.addAttribute("users", users);

        RoleUpdateDto roleUpdateDto = new RoleUpdateDto();
        model.addAttribute("roleUpdateDto", roleUpdateDto);
        return "admin/userList"; // 사용자 목록을 표시할 HTML 페이지
    }
    @PostMapping
    public String userList(Model model) {
        List<Member> users = memberService.getAllUsers();
        model.addAttribute("users", users);
        RoleUpdateDto roleUpdateDto = new RoleUpdateDto();
        model.addAttribute("roleUpdateDto", roleUpdateDto);
        return "admin/userList";
    }
    @PostMapping("/update-role")
    public String updateRole(@ModelAttribute RoleUpdateDto roleUpdateDto) {
        Role newRole = Role.valueOf(roleUpdateDto.getNewRole());
        Long memberId = roleUpdateDto.getMemberId();
        memberService.updateRole(memberId, newRole, roleUpdateDto);
        return "redirect:/admin/users";
    }
}