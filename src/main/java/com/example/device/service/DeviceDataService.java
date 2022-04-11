package com.example.device.service;

import com.example.device.Dto.DeviceDataDto;
import com.example.device.po.*;

import java.util.List;

public interface DeviceDataService {
    String recordDeviceData(DeviceDataDto deviceDataDto);

    List<DeviceMessageA> showDataA(String deviceSn);

    List<DeviceMessageB> showDataB(String deviceSn);

    List<DeviceMessageC> showDataC(String deviceSn);

    List<Ir> showDataIr(String deviceSn);

    List<Ia> showDataIa(String deviceSn);

    List<Ph> showDataPh(String deviceSn);

    List<Ir> showDataIrByTime(String deviceSn, Integer days);

    List<Ia> showDataIaByTime(String deviceSn, Integer days);

    List<Ph> showDataPhByTime(String deviceSn, Integer days);

    List<DeviceMessageA> showDataAByTime(String deviceSn, Integer days);

    List<DeviceMessageB> showDataBByTime(String deviceSn, Integer days);

    List<DeviceMessageC> showDataCByTime(String deviceSn, Integer days);

    List<DeviceMessageA> getAllDataA();

    List<DeviceMessageB> getAllDataB();

    List<DeviceMessageC> getAllDataC();



}
