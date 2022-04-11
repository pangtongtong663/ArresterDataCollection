package com.example.device.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDto {

    @NotBlank(message = "Empty username")
    private String userNameEn;

    private String userNameCn;

    @NotBlank(message = "Empty password")
    private String password;


    private Integer permission;


}
