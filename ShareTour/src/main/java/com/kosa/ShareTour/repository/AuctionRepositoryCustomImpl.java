package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.constant.AuctionStatus;
import com.kosa.ShareTour.dto.*;
import com.kosa.ShareTour.entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class AuctionRepositoryCustomImpl implements AuctionRepositoryCustom {
    //동적으로 쿼리를 생성하기 위해 JPAQueryFactory 클래스를 사용
    private JPAQueryFactory queryFactory;

    //JPAQueryFactory의 생성자로 EntityManager 객체를 넣어줌

    public AuctionRepositoryCustomImpl(EntityManager em) {

        this.queryFactory = new JPAQueryFactory(em);
    }

    //상품 판매 상태 조건이 전체(null)일 경우는 null 리턴, 결과값이 null이면 where 절에서 해당 조건은 무시됨
    private BooleanExpression searchSellStatusEq(AuctionStatus searchSellStatus) {

        //serachDateType의 값에 따라서 dateTime의 값을 이전 시간의 값으로 세팅 후 해당 시간 이후로 등록된 상품만 조회
        return searchSellStatus == null ? null : QAuction.auction.auctionStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {

        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QAuction.auction.regTime.after(dateTime);
    }

    //searchBy의 값에 따라서 상품명에 검색어를 포함하고 있는 상품 또는 상품 생성자의 아이디에 검색어를 포함하고 있는 상품을 조회하도록 조건값을 반환
    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        if (StringUtils.equals("title", searchBy)) {
            return QAuction.auction.title.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)) {
            return QAuction.auction.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Auction> getAdminAuctionPage(AuctionSearchDto auctionSearchDto, Pageable pageable) {
        QueryResults<Auction> results = queryFactory       //queryFactory를 이용하여 쿼리를 생성
                .selectFrom(QAuction.auction)
                .where(regDtsAfter(auctionSearchDto.getSearchDateType()), searchSellStatusEq(auctionSearchDto.getSearchSellStatus()), searchByLike(auctionSearchDto.getSearchBy(), auctionSearchDto.getSearchQuery()))
                .orderBy(QAuction.auction.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Auction> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);    //조회한 데이터를 Page 클래스의 구현체인 PageImpl 객체로 반환
    }


    private BooleanExpression titleLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QAuction.auction.title.like("%" + searchQuery + "%");
    }


    @Override
    public Page<MainAuctionDto> getMainAuctionPage(AuctionSearchDto auctionSearchDto, Pageable pageable) {
        QAuction auction = QAuction.auction;
        QAuctionImg auctionImg = QAuctionImg.auctionImg;

        QueryResults<MainAuctionDto> results = queryFactory
                .select(
                        new QMainAuctionDto(
                                auction.id,
                                auction.title,
                                auction.content,
                                auctionImg.imgUrl,
                                auction.price)
                )
                .from(auctionImg)
                .join(auctionImg.auction, auction)
                .where(auctionImg.repimgYn.eq("Y"))
                .where(titleLike(auctionSearchDto.getSearchQuery()))
                .orderBy(auction.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainAuctionDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);

    }

}
