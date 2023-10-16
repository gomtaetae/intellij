package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.dto.MainItemDto;
import com.kosa.ShareTour.dto.QMainItemDto;
import com.kosa.ShareTour.entity.QItemImg;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.kosa.ShareTour.constant.ItemSellStatus;
import com.kosa.ShareTour.dto.ItemSearchDto;
import com.kosa.ShareTour.entity.Item;
import com.kosa.ShareTour.entity.QItem;

//import com.querydsl.core.types.dsl.Wildcard;
//import com.kosa.ShareTour.dto.MainItemDto;
//import com.kosa.ShareTour.dto.QMainItemDto;
//import com.kosa.ShareTour.entity.QItemImg;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;


//itemRepositoryCustom을 상속
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    //동적으로 쿼리를 생성하기 위해 JPAQueryFactory 클래스를 사용
    private JPAQueryFactory queryFactory;

    //JPAQueryFactory의 생성자로 EntityManager 객체를 넣어줌

    public ItemRepositoryCustomImpl(EntityManager em) {

        this.queryFactory = new JPAQueryFactory(em);
    }

    //상품 판매 상태 조건이 전체(null)일 경우는 null 리턴, 결과값이 null이면 where 절에서 해당 조건은 무시됨
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {

        //serachDateType의 값에 따라서 dateTime의 값을 이전 시간의 값으로 세팅 후 해당 시간 이후로 등록된 상품만 조회
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
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

        return QItem.item.regTime.after(dateTime);
    }

    //searchBy의 값에 따라서 상품명에 검색어를 포함하고 있는 상품 또는 상품 생성자의 아이디에 검색어를 포함하고 있는 상품을 조회하도록 조건값을 반환
    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        if (StringUtils.equals("title", searchBy)) {
            return QItem.item.title.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)) {
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        QueryResults<Item> results = queryFactory       //queryFactory를 이용하여 쿼리를 생성
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()), searchSellStatusEq(itemSearchDto.getSearchSellStatus()), searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Item> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);    //조회한 데이터를 Page 클래스의 구현체인 PageImpl 객체로 반환
    }


    /*
    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        List<Item> content = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(Wildcard.count).from(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .fetchOne()
                ;

        return new PageImpl<>(content, pageable, total);
    }
*/
    private BooleanExpression titleLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.title.like("%" + searchQuery + "%");
    }


    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        QueryResults<MainItemDto> results = queryFactory
                .select(
                        new QMainItemDto(
                        item.id,
                        item.title,
                        item.content,
                        itemImg.imgUrl,
                        item.price)
                )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repimgYn.eq("Y"))
                .where(titleLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);

    }

}
