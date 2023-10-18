package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.constant.AuctionStatus;
import com.kosa.ShareTour.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuctionDto {

    private Long id;

    private String title;

    private String content;

    private String img;

    private Integer price;

    private Integer plusPrice;

    private AuctionStatus auctionStatus;
}
