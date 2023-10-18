package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.entity.Accommodation;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class AccommodationDto {

    private Long id;

    private String name;

    private String address;

    private String url;

    private String phone;

    private String area;

    private String grade;

    private String parking;

    private String locX;

    private String locY;

    private float price;


    private static ModelMapper modelMapper = new ModelMapper();
    //실제 Accommodation을 생성하는 것이 아닌 객체 반환용
    public Accommodation createAccommodation() {
        return modelMapper.map(this, Accommodation.class);
    }

    public static AccommodationDto of(Accommodation accommodation) {
        return modelMapper.map(accommodation, AccommodationDto.class);
    }

}