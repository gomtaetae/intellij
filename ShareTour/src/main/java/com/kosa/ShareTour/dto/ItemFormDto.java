package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.constant.ItemSellStatus;
import com.kosa.ShareTour.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 값입니다")
    private String title;

    @NotBlank(message = "내용은 필수 값입니다")
    private String content;

    @NotNull(message = "가격은 필수 값입니다")
    private Integer price;

    @NotNull(message = "재고는 필수 값입니다")
    private Integer inStock;

    private ItemSellStatus itemSellStatus;

    //상품 저장후 수정할때 상품 이미지를 저장하는 리스트
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    //상품의 이미지 아이디를 저장하는 리스트
    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    //Item, ItemFormDto는 modelMapper를 이용해서 엔티티 객체와 DTO 객체 간의 데이터를 복사한 객체를 반환해주는 메소드
    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }

}
