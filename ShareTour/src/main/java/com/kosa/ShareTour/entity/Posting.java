package com.kosa.ShareTour.entity;

import com.kosa.ShareTour.dto.PostingFormDto;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="postings")
@Setter
@Getter
@DynamicInsert @DynamicUpdate       //모든 필드를 업데이트하지 않고 일부만 업데이트(좋아요 기능을 위해 추가)
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //NoArgsConstructor(아무런 매개변수가 없는 생성자 - 다른 패키지에 소속된 클래스 접근 불가)
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

    @OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)
    private List<Postimage> postimageList = new ArrayList<>();

    //좋아요, 조회수
    private Integer postingCount = 0;

    private Integer likeCount = 0;

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