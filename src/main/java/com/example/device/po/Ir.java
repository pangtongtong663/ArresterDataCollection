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
@TableName("ir")
public class Ir {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "deviceSn")
    private String deviceSn;

    @TableField(value = "IrA")
    private Double IrA;

    @TableField(value = "IrB")
    private Double IrB;

    @TableField(value = "IrC")
    private Double IrC;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
