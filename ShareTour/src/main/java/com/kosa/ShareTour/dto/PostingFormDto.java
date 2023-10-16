package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.entity.Posting;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostingFormDto {

    private Long id;

    @NotBlank(message = "제목을 적어주세요")
    private String title;

    @NotBlank(message = "내용을 적어주세요")
    private String content;

    private List<PostimageDto> postimageDtoList = new ArrayList<>();

    private List<Long> postimageIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Posting createPosting() {
        return modelMapper.map(this, Posting.class);
    }

    public static PostingFormDto of(Posting posting) {
        return modelMapper.map(posting, PostingFormDto.class);
    }


}