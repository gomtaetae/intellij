package com.kosa.ShareTour.dto;
import com.kosa.ShareTour.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {

    private Long id;

    private String title;

    private String content;

    private Integer price;

    private Integer inStock;

    private Integer stockLeft;

    private ItemSellStatus itemSellStatus;

}