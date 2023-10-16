package com.kosa.ShareTour.entity;

import com.kosa.ShareTour.dto.PostingFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="postings")
@Setter
@Getter
@ToString(exclude = "member")
public class Posting extends BaseEntity {

    @Id
    @Column(name="posting_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Lob
    @Column(name="content", columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="members_id")
    private Member member;

//    @OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)
//    private List<Comment> commentList = new ArrayList<>();

//    public void createPosting(String title, String content) {
//        Posting posting = new Posting();
//        posting.setTitle(title);
//        posting.setContent(content);
//    }

    public void updatePosting(PostingFormDto postingFormDto){
        this.title = postingFormDto.getTitle();
        this.content = postingFormDto.getContent();
    }

}