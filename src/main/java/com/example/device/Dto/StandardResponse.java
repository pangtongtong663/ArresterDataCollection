package com.example.device.Dto;

import java.util.ArrayList;

public class StandardResponse {

    public static ResponseDto<Object> ok() {
        return new ResponseDto<>("200", "success");
    }

    public static ResponseDto<Object> ok(Object data) {
        return new ResponseDto<>("200", "success", data);
    }

    public static ResponseDto<Object> fail() {
        return new ResponseDto<>("400", "failed");
    }
    public static ResponseDto<Object> fail(Object data) {
        return new ResponseDto<>("400", "failed", data);
    }


    public static ResponseDto<Object> duplicateInformation(ArrayList<String> duplicateInfos) {
        return new ResponseDto<>("403", "duplicate information", duplicateInfos);
    }

}
