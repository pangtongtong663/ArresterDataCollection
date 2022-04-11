package com.example.device.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.device.Dao.DeviceMapper;
import com.example.device.Dto.DeviceDataByTimeDto;
import com.example.device.Dto.DeviceDataDto;
import com.example.device.Dto.ResponseDto;
import com.example.device.Dto.StandardResponse;
import com.example.device.annotation.LoginRequired;
import com.example.device.intercepter.AuthInterceptor;
import com.example.device.po.*;
import com.example.device.service.DeviceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/data", produces = "application/json")
public class DeviceDataController {

    @Autowired
    DeviceDataService deviceDataService;

    @Autowired
    DeviceMapper deviceMapper;


    @RequestMapping(value = "/record_data", method = RequestMethod.POST)
    public ResponseDto<Object> recordData(@RequestBody @Valid DeviceDataDto deviceDataDto) {
        System.out.println(deviceDataDto.getDeviceSn());
        deviceDataService.recordDeviceData(deviceDataDto);
        return StandardResponse.ok();
    }

    @LoginRequired()
    @RequestMapping(value = "/show_dataA", method = RequestMethod.GET)
    public ResponseDto<Object> showDataA(String deviceSn) {
        User currentUser = AuthInterceptor.getCurrentUser();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceSn);
        Device device = deviceMapper.selectList(qw).get(0);
        if (currentUser.isAdmin() || device.getUsernameEn().equals(currentUser.getUserNameEn())) {
            List<DeviceMessageA> ret = deviceDataService.showDataA(deviceSn);
            return StandardResponse.ok(ret);
        } else {
            return StandardResponse.fail();
        }
    }

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/show_all_dataA", method = RequestMethod.GET)
    public List<DeviceMessageA> showAllDataA() {
        return deviceDataService.getAllDataA();
    }

    @LoginRequired()
    @RequestMapping(value = "/show_dataB", method = RequestMethod.GET)
    public ResponseDto<Object> showDataB(String deviceSn) {
        User currentUser = AuthInterceptor.getCurrentUser();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceSn);
        Device device = deviceMapper.selectList(qw).get(0);
        if (currentUser.isAdmin() || device.getUsernameEn().equals(currentUser.getUserNameEn())) {
            List<DeviceMessageB> ret = deviceDataService.showDataB(deviceSn);
            return StandardResponse.ok(ret);
        } else {
            return StandardResponse.fail();
        }
    }

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/show_all_dataB", method = RequestMethod.GET)
    public List<DeviceMessageB> showAllDataB() {
        return deviceDataService.getAllDataB();
    }

    @LoginRequired()
    @RequestMapping(value = "/show_dataC", method = RequestMethod.GET)
    public ResponseDto<Object> showDataC(String deviceSn) {
        User currentUser = AuthInterceptor.getCurrentUser();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceSn);
        Device device = deviceMapper.selectList(qw).get(0);
        if (currentUser.isAdmin() || device.getUsernameEn().equals(currentUser.getUserNameEn())) {
            List<DeviceMessageC> ret =  deviceDataService.showDataC(deviceSn);
            return StandardResponse.ok(ret);
        } else {
            return StandardResponse.fail();
        }
    }

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/show_all_dataC", method = RequestMethod.GET)
    public List<DeviceMessageC> showAllDataC() {
        return deviceDataService.getAllDataC();
    }

    @LoginRequired()
    @RequestMapping(value = "/show_dataIa", method = RequestMethod.GET)
    public ResponseDto<Object> showDataIa(String deviceSn) {
        User currentUser = AuthInterceptor.getCurrentUser();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceSn);
        Device device = deviceMapper.selectList(qw).get(0);
        if (currentUser.isAdmin() || device.getUsernameEn().equals(currentUser.getUserNameEn())) {
            List<Ia> ret = deviceDataService.showDataIa(deviceSn);
            return StandardResponse.ok(ret);
        } else {
            return StandardResponse.fail();
        }
    }

    @LoginRequired()
    @RequestMapping(value = "/show_dataIr", method = RequestMethod.GET)
    public ResponseDto<Object> showDataIr(String deviceSn) {
        User currentUser = AuthInterceptor.getCurrentUser();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceSn);
        Device device = deviceMapper.selectList(qw).get(0);
        if (currentUser.isAdmin() || device.getUsernameEn().equals(currentUser.getUserNameEn())) {
            List<Ir> ret = deviceDataService.showDataIr(deviceSn);
            return StandardResponse.ok(ret);
        } else {
            return StandardResponse.fail();
        }
    }

    @LoginRequired()
    @RequestMapping(value = "/show_dataPh", method = RequestMethod.GET)
    public ResponseDto<Object> showDataPh(String deviceSn) {
        User currentUser = AuthInterceptor.getCurrentUser();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceSn);
        Device device = deviceMapper.selectList(qw).get(0);
        if (currentUser.isAdmin() || device.getUsernameEn().equals(currentUser.getUserNameEn())) {
            List<Ph> ret = deviceDataService.showDataPh(deviceSn);
            return StandardResponse.ok(ret);
        } else {
            return StandardResponse.fail();
        }
    }

    @LoginRequired()
    @RequestMapping(value = "/show_aByTime", method = RequestMethod.GET)
    public ResponseDto<Object> showDataAByTime(@RequestBody @Valid DeviceDataByTimeDto deviceDataByTimeDto) {
        User currentUser = AuthInterceptor.getCurrentUser();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceDataByTimeDto.getDeviceSn());
        Device device = deviceMapper.selectList(qw).get(0);
        if (currentUser.isAdmin() || device.getUsernameEn().equals(currentUser.getUserNameEn())) {
            List<DeviceMessageA> ret = deviceDataService.showDataAByTime(deviceDataByTimeDto.getDeviceSn(), deviceDataByTimeDto.getDays());
            return StandardResponse.ok(ret);
        } else {
            return StandardResponse.fail();
        }
    }

    @LoginRequired()
    @RequestMapping(value = "/show_bByTime", method = RequestMethod.GET)
    public ResponseDto<Object> showDataBByTime(@RequestBody @Valid DeviceDataByTimeDto deviceDataByTimeDto) {
        User currentUser = AuthInterceptor.getCurrentUser();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceDataByTimeDto.getDeviceSn());
        Device device = deviceMapper.selectList(qw).get(0);
        if (currentUser.isAdmin() ||device.getUsernameEn().equals(currentUser.getUserNameEn())) {
            List<DeviceMessageB> ret = deviceDataService.showDataBByTime(deviceDataByTimeDto.getDeviceSn(), deviceDataByTimeDto.getDays());
            return StandardResponse.ok(ret);
        } else {
            return StandardResponse.fail();
        }
    }

    @LoginRequired()
    @RequestMapping(value = "/show_cByTime", method = RequestMethod.GET)
    public ResponseDto<Object> showDataCByTime(@RequestBody @Valid DeviceDataByTimeDto deviceDataByTimeDto) {
        User currentUser = AuthInterceptor.getCurrentUser();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceDataByTimeDto.getDeviceSn());
        Device device = deviceMapper.selectList(qw).get(0);
        if (currentUser.isAdmin() || device.getUsernameEn().equals(currentUser.getUserNameEn())) {
            List<DeviceMessageC> ret = deviceDataService.showDataCByTime(deviceDataByTimeDto.getDeviceSn(), deviceDataByTimeDto.getDays());
            return StandardResponse.ok(ret);
        } else {
            return StandardResponse.fail();
        }
    }

    @LoginRequired()
    @RequestMapping(value = "/show_IaByTime", method = RequestMethod.GET)
    public ResponseDto<Object> showDataIaByTime(@RequestBody @Valid DeviceDataByTimeDto deviceDataByTimeDto) {
        User currentUser = AuthInterceptor.getCurrentUser();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceDataByTimeDto.getDeviceSn());
        Device device = deviceMapper.selectList(qw).get(0);
        if (currentUser.isAdmin() || device.getUsernameEn().equals(currentUser.getUserNameEn())) {
            List<Ia> ret = deviceDataService.showDataIaByTime(deviceDataByTimeDto.getDeviceSn(), deviceDataByTimeDto.getDays());
            return StandardResponse.ok(ret);
        } else {
            return StandardResponse.fail();
        }
    }

    @LoginRequired()
    @RequestMapping(value = "/show_IrByTime", method = RequestMethod.GET)
    public ResponseDto<Object> showDataTrByTime(@RequestBody @Valid DeviceDataByTimeDto deviceDataByTimeDto) {
        User currentUser = AuthInterceptor.getCurrentUser();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceDataByTimeDto.getDeviceSn());
        Device device = deviceMapper.selectList(qw).get(0);
        if (currentUser.isAdmin() || device.getUsernameEn().equals(currentUser.getUserNameEn())) {
            List<Ir> ret = deviceDataService.showDataIrByTime(deviceDataByTimeDto.getDeviceSn(), deviceDataByTimeDto.getDays());
            return StandardResponse.ok(ret);
        } else {
            return StandardResponse.fail();
        }
    }

    @LoginRequired()
    @RequestMapping(value = "/show_PhByTime", method = RequestMethod.GET)
    public ResponseDto<Object> showDataPhByTime(@RequestBody @Valid DeviceDataByTimeDto deviceDataByTimeDto) {
        User currentUser = AuthInterceptor.getCurrentUser();
        QueryWrapper<Device> qw = new QueryWrapper<>();
        qw.eq("device_id", deviceDataByTimeDto.getDeviceSn());
        Device device = deviceMapper.selectList(qw).get(0);
        if (currentUser.isAdmin() || device.getUsernameEn().equals(currentUser.getUserNameEn())) {
            List<Ph> ret = deviceDataService.showDataPhByTime(deviceDataByTimeDto.getDeviceSn(), deviceDataByTimeDto.getDays());
            return StandardResponse.ok(ret);
        } else {
            return StandardResponse.fail();
        }
    }

}
