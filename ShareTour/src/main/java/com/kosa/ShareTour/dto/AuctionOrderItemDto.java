package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.entity.AuctionOrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuctionOrderItemDto {
    public AuctionOrderItemDto(AuctionOrderItem auctionOrderItem, String imgUrl){
        this.title = auctionOrderItem.getAuction().getTitle();
        this.count = auctionOrderItem.getCount();
        this.orderPrice = auctionOrderItem.getPrice();
        this.imgUrl = imgUrl;
    }

    private String title; //상품명
    private int count; //주문 수량

    private int orderPrice; //주문 금액
    private String imgUrl; //상품 이미지 경로
}
