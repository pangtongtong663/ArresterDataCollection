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
public class DeviceBindDto {

    private String usernameEn;

    private String usernameCn;

    private String deviceId;
}
