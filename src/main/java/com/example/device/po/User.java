package com.example.device.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "username_en")
    private String userNameEn;

    @TableField(value = "username_cn")
    private String userNameCn;

    @TableField(value = "password")
    @JsonIgnore
    private String password;


    @TableField(value = "permission")
    private Integer permission;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    public boolean isAdmin() {
        return permission == 1;
    }



}