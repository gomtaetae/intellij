package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.constant.AuctionStatus;
import com.kosa.ShareTour.constant.OrderStatus;
import com.kosa.ShareTour.entity.AuctionOrder;

import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class AuctionOrderHistDto {

    public AuctionOrderHistDto(AuctionOrder auctionOrder){
        this.auctionOrderId = auctionOrder.getId();
        this.auctionOrderDate = auctionOrder.getAuctionOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.auctionStatus = auctionOrder.getAuctionStatus();
    }

    private Long auctionOrderId; //주문아이디
    private String auctionOrderDate; //주문날짜
    private AuctionStatus auctionStatus; //주문 상태

    private List<AuctionOrderItemDto> auctionOrderItemDtoList = new ArrayList<>();

    //주문 상품리스트
    public void addAuctionOrderItemDto(AuctionOrderItemDto auctionOrderItemDto){
        auctionOrderItemDtoList.add(auctionOrderItemDto);
    }

}
