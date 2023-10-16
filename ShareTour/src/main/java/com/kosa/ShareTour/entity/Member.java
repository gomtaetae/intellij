package com.kosa.ShareTour.entity;

import com.kosa.ShareTour.constant.Role;
import com.kosa.ShareTour.dto.MemberFormDto;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="members")
@Data
public class Member extends BaseEntity{

    @Id
    @Column(name="members_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="username", length = 32, nullable = false)
    private String username;

    @Column(name="email", length = 60, nullable = false, unique = true)
    private String email;

    @Column(name="nickname", length = 45, nullable = false, unique = true)
    private String nickname;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="create_time")
    private LocalDateTime createTime;


    //@PrePersist
    //protected void onCreate() {
    //    createTime = LocalDateTime.now();
    //}

    @Column(name="img")
    private String imgUrl;

    @Column(name="gender", length = 45)
    private String gender;

    @Column(name="birthday", nullable = false)
    private LocalDate birthday;

    @Column(name="mobile", length = 45)
    private String mobile;

    @Column(name="address", length = 45, nullable = false)
    private String address;

//    @Column(name="grade", length = 45)
//    private String grade;

    @Column(name="point")
    private int point;

//    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
//    private List<Posting> postingList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setUsername(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setNickname(memberFormDto.getNickname());

        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);

        member.setCreateTime(LocalDateTime.now());
        member.setImgUrl(memberFormDto.getImgUrl());
        member.setGender(memberFormDto.getGender());
        member.setBirthday(LocalDate.parse(memberFormDto.getBirthday()));
        member.setMobile(memberFormDto.getPhone());
        member.setAddress(memberFormDto.getAddressMain() + memberFormDto.getAddressSub());
//        member.setGrade(memberFormDto.getGrade());
        member.setPoint(memberFormDto.getPoint());

        member.setRole(Role.USER);
        flush();

        return member;
    }

    //사용자 수정 안되면 지우기
//    public void updateMember(MemberFormDto memberFormDto) {
//        this.username = memberFormDto.getName();
//        this.email = memberFormDto.getEmail();
//        this.nickname = memberFormDto.getNickname();
//        this.password = memberFormDto.getPassword();
//        this.gender = memberFormDto.getGender();
//        this.mobile = memberFormDto.getPhone();
//        this.grade = memberFormDto.getGrade();
//        this.point = memberFormDto.getPoint();
//    }
    //여기 위까지 사용자 수정 추가

    private static void flush() {
    }

}