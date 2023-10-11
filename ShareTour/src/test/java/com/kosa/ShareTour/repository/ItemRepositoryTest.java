package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("패키지 저장 테스트")
    public void createItemTest(){
        Item item = new Item();
        item.setTitle("부산여행");
        item.setContent("ㅁasdfasdfasdfasdfasdfas낭러ㅣ마넝리ㅏㅓㅁㄴ이ㅏ럼ㄴ이ㅏ럼");
        item.setStartDate(LocalDate.EPOCH);
        item.setEndDate(LocalDate.MAX);
        item.setCreatedAt(LocalDateTime.now());
        item.setModifiedAt(LocalDateTime.now());
        item.setImg("이미지.png");
        item.setTotalPrice(100000);
        item.setDuration(2234234);
        item.setExpire(LocalDateTime.now());
        item.setInStock(20202020);
        item.setStockLeft(3023402);
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }
}