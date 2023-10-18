package com.kosa.ShareTour.entity;

import com.kosa.ShareTour.constant.Role;
import com.kosa.ShareTour.dto.MemberFormDto;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;

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
    private String name;
    @Column(name="email", length = 60, nullable = false, unique = true)
    private String email;

    @Column(name="nickname", length = 45, nullable = false, unique = true)
    private String nickname;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="img")
    private String imgUrl;

    @Column(name="gender", length = 45, nullable = false)
    private String gender;

    @Column(name="birthday", nullable = false)
    private LocalDate birthday;

    @Column(name="phone", length = 45, nullable = false)
    private String phone;

    @Column(name="addressMain", nullable = false)
    private String addressMain;

    @Column(name="addressSub", nullable = false)
    private String addressSub;

    @Column(name="point")
    private int point;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Posting> postingList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

//    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
//    private List<Purchase> purchaseList = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE)
    private Cart cart;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Auction> auctionList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL
            , orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AuctionOrder> auctionOrderList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setNickname(memberFormDto.getNickname());

        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);

        member.setImgUrl(memberFormDto.getImgUrl());
        member.setGender(memberFormDto.getGender());
        member.setBirthday(LocalDate.parse(memberFormDto.getBirthday()));
        member.setPhone(memberFormDto.getPhone());
        member.setAddressMain(memberFormDto.getAddressMain());
        member.setAddressSub(memberFormDto.getAddressSub());
        member.setPoint(memberFormDto.getPoint());

        member.setRole(Role.USER);

        return member;
    }
}