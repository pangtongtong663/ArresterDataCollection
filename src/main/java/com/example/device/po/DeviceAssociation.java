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
@TableName("device_association")
public class DeviceAssociation {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "username_en")
    private String usernameEn;

    @TableField(value = "username_cn")
    private String usernameCn;

    @TableField(value = "device_id")
    private String deviceId;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
