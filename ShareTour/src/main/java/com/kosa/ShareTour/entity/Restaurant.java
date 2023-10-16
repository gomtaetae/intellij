package com.kosa.ShareTour.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="restaurants")
@Getter
@Setter
@ToString
public class Restaurant {

    @Id
    @Column(name="restaurants_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="address")
    private String address;

    @Column(name="phone")
    private String phone;

    @Column(name="area")
    private String area;

    @Column(name="parking")
    private String parking;

    @Column(name="loc_x", nullable = false)
    private String locX;

    @Column(name="loc_y", nullable = false)
    private String locY;

    @Column(name="price", nullable = false)
    private float price;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE)
    private List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE)
    private List<Packageimage> packageimageList = new ArrayList<>();

}