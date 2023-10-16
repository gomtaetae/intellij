package com.kosa.ShareTour.entity;

import lombok.*;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="landmarks")
@Getter
@Setter
@ToString
public class Landmark {

    @Id
    @Column(name="landmarks_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="category", nullable = false)
    private String category;

    @Column(name="address")
    private String address;

    @Lob
    @Column(name="url")
    private String url;

    @Column(name="phone")
    private String phone;

    @Column(name="area")
    private String area;

    @Column(name="parking")
    private String parking;

    @Column(name="loc_x", length = 50, nullable = false)
    private String locX;

    @Column(name="loc_y", length = 50, nullable = false)
    private String locY;

    @Column(name="price")
    private float price;

    @OneToMany(mappedBy = "landmark", cascade = CascadeType.REMOVE)
    private List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "landmark", cascade = CascadeType.REMOVE)
    private List<Packageimage> packageimageList = new ArrayList<>();

}