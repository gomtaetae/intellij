package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg) {
        //itemImg 엔티티 객체를 파라미터로 받아서 객체의 ㄱ자료형과 멤버변수의 이름이 같을 때 ItemImgDto로 값을 복사해서 반환받는 코드
        //static을 선언해 ItemImgDto 객체를 생성하지 않아도 호출할 수 있도록 작성
        return modelMapper.map(itemImg,ItemImgDto.class);
    }

}
