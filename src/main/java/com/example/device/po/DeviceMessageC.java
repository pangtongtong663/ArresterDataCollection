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
@TableName("device_message_c")
public class DeviceMessageC {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "data_type")
    private String dataType;

    @TableField(value = "sign")
    private String sign;

    @TableField(value = "IaC")
    private Double IaC;

    @TableField(value = "IrC")
    private Double IrC;

    @TableField(value = "PhC")
    private Double PhC;

    @TableField(value = "PhBC")
    private Double PhBC;

    @TableField(value = "PhCA")
    private Double PhCA;

    @TableField(value = "ItpC")
    private Integer ItpC;

    @TableField(value = "ItCntC")
    private Integer ItCntC;

    @TableField(value = "TempC")
    private Double TempC;

    @TableField(value = "StaC")
    private Integer StaC;

    @TableField(value = "MoaName")
    private String MoaName;

    @TableField(value = "IaIniC")
    private Double IaIniC;

    @TableField(value = "IaWarnC")
    private Double IaWarnC;

    @TableField(value = "IaStopC")
    private Double IaStopC;

    @TableField(value = "Refer")
    private Integer Refer;

    @TableField(value = "PhIni")
    private Double PhIni;

    @TableField(value = "IrWarnC")
    private Double IrWarnC;

    @TableField(value = "IrStopC")
    private Double IrStopC;

    @TableField(value = "moduleSn")
    private String moduleSn;

    @TableField(value = "deviceSn")
    private String deviceSn;

    @TableField(value = "orderC")
    private Integer orderC;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
