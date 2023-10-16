package com.kosa.ShareTour.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.kosa.ShareTour.dto.PostingSearchDto;
//import com.kosa.ShareTour.dto.MainPostingDto;
//import com.kosa.ShareTour.dto.QMainPostingDto;
import com.kosa.ShareTour.entity.Posting;
import com.kosa.ShareTour.entity.QPosting;
import com.kosa.ShareTour.entity.QPostimage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class PostingRepositoryCustomImpl implements PostingRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public PostingRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression regDtsAfter(String searchDateType){

        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        return QPosting.posting.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("title", searchBy)){
            return QPosting.posting.title.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)){
            return QPosting.posting.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Posting> getUserPostingPage(PostingSearchDto postingSearchDto, Pageable pageable) {

        QueryResults<Posting> results = queryFactory
                .selectFrom(QPosting.posting)
                .where(regDtsAfter(postingSearchDto.getSearchDateType()),
                        searchByLike(postingSearchDto.getSearchBy(),
                                postingSearchDto.getSearchQuery()))
                .orderBy(QPosting.posting.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Posting> content = results.getResults();
        long total = results.getTotal();

//        long total = queryFactory.select(Wildcard.count).from(QPosting.posting)
//                .where(regDtsAfter(postingSearchDto.getSearchDateType()),
//                        searchByLike(postingSearchDto.getSearchBy(), postingSearchDto.getSearchQuery()))
//                .fetchOne()
//                ;

        return new PageImpl<>(content, pageable, total);
    }

}