package com.kosa.ShareTour.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="postings")
@Data
@ToString(exclude = "member")
public class Posting {

    @Id
    @Column(name="posting_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="title", length = 32, nullable = false)
    private String title;

    @Column(name="content", columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="members_id", nullable = false)
    private Member member;

//    @Override
//    public String toString() {
//        return "Posting{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                ", createdAt='" + createdAt +
//                ", user_id=" + (user != null ? user.getId() : null) +
//                ", user_username=" + (user != null ? user.getUsername() : null) +
//                ", user_email=" + (user != null ? user.getEmail() : null) +
//                ", user_nickname=" + (user != null ? user.getNickname() : null) +
//                ", user_password=" + (user != null ? user.getPassword() : null) +
//                ", user_createTime=" + (user != null ? user.getCreateTime() : null) +
//                ", user_imgUrl=" + (user != null ? user.getImgUrl() : null) +
//                ", user_gender=" + (user != null ? user.getGender() : null) +
//                ", user_birthday=" + (user != null ? user.getBirthday() : null) +
//                ", user_mobile=" + (user != null ? user.getMobile() : null) +
//                ", user_address=" + (user != null ? user.getAddress() : null) +
//                ", user_grade=" + (user != null ? user.getGrade() : null) +
//                ", user_point=" + (user != null ? user.getPoint() : null) +
//                '}';
//    }

}