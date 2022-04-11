package com.example.device.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDataDto {

   private String moduleSn;

   private String deviceSn;

   private String sign;

   private DeviceMessageDto[] msgs;
}
