package com.kosa.ShareTour.service;

import com.kosa.ShareTour.entity.Member;
import com.kosa.ShareTour.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{

    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member){
        Member findMemberEmail = memberRepository.findByEmail(member.getEmail());
        if(findMemberEmail != null) {
            throw new IllegalStateException("이미 가입된 회원입니다(동일한 Email을 사용하는 계정 존재)");
        }

        Member findMemberNick = memberRepository.findByNickname(member.getNickname());
        if(findMemberNick != null) {
            throw new IllegalStateException("이미 사용중인 아이디 입니다");
        }

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

}