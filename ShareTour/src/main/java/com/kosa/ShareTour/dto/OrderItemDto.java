package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemDto {

    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.title = orderItem.getItem().getTitle();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }

    private String title; //상품명
    private int count; //주문 수량

    private int orderPrice; //주문 금액
    private String imgUrl; //상품 이미지 경로

}