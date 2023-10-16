package com.kosa.ShareTour.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="accommodations")
@Getter
@Setter
@ToString
public class Accommodation {

    @Id
    @Column(name="accommodations_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="address")
    private String address;

    @Lob
    @Column(name="url")
    private String url;

    @Column(name="phone")
    private String phone;

    @Column(name="area")
    private String area;

    @Column(name="grade")
    private String grade;

    @Column(name="parking")
    private String parking;

    @Column(name="loc_x", nullable = false)
    private String locX;

    @Column(name="loc_y", nullable = false)
    private String locY;

    @Column(name="price", nullable = false)
    private float price;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.REMOVE)
    private List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.REMOVE)
    private List<Auction> auctionList = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.REMOVE)
    private List<Packageimage> packageimageList = new ArrayList<>();


//    public static Accommodation createAccommodation(AccommodationFormDto accommodationFormDto) {
//        Accommodation accommo = new Accommodation();
//        accommo.setName(accommodationFormDto.getName());
//        accommo.setAddress(accommodationFormDto.getAddress());
//        accommo.setUrl(accommodationFormDto.getUrl());
//        accommo.setPhone(accommodationFormDto.getPhone());
//        accommo.setArea(accommodationFormDto.getArea());
//        accommo.setGrade(accommodationFormDto.getGrade());
//        accommo.setParking(accommodationFormDto.getParking());
//        accommo.setLocX(accommodationFormDto.getLocX());
//        accommo.setLocY(accommodationFormDto.getLocY());
//        accommo.setPrice(accommodationFormDto.getPrice());
//
//        return accommo;
//    }


}