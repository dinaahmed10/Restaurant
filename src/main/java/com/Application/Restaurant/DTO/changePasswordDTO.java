package com.Application.Restaurant.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class changePasswordDTO
{
    String oldPassword;
    String newPassword;
    String confirmNewPassword;
}

