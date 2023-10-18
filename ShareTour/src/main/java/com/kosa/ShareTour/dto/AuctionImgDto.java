package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.entity.AuctionImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class AuctionImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static AuctionImgDto of(AuctionImg auctionImg) {
        return modelMapper.map(auctionImg, AuctionImgDto.class);
    }

}
