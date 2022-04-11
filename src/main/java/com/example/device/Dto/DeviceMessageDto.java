package com.example.device.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceMessageDto {

    private String dataType;

    private Double iaA;

    private Double iaB;

    private Double iaC;

    private Double irA;

    private Double irB;

    private Double irC;

    private Double phA;

    private Double phB;

    private Double phC;

    private Double phAB;

    private Double phBC;

    private Double phCA;

    private Integer itpA;

    private Integer itpB;

    private Integer itpC;

    private Integer itCntA;

    private Integer itCntB;

    private Integer itCntC;

    private Double tempA;

    private Double tempB;

    private Double tempC;

    private Integer staA;

    private Integer staB;

    private Integer staC;

    private String moaName;

    private Double iaIniA;

    private Double iaIniB;

    private Double iaIniC;

    private Double iaWarnA;

    private Double iaWarnB;

    private Double iaWarnC;

    private Double iaStopA;

    private Double iaStopB;

    private Double iaStopC;

    private Integer refer;

    private Double phIni;

    private Double irWarnA;

    private Double irWarnB;

    private Double irWarnC;

    private Double irStopA;

    private Double irStopB;

    private Double irStopC;


}
