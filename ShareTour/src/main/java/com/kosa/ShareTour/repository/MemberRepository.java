package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>{

    List<Member> findByUsername(String username);
    Member findByEmail(String email);
    Member findByNickname(String nickname);
    void deleteByNickname(String nickname);

}