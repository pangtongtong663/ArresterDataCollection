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
@TableName("ph")
public class Ph {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "PhA")
    private Double PhA;

    @TableField(value = "deviceSn")
    private String deviceSn;

    @TableField(value = "PhB")
    private Double PhB;

    @TableField(value = "PhC")
    private Double PhC;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
