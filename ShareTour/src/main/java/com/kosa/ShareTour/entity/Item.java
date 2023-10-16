package com.kosa.ShareTour.entity;

import com.kosa.ShareTour.constant.ItemSellStatus;
import com.kosa.ShareTour.dto.ItemFormDto;
import com.kosa.ShareTour.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity{

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;       //패키지 이름

    @Column(name = "content", columnDefinition = "LONGTEXT", nullable = false)
    private String content;       //패키지 설명

    @Column(name="img")
    private String img;

    @Column(name="price", nullable = false)
    private Integer price;

    @Column(name="in_stock", nullable = false)
    private Integer inStock;


    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;    //패키지 판매 상태

    public void updateItem(ItemFormDto itemFormDto) {

        this.title = itemFormDto.getTitle();
        this.content = itemFormDto.getContent();
        this.price = itemFormDto.getPrice();
        this.inStock = itemFormDto.getInStock();
        this.itemSellStatus = itemFormDto.getItemSellStatus();

    }

    public void removeStock ( int inStock){
        int restStock = this.inStock - inStock;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족 합니다.(현재 재고 수량: " + this.inStock + ")");
        }
        this.inStock = restStock;
    }

    public void addStock(int inStock){
        this.inStock += inStock;

    }
//    @ManyToOne
//    @JoinColumn(name="places_id")
//    private Place place;
//
//    @ManyToOne
//    @JoinColumn(name="landmarks_id")
//    private Landmark landmark;
//
//    @ManyToOne
//    @JoinColumn(name="accommodations_id")
//    private Accommodation accommodation;
//
//    @ManyToOne
//    @JoinColumn(name="restaurants_id")
//    private Restaurant restaurant;
}
