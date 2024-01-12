package com.bandall.location_share.web.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCreateDto {

    @Email
    private String email;

    @NotNull
    private String password;

    @NotEmpty
    private String username;

    @NotEmpty
    private String phoneNumber;
}
