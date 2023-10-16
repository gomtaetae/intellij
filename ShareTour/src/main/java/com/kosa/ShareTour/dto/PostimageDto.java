package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.entity.Postimage;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class PostimageDto {

    private Integer id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repimgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static PostimageDto of(Postimage postimage) {
        return modelMapper.map(postimage, PostimageDto.class);
    }


}