package com.kosa.ShareTour.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="packageimages")
@Data
public class Packageimage implements Serializable {

    @Id
    @Column(name="packageimages_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="landmarks_id")
    private Landmark landmark;

    @ManyToOne
    @JoinColumn(name="accommodations_id")
    private Accommodation accommodation;

    @ManyToOne
    @JoinColumn(name="restaurants_id")
    private Restaurant restaurant;

    @Column(name="img")
    private String img;

}