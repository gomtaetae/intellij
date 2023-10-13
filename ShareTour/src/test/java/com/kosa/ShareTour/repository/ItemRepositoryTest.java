package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.constant.ItemSellStatus;
import com.kosa.ShareTour.entity.Item;
import com.kosa.ShareTour.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class ItemRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("패키지 저장 테스트")
    public void createItemTest(){
        Item item = new Item();
        item.setTitle("부산여행");
        item.setContent("부산");
        item.setImg("이미지.png");
        item.setTotalPrice(100000);
        item.setInStock(20202020);
        item.setStockLeft(3023402);
        item.setItemSellStatus(ItemSellStatus.SELL);
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    public void createItemList(){
        for(int i=1;i<=10;i++){
            Item item = new Item();
            item.setTitle("테스트 상품" + i);
            item.setContent("부산" + i);
            item.setImg("이미지.png");
            item.setTotalPrice(100000 + i);
            item.setInStock(20202020 + i);
            item.setStockLeft(3023402 + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품 조회 테스트")
    public void findByTitleTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByTitle("테스트 상품1");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByTitleOrContent(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByTitleOrContent("테스트 상품1", "테스트 상품 상세 설명5");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByTotalPriceLessThanTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByTotalPriceLessThan(100005);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByTotalPriceLessThanOrderByToTalPriceDesc(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByTotalPriceLessThanOrderByTotalPriceDesc(10005);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByContentTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByContent("테스트 상품 상세 설명");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByContentByNative(){
        this.createItemList();
        List <Item> itemList = itemRepository.findByContentlByNative("테스트 상품 상세 설명");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest(){
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query  = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.content.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.totalPrice.desc());

        List<Item> itemList = query.fetch();

        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    public void createItemList2(){
        for(int i=1;i<=5;i++){
            Item item = new Item();
            item.setTitle("테스트 상품" + i);
            item.setTotalPrice(10000 + i);
            item.setContent("테스트 상품 상세 설명" + i);
            item.setImg("이미지.png");
            item.setTotalPrice(100000 + i);
            item.setInStock(20202020 + i);
            item.setStockLeft(3023402 + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            itemRepository.save(item);
        }

        for(int i=6;i<=10;i++){
            Item item = new Item();
            item.setContent("테스트 상품" + i);
            item.setTotalPrice(10000 + i);
            item.setContent("테스트 상품 상세 설명" + i);
            item.setImg("이미지.png");
            item.setTotalPrice(100000 + i);
            item.setInStock(20202020 + i);
            item.setStockLeft(3023402 + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트 2")
    public void queryDslTest2(){

        this.createItemList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;
        String content = "테스트 상품 상세 설명";
        int totalPrice = 10003;
        String itemSellStat = "SELL";

        booleanBuilder.and(item.content.like("%" + content + "%"));
        booleanBuilder.and(item.totalPrice.gt(totalPrice));
        System.out.println(ItemSellStatus.SELL);
        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)){
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("total elements : " + itemPagingResult. getTotalElements ());

        List<Item> resultItemList = itemPagingResult.getContent();
        for(Item resultItem: resultItemList){
            System.out.println(resultItem.toString());
        }
    }
}