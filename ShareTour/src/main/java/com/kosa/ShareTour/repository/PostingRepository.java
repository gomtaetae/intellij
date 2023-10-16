package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long>,
        QuerydslPredicateExecutor<Posting>, PostingRepositoryCustom {

    List<Posting> findByMemberNickname(String memberNickname);

    List<Posting> findByTitle(String title);

    void deleteByTitle(String title);

}