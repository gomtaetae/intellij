package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {

    private Long id;

    private String title;

    private String content;

    private String img;

    private Integer price;

    private Integer inStock;

    private ItemSellStatus itemSellStatus;
}
