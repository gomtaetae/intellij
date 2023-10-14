package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {

    private String searchDateType; //현재 시간과 상품 등록일을 비교해서 상품 데이터를 조회

    private ItemSellStatus searchSellStatus;    //상품의 판매상태를 기준으로 상품 데이터를 조회

    private String searchBy;       //상품을 조회할 때 어떤 유형으로 조회할지 선택

    private String searchQuery =""; //조회할 검색어 저장할 변수(searchBy가 title일때 상품명 기준으로 검색, createdBy일 경우 상품 등록자 아이디 기준)
}
