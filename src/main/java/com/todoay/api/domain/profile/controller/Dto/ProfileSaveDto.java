package com.todoay.api.domain.profile.controller.Dto;

import com.todoay.api.domain.auth.entity.Auth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSaveDto {

    private String nickname;
    private String img_url;
    private String intro_msg;

}
