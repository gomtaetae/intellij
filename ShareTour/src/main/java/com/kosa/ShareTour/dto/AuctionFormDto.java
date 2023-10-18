package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.constant.AuctionStatus;
import com.kosa.ShareTour.entity.Auction;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class AuctionFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 값입니다")
    private String title;

    @NotBlank(message = "내용은 필수 값입니다")
    private String content;

    @NotNull(message = "가격은 필수 값입니다")
    private Integer price;

    @NotNull(message = "증가 금액은 필수 값입니다")
    private Integer plusPrice;

    private AuctionStatus auctionStatus;

    //상품 저장후 수정할때 상품 이미지를 저장하는 리스트
    private List<AuctionImgDto> auctionImgDtoList = new ArrayList<>();

    //상품의 이미지 아이디를 저장하는 리스트
    private List<Long> auctionImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Auction createAuction(){
        return modelMapper.map(this, Auction.class);
    }

    public static AuctionFormDto of(Auction auction) {
        return modelMapper.map(auction, AuctionFormDto.class);
    }

}
