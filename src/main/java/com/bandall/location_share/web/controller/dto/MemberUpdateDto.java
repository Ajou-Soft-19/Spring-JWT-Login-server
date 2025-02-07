package com.bandall.location_share.web.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateDto {
    public String username;
    public String phoneNumber;
    public String oldPassword;
    public String newPassword;
}
