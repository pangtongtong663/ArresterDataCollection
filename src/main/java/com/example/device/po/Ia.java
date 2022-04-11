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
@TableName("ia")
public class Ia {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "deviceSn")
    private String deviceSn;

    @TableField(value = "IaA")
    private Double IaA;

    @TableField(value = "IaB")
    private Double IaB;

    @TableField(value = "IaC")
    private Double IaC;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
