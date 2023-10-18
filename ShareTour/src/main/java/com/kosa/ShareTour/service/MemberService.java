package com.kosa.ShareTour.service;


import com.kosa.ShareTour.constant.Role;
import com.kosa.ShareTour.dto.MemberFormDto;
import com.kosa.ShareTour.dto.RoleUpdateDto;
import com.kosa.ShareTour.dto.UserListDto;
import com.kosa.ShareTour.entity.Member;
import com.kosa.ShareTour.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다(동일한 Email을 사용하는 계정 존재)");
        }
        Member findMemberNick = memberRepository.findByNickname(member.getNickname());
        if (findMemberNick != null) {
            throw new IllegalStateException("이미 사용중인 아이디 입니다");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException(email);
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public String getCurrentLoggedInMemberNickname() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        Member member = memberRepository.findByEmail(email);
//                .orElseThrow(EntityNotFoundException::new);
        if (member == null) {
            throw new UsernameNotFoundException(email);
        }
        return member.getNickname();
    }

    //ChatGPT (일부완료)
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    //ChatGPT (일부완료)
    public Member updateMemberProfile(MemberFormDto memberFormDto) {
        // 현재 사용자 정보 가져오는 코드
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Member member = memberRepository.findByEmail(username);
        if (member == null) {
            throw new UsernameNotFoundException(username);
        }
        // 업데이트할 정보를 Member 엔티티에 설정
        member.setPassword(memberFormDto.getPassword());
        member.setImgUrl(memberFormDto.getImgUrl());
        member.setGender(memberFormDto.getGender());
        member.setPhone(memberFormDto.getPhone());
        member.setAddressMain(memberFormDto.getAddressMain());
        member.setAddressSub(memberFormDto.getAddressSub());
        // 다른 필드도 필요에 따라 업데이트
        return memberRepository.save(member);
    }

    //ChatGPT
    public List<UserListDto> getAllUsersForAdmin() {
        List<Member> memberList = memberRepository.findAll();
        return memberList.stream()
                .map(this::convertToUserListDto)
                .collect(Collectors.toList());
    }

    public List<UserListDto> getOtherUsersForAdmin() {
        String currentLoggedInMemberNickname = this.getCurrentLoggedInMemberNickname();

        List<Member> allMembers = this.getAllUsers();

        List<Member> otherMembers = allMembers.stream()
                .filter(member -> !member.getNickname().equals(this.getCurrentLoggedInMemberNickname()))
                .toList();

        return otherMembers.stream()
                .map(this::convertToUserListDto)
                .collect(Collectors.toList());
    }

    //ChatGPT
    private UserListDto convertToUserListDto(Member member) {
        UserListDto userListDto = new UserListDto();
        userListDto.setId(member.getId());
        userListDto.setName(member.getName());
        userListDto.setEmail(member.getEmail());
        userListDto.setNickname(member.getNickname());
        userListDto.setBirthday(member.getBirthday());
        userListDto.setPhone(member.getPhone());
        userListDto.setPoint(member.getPoint());
        userListDto.setRole(member.getRole());
        return userListDto;
    }

    //ChatGPT
    public List<Member> getAllUsers() {
        return memberRepository.findAll();
    }

    public void updateRole(Long id, Role newRole, RoleUpdateDto roleUpdateDto) {
        if (id == null) {
            throw new IllegalArgumentException("The given id must not be null!");
        }
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setRole(newRole);
            memberRepository.save(member);
        } else {
            throw new EntityNotFoundException("다음 Id를 가진 회원은 없습니다 : " + id);
        }
    }
}