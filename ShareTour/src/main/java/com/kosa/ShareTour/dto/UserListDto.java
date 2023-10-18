package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.constant.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserListDto {

    private Long id;

    private String name;

    private String email;

    private String nickname;

    private LocalDate birthday;

    private String phone;

    private int point;

    private Role role;

}