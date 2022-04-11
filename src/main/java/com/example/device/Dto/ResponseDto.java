package com.example.device.Dto;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseDto <T> {
    @NonNull
    private String code;

    @NonNull
    private String message;

    private T data;

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

}
