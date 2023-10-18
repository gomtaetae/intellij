package com.kosa.ShareTour.dto;

import com.kosa.ShareTour.constant.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Data
@Setter
public class RoleUpdateDto {

    private Long memberId;
    private String newRole;

    public RoleUpdateDto(Long memberId, String newRole) {
        this.memberId = memberId;
        this.newRole = newRole;
    }

    public RoleUpdateDto() {

    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setNewRole(Role newRole) {
        this.newRole = String.valueOf(newRole);
    }

}