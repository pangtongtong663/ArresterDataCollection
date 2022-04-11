package com.example.device.service;

import com.example.device.Dto.DeviceBindDto;
import com.example.device.Dto.DeviceCreateDto;
import com.example.device.Dto.UserLogInDto;
import com.example.device.Dto.UserSignUpDto;
import com.example.device.po.*;

import java.util.List;

public interface UserService {
    void signUp(UserSignUpDto signUpDto);

    User logIn(UserLogInDto userLogInDto);

    String modify(UserLogInDto userLogInDto);

    void deviceCreate(DeviceCreateDto deviceCreateDto);

    void deviceBind(DeviceBindDto deviceBindDto);

    Device modifyDevice(DeviceCreateDto deviceCreateDto);

    String deleteDevice(String deviceSn);

    List<Device> getDevices(String usernameEn);

    List<Device> getAllDevices();

    Device getDeviceById(Integer id);

    List<User> getAllUsers();

    User delete(Integer id);
}
