package com.kosa.ShareTour.entity;

import com.kosa.ShareTour.constant.AuctionStatus;
import com.kosa.ShareTour.dto.AuctionFormDto;
import lombok.Data;

import javax.persistence.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="auctions")
@Data
public class Auction extends BaseEntity {

    @Id
    @Column(name = "auction_id")
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

    //주문시 한번에 증가 가격
    @Column(name="plusprice", nullable = false)
    private Integer plusPrice;

    @Column(name="totalPrice")
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    private AuctionStatus auctionStatus;    //옥션 판매 상태

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="accommodation_id")
    private Accommodation accommodation;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.REMOVE)
    private List<AuctionImg> auctionImgList = new ArrayList<>();

    @OneToMany(mappedBy = "auction", cascade = CascadeType.REMOVE)
    private List<AuctionOrderItem> auctionOrderItemList = new ArrayList<>();

    public void updateAuction(AuctionFormDto auctionFormDto) {

        this.title = auctionFormDto.getTitle();
        this.content = auctionFormDto.getContent();
        this.price = auctionFormDto.getPrice();
        this.plusPrice = auctionFormDto.getPlusPrice();
        this.auctionStatus = auctionFormDto.getAuctionStatus();

    }


    public void addPlusPrice(int plusPrice){
        this.plusPrice += plusPrice;

    }

}