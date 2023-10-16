package com.kosa.ShareTour.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="auctions")
@Data
public class Auction extends BaseEntity {

    @Id
    @Column(name="auctions_id")
    private Long id;

    @Column(name="finalprice")
    private float finalPrice;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="accommodation_id")
    private Accommodation accommodation;

}