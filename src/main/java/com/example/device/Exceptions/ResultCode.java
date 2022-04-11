package com.example.device.Exceptions;

public enum ResultCode implements ErrorCode {
    SUCCESS(0, "操作成功"),
    FAILED(100, "操作失败"),
    USER_NOT_EXISTS(101, "用户不存在"),
    UNAUTHORIZED(102, "token验证失败"),
    FORBIDDEN(103, "没有相关权限"),
    LOGIN_FAILED(104, "用户名与密码不对应"),
    PASSWORD_ERROR(105, "输入的密码与原密码不一致"),
    DEVICE_DELETE_ERROR(106, "非管理员只能删除自己的设备"),
    DEVICE_NO_DATA(107, "该设备没有数据"),
    DEVICE_NOT_EXISTS(108, "设备不存在"),
    FILE_ERROR(109, "文件错误"),
    FORCE_DOWN(110, "强制被挤下线"),
    FILE_ALREADY_EXISTS(111, "文件已存在"),
    FILE_SAVE_FAILED(112, "文件保存失败"),
    FILE_PATH_ERROR(113, "文件路径错误"),
    NO_APK_FOUND(114, "没有找到apk文件"),
    MUTI_APK_FOUND(115, "服务器中有多个文件"),
    FILE_MAKE_FAILED(116, "目录创建失败"),
    FILE_DELETE_FAILED(117, "尝试删除服务器上已有的apk失败"),
    FILE_NOT_EXISTS(118, "apk目录不存在"),
    DOWNLOAD_FILE_FAILED(119, "文件下载失败"),
    CANNOT_OPERATION(120, "无法操作，请注意自己的操作权限"),
    DEVICE_NO_USER(121, "设备所属用户不存在"),
    DATA_NOT_EXISTS(122, "数据不存在"),
    PERMISSION_DENIED(2403, "权限不足"),
    PARAMS_ERROR(2000, "参数错误");

    private final Integer code;
    private final String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
