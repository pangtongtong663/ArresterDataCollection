package com.example.device.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("device_message_a")
public class DeviceMessageA {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "data_type")
    private String dataType;

    @TableField(value = "sign")
    private String sign;

    @TableField(value = "IaA")
    private Double IaA;

    @TableField(value = "IrA")
    private Double IrA;

    @TableField(value = "PhA")
    private Double PhA;

    @TableField(value = "PhAB")
    private Double PhAB;

    @TableField(value = "PhCA")
    private Double PhCA;

    @TableField(value = "ItpA")
    private Integer ItpA;

    @TableField(value = "ItCntA")
    private Integer ItCntA;

    @TableField(value = "TempA")
    private Double TempA;

    @TableField(value = "StaA")
    private Integer StaA;

    @TableField(value = "MoaName")
    private String MoaName;

    @TableField(value = "IaIniA")
    private Double IaIniA;

    @TableField(value = "IaWarnA")
    private Double IaWarnA;

    @TableField(value = "IaStopA")
    private Double IaStopA;

    @TableField(value = "Refer")
    private Integer Refer;

    @TableField(value = "PhIni")
    private Double PhIni;

    @TableField(value = "IrWarnA")
    private Double IrWarnA;

    @TableField(value = "IrStopA")
    private Double IrStopA;

    @TableField(value = "moduleSn")
    private String moduleSn;

    @TableField(value = "deviceSn")
    private String deviceSn;

    @TableField(value = "orderA")
    private Integer orderA;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
