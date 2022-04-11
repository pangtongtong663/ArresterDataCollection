package com.example.device.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogInDto {
    @NotBlank(message = "Empty username")
    private String userNameEn;

    @NotBlank(message = "Empty password")
    private String password;
}
