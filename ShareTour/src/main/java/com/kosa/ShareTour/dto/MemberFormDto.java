package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.entity.Item;
import com.kosa.ShareTour.entity.Member;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class MemberFormDto {

//    private Long id;

    @NotBlank(message = "이름은 필수 입력 값입니다")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다")
    @Email(message = "이메일 형식으로 입력 해야됩니다")
    private String email;

    @NotBlank(message = "닉네임은 필수 입력 값입니다")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력 해야 합니다")
    private String password;

    private LocalDateTime createTime;

    private String imgUrl;

    private String gender;

    @NotBlank(message = "생년월일은 필수 입력 값입니다")
    private String birthday;

    @Length(min = 1, max= 11, message = "전화번호는 '-'없이 11자 이하로 입력 해야 됩니다")
    private String phone;

    @NotBlank(message = "주소는 필수 입력 값입니다")
    private String addressMain;

    private String addressSub;

//    private String grade;

    private int point;


    //유저 이미지 추가 코드 아래 쭉
//    private List<MemberImgDto> memberImgDtoList = new ArrayList<>();
//
//    private List<Long> memberImgIds = new ArrayList<>();
//
//    private static ModelMapper modelMapper = new ModelMapper();
//
//    public Member createMember(){
//        return modelMapper.map(this, Member.class);
//    }
//
//    public static MemberFormDto of(Member member) {
//        return modelMapper.map(member, MemberFormDto.class);
//    }

}