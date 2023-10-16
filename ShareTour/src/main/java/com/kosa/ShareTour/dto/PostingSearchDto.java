package com.kosa.ShareTour.dto;

import lombok.Data;

@Data
public class PostingSearchDto {

    private String searchDateType;

    private String searchBy;

    private String searchQuery = "";

}