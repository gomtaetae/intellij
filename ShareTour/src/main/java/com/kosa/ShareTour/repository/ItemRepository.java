package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {
//상속되는 파일들

    //title 찾는 쿼리 메소드
    List<Item> findByTitle(String title);

    //title이나 내용에서 찾는 쿼리 메소드
    List<Item> findByTitleOrContent(String title, String content);

    //입력 price보다 작은 상품을 모두 조회
    List<Item> findByPriceLessThan(Integer price);

    //OrderBy로 내림차순 정렬(Desc는 내림차순, Asc는 오름차순)
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    //SQL 쿼리문을 사용해 조회하는 방법
    @Query("select i from Item i where i.content like " +
            "%:content% order by i.price desc")
    List<Item> findByContent(@Param("content") String content);

    //navtiveQuery를 사용해서 기존쿼리를 그대로 사용가능
    @Query(value="select * from item i where i.content like " +
            "%:content% order by i.price desc", nativeQuery = true)
    List<Item> findByContentlByNative(@Param("content") String content);
}
