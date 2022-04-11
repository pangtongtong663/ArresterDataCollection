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
@TableName("device_message_b")
public class DeviceMessageB {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "data_type")
    private String dataType;

    @TableField(value = "sign")
    private String sign;

    @TableField(value = "IaB")
    private Double IaB;

    @TableField(value = "IrB")
    private Double IrB;

    @TableField(value = "PhB")
    private Double PhB;

    @TableField(value = "PhAB")
    private Double PhAB;

    @TableField(value = "PhBC")
    private Double PhBC;

    @TableField(value = "ItpB")
    private Integer ItpB;

    @TableField(value = "ItCntB")
    private Integer ItCntB;

    @TableField(value = "TempB")
    private Double TempB;

    @TableField(value = "StaB")
    private Integer StaB;

    @TableField(value = "MoaName")
    private String MoaName;

    @TableField(value = "IaIniB")
    private Double IaIniB;

    @TableField(value = "IaWarnB")
    private Double IaWarnB;

    @TableField(value = "IaStopB")
    private Double IaStopB;

    @TableField(value = "Refer")
    private Integer Refer;

    @TableField(value = "PhIni")
    private Double PhIni;

    @TableField(value = "IrWarnB")
    private Double IrWarnB;

    @TableField(value = "IrStopB")
    private Double IrStopB;

    @TableField(value = "moduleSn")
    private String moduleSn;

    @TableField(value = "deviceSn")
    private String deviceSn;

    @TableField(value = "orderB")
    private Integer orderB;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
