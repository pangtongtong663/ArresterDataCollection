package com.example.device.controller;

import com.example.device.Dto.*;
import com.example.device.Exceptions.DuplicateInfoException;
import com.example.device.annotation.LoginRequired;
import com.example.device.intercepter.AuthInterceptor;
import com.example.device.po.Device;
import com.example.device.po.User;
import com.example.device.service.UserService;
import com.example.device.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwt;

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
    public ResponseDto<Object> signUp(@Valid @RequestBody UserSignUpDto userSignUpDto) {
        try {
            userService.signUp(userSignUpDto);
            return StandardResponse.ok("注册成功");
        } catch(DuplicateInfoException e) {
            return StandardResponse.duplicateInformation(e.getDuplicateInfos());
        }
    }

    @RequestMapping(value = "/log_in", method = RequestMethod.POST)
    public ResponseDto<Object> logIn(@Valid @RequestBody UserLogInDto userLogInDto) {
        User user = userService.logIn(userLogInDto);

        if (user != null) {
            Map<String,String> map = new HashMap<>();
            map.put("token", jwt.generateJwtToken(user.getId()));
            map.put("登陆状态", "成功登录");
            return StandardResponse.ok(map);
        } else {
            return StandardResponse.fail("用户不存在");
        }

    }

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseDto<Object> modify(@Valid @RequestBody UserLogInDto userLogInDto) {
        return StandardResponse.ok(userService.modify(userLogInDto));
    }

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseDto<Object> delete(Integer id) {
        return StandardResponse.ok(userService.delete(id));
    }

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/device_create", method = RequestMethod.POST)
    public ResponseDto<Object> deviceCreate(@Valid @RequestBody DeviceCreateDto deviceCreateDto) {
        try {
            userService.deviceCreate(deviceCreateDto);
            if (deviceCreateDto.getUsernameEn().length() == 0) {
                return StandardResponse.ok("设备创建成功，未绑定");
            } else {
                return StandardResponse.ok("设备创建成功，已绑定");
            }

        } catch(DuplicateInfoException e) {
            return StandardResponse.duplicateInformation(e.getDuplicateInfos());
        }
    }

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/device_bind", method = RequestMethod.POST)
    public ResponseDto<Object> deviceBind(@Valid @RequestBody DeviceBindDto deviceBindDto) {
        try {
            userService.deviceBind(deviceBindDto);
            return StandardResponse.ok("绑定成功");
        } catch(DuplicateInfoException e) {
            return StandardResponse.duplicateInformation(e.getDuplicateInfos());
        }
    }

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/show_all_users", method = RequestMethod.GET)
    public List<User> showAllUsers() {
        return userService.getAllUsers();
    }

    @LoginRequired
    @RequestMapping(value = "/test/user_po", method = RequestMethod.GET)
    public User queryCurrentUser() {
        return AuthInterceptor.getCurrentUser();
    }

    @LoginRequired
    @RequestMapping(value = "/test/user_id", method = RequestMethod.GET)
    public Integer getUserId() {
        return AuthInterceptor.getCurrentUser().getId();
    }

    @LoginRequired
    @RequestMapping(value = "/test/username_en", method = RequestMethod.GET)
    public String getUserNameEn() {
        return AuthInterceptor.getCurrentUser().getUserNameEn();
    }

    @LoginRequired
    @RequestMapping(value = "/test/username_cn", method = RequestMethod.GET)
    public String getUserNameCn() {
        return AuthInterceptor.getCurrentUser().getUserNameCn();
    }

    @LoginRequired
    @RequestMapping(value = "/test/user_admin", method = RequestMethod.GET)
    public Boolean isUserAdmin() {
        if (AuthInterceptor.getCurrentUser().getPermission() == 1) {
            return true;
        } else {
            return false;
        }
    }

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/delete_device", method = RequestMethod.POST)
    public String deleteDevice(String deviceSn) {
        return userService.deleteDevice(deviceSn);
    }

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/modify_device", method = RequestMethod.POST)
    public String deviceModify(@RequestBody DeviceCreateDto deviceCreateDto) {
        Device device = userService.modifyDevice(deviceCreateDto);
        if (device.getUsernameEn().equals("wrong_null")) {
            return "用户不存在";
        } else {
            return "修改成功";
        }
    }

    @LoginRequired
    @RequestMapping(value = "/get_device", method = RequestMethod.GET)
    public List<Device> getDevice() {
        User currentUser = AuthInterceptor.getCurrentUser();
        return userService.getDevices(currentUser.getUserNameEn());
    }

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/get_deviceById", method = RequestMethod.GET)
    public Device getDeviceById(Integer id) {

        return userService.getDeviceById(id);
    }

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/get_all_device", method = RequestMethod.GET)
    public List<Device> getAllDevice() {
        return userService.getAllDevices();
    }

}
