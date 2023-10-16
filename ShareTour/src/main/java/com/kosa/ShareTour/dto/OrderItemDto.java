package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.entity.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {

    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.Title = orderItem.getItem().getTitle();
        this.count = orderItem.getCount();
        this.orderPrice =orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }

    private String Title;//상품명

    private int count;

    private int orderPrice;

    private String imgUrl;
}