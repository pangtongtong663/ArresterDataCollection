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
public class DeviceDataByTimeDto {
    @NotBlank(message = "Empty deviceSn")
    private String deviceSn;

    @NotBlank(message = "Empty duration")
    private Integer days;
}
