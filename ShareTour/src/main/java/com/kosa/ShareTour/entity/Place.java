package com.kosa.ShareTour.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="places")
@Getter
@Setter
@ToString
public class Place {

    @Id
    @Column(name="places_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="country", nullable = false)
    private String country;

    @Column(name="province", nullable = false)
    private String province;

    @Column(name="city", nullable = false)
    private String city;

    @Column(name="loc_x", nullable = false)
    private String locX;

    @Column(name="loc_y", nullable = false)
    private String locY;

    @Lob
    @Column(name="img")
    private String img;

    @OneToMany(mappedBy = "place", cascade = CascadeType.REMOVE)
    private List<Item> itemList = new ArrayList<>();

}