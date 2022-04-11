package com.example.device.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.device.Dao.DeviceAssociationMapper;
import com.example.device.Dao.DeviceMapper;
import com.example.device.Dao.UserMapper;
import com.example.device.Dto.DeviceBindDto;
import com.example.device.Dto.DeviceCreateDto;
import com.example.device.Dto.UserLogInDto;
import com.example.device.Dto.UserSignUpDto;
import com.example.device.Exceptions.DuplicateInfoException;
import com.example.device.annotation.LoginRequired;
import com.example.device.po.Device;
import com.example.device.po.DeviceAssociation;
import com.example.device.po.DeviceMessageA;
import com.example.device.po.User;
import com.example.device.service.UserService;
import com.example.device.utils.SecurityTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    DeviceMapper deviceMapper;

    @Autowired
    DeviceAssociationMapper deviceAssociationMapper;

    @Override
    public void signUp(UserSignUpDto signUpDto) {
        DuplicateInfoException e = new DuplicateInfoException();
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username_en", signUpDto.getUserNameEn());

        if (userMapper.selectList(qw).size() != 0) {
            e.addDuplicateInfoField("username_en");
        }

        if (e.getDuplicateInfos().size() == 0) {

            User user = User.builder().userNameEn(signUpDto.getUserNameEn())
                            .userNameCn(signUpDto.getUserNameCn())
                                    .password(SecurityTool.encrypt(signUpDto.getUserNameEn(), signUpDto.getPassword()))
                                            .permission(signUpDto.getPermission())
                                                    .build();

            userMapper.insert(user);

        } else {
            throw e;
        }

    }


    @Override
    public User logIn(UserLogInDto userLogInDto) {
        String username = userLogInDto.getUserNameEn();
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username_en", username);
        for (User user : userMapper.selectList(qw)) {
            if (SecurityTool.match(user.getPassword(), userLogInDto.getUserNameEn(), userLogInDto.getPassword())) {
                return user;
            }
        }
        return null;

    }

    @Override
    @LoginRequired(needAdmin = true)
    public String modify(UserLogInDto userLogInDto) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username_en", userLogInDto.getUserNameEn());
        User user = userMapper.selectList(qw).get(0);
        user.setPassword(SecurityTool.encrypt(userLogInDto.getUserNameEn(), userLogInDto.getPassword()));
        userMapper.updateById(user);
        return "修改成功！";
    }

    @Override
    public void deviceCreate(DeviceCreateDto deviceCreateDto) {
        DuplicateInfoException e = new DuplicateInfoException();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceCreateDto.getDeviceId());

        if (deviceMapper.selectList(qw).size() != 0) {
            e.addDuplicateInfoField("device_id");
        }

        if (e.getDuplicateInfos().size() == 0) {

            Device device = Device.builder().deviceId(deviceCreateDto.getDeviceId())
                    .moaName(deviceCreateDto.getMoaName())
                    .usernameEn(deviceCreateDto.getUsernameEn())
                    .build();

            deviceMapper.insert(device);
            if (deviceCreateDto.getUsernameEn().length() > 0 || !deviceCreateDto.getUsernameEn().equals("undefined")) {
                DeviceAssociation deviceAssociation = DeviceAssociation.builder().usernameEn(deviceCreateDto.getUsernameEn())
                        .deviceId(deviceCreateDto.getDeviceId())
                        .build();
                deviceAssociationMapper.insert(deviceAssociation);
            }

        } else {
            throw e;
        }
    }

    @Override
    public void deviceBind(DeviceBindDto deviceBindDto){
        QueryWrapper<DeviceAssociation> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceBindDto.getDeviceId());
        if (deviceAssociationMapper.selectList(qw).size() == 0) {
            DeviceAssociation deviceAssociation = DeviceAssociation.builder().usernameEn(deviceBindDto.getUsernameEn())
                    .usernameCn(deviceBindDto.getUsernameCn())
                    .deviceId(deviceBindDto.getDeviceId())
                    .build();
            deviceAssociationMapper.insert(deviceAssociation);

        } else {
            DeviceAssociation deviceAssociation = deviceAssociationMapper.selectList(qw).get(0);
            deviceAssociation.setUsernameEn(deviceBindDto.getUsernameEn());
            deviceAssociation.setUsernameCn(deviceBindDto.getUsernameCn());
            deviceAssociationMapper.updateById(deviceAssociation);
        }
        QueryWrapper<Device> qw2 = new QueryWrapper<>();
        qw2.eq("device_id", deviceBindDto.getDeviceId());
        Device device = deviceMapper.selectList(qw2).get(0);
        device.setUsernameEn(deviceBindDto.getUsernameEn());
        device.setUsernameCn(deviceBindDto.getUsernameCn());
        deviceMapper.updateById(device);
    }

    @Override
    public Device modifyDevice(DeviceCreateDto deviceCreateDto) {
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceCreateDto.getDeviceId());
        Device device = deviceMapper.selectList(qw).get(0);
        if (deviceCreateDto.getUsernameEn().length() != 0) {
            device.setUsernameEn(deviceCreateDto.getUsernameEn());
            QueryWrapper<DeviceAssociation> qw2 = new QueryWrapper<>();
            qw2.eq("device_id", deviceCreateDto.getDeviceId());
            QueryWrapper<User> qw3 = new QueryWrapper<>();
            qw3.eq("username_en", deviceCreateDto.getUsernameEn());
            if (userMapper.selectList(qw3).size() == 0) {
                device.setUsernameEn("wrong_null");
            } else {
                DeviceAssociation deviceAssociation = deviceAssociationMapper.selectList(qw2).get(0);
                deviceAssociation.setUsernameEn(deviceCreateDto.getUsernameEn());
            }
        }
        if (deviceCreateDto.getMoaName().length() != 0) {
            device.setMoaName(deviceCreateDto.getMoaName());
        }
        if (device.getUsernameEn().equals("wrong_null")) {
            return device;
        }
        deviceMapper.updateById(device);
        return device;
    }

    @Override
    public String deleteDevice(String deviceSn) {
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceSn);
        List<Device> list = deviceMapper.selectList(qw);
        if (list.size() == 0) {
            return "设备不存在";
        } else {
            deviceMapper.deleteById(list.get(0).getId());
            return "删除成功";
        }
    }

    @Override
    public List<Device> getDevices(String usernameEn) {
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("username_en", usernameEn);
        return deviceMapper.selectList(qw);
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceMapper.selectList(null);
    }

    @Override
    public Device getDeviceById(Integer id) {
        return deviceMapper.selectById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectList(null);
    }

    @Override
    public User delete(Integer id) {
        User user = userMapper.selectById(id);
        userMapper.deleteById(id);
        return user;
    }


}
