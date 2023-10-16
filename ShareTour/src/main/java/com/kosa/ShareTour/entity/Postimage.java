package com.kosa.ShareTour.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="postimages")
@Setter
@Getter
public class Postimage extends BaseEntity {

    @Id
    @Column(name="postimages_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name="postings_id")
    private Posting posting;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repimgYn;

    public void updatePostimage(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
//        this.setPosting(posting);
    }

}