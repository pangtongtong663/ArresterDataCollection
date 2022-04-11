package com.example.device.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.device.Dao.*;
import com.example.device.Dto.DeviceDataDto;
import com.example.device.Dto.DeviceMessageDto;
import com.example.device.po.*;
import com.example.device.service.DeviceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceDataServiceImpl implements DeviceDataService {

    @Autowired
    DeviceMessageAMapper deviceMessageAMapper;

    @Autowired
    DeviceMessageBMapper deviceMessageBMapper;

    @Autowired
    DeviceMessageCMapper deviceMessageCMapper;

    @Autowired
    IrMapper irMapper;

    @Autowired
    IaMapper iaMapper;

    @Autowired
    PhMapper phMapper;


    @Override
    public String recordDeviceData(DeviceDataDto deviceDataDto) {
        String thisDeviceSn = deviceDataDto.getDeviceSn();
        String thisModuleSn = deviceDataDto.getModuleSn();
        String thisSign = deviceDataDto.getSign();
        int i = 1;
        for (DeviceMessageDto deviceMessageDto : deviceDataDto.getMsgs()) {
            DeviceMessageA deviceMessageA = DeviceMessageA.builder().deviceSn(thisDeviceSn).sign(thisSign)
                    .moduleSn(thisModuleSn).orderA(i)
                    .dataType(deviceMessageDto.getDataType())
                    .IaA(deviceMessageDto.getIaA()).IrA(deviceMessageDto.getIrA())
                    .PhA(deviceMessageDto.getPhA()).PhAB(deviceMessageDto.getPhAB())
                    .PhCA(deviceMessageDto.getPhCA()).ItpA(deviceMessageDto.getItpA())
                    .ItCntA(deviceMessageDto.getItCntA()).TempA(deviceMessageDto.getTempA())
                    .StaA(deviceMessageDto.getStaA()).MoaName(deviceMessageDto.getMoaName())
                    .IaIniA(deviceMessageDto.getIaIniA()).IaWarnA(deviceMessageDto.getIaWarnA())
                    .IaStopA(deviceMessageDto.getIaStopA()).Refer(deviceMessageDto.getRefer())
                    .PhIni(deviceMessageDto.getPhIni()).IrWarnA(deviceMessageDto.getIrWarnA())
                    .IrStopA(deviceMessageDto.getIrStopA()).build();

            DeviceMessageB deviceMessageB = DeviceMessageB.builder().deviceSn(thisDeviceSn).sign(thisSign)
                    .moduleSn(thisModuleSn).orderB(i)
                    .dataType(deviceMessageDto.getDataType())
                    .IaB(deviceMessageDto.getIaB()).IrB(deviceMessageDto.getIrB())
                    .PhB(deviceMessageDto.getPhB()).PhAB(deviceMessageDto.getPhAB())
                    .PhBC(deviceMessageDto.getPhBC()).ItpB(deviceMessageDto.getItpB())
                    .ItCntB(deviceMessageDto.getItCntB()).TempB(deviceMessageDto.getTempB())
                    .StaB(deviceMessageDto.getStaB()).MoaName(deviceMessageDto.getMoaName())
                    .IaIniB(deviceMessageDto.getIaIniB()).IaWarnB(deviceMessageDto.getIaWarnB())
                    .IaStopB(deviceMessageDto.getIaStopB()).Refer(deviceMessageDto.getRefer())
                    .PhIni(deviceMessageDto.getPhIni()).IrWarnB(deviceMessageDto.getIrWarnB())
                    .IrStopB(deviceMessageDto.getIrStopB()).build();

            DeviceMessageC deviceMessageC = DeviceMessageC.builder().deviceSn(thisDeviceSn).sign(thisSign)
                    .moduleSn(thisModuleSn).orderC(i)
                    .dataType(deviceMessageDto.getDataType())
                    .IaC(deviceMessageDto.getIaC()).IrC(deviceMessageDto.getIrC())
                    .PhC(deviceMessageDto.getPhC()).PhCA(deviceMessageDto.getPhCA())
                    .PhBC(deviceMessageDto.getPhBC()).ItpC(deviceMessageDto.getItpC())
                    .ItCntC(deviceMessageDto.getItCntC()).TempC(deviceMessageDto.getTempC())
                    .StaC(deviceMessageDto.getStaC()).MoaName(deviceMessageDto.getMoaName())
                    .IaIniC(deviceMessageDto.getIaIniC()).IaWarnC(deviceMessageDto.getIaWarnC())
                    .IaStopC(deviceMessageDto.getIaStopC()).Refer(deviceMessageDto.getRefer())
                    .PhIni(deviceMessageDto.getPhIni()).IrWarnC(deviceMessageDto.getIrWarnC())
                    .IrStopC(deviceMessageDto.getIrStopC()).build();

            Ia ia = Ia.builder().deviceSn(thisDeviceSn).IaA(deviceMessageDto.getIaA())
                    .IaB(deviceMessageDto.getIaB()).
                    IaC(deviceMessageDto.getIaC()).build();

            Ir ir = Ir.builder().deviceSn(thisDeviceSn).
                    IrA(deviceMessageDto.getIrA())
                    .IrB(deviceMessageDto.getIrB()).
                    IrC(deviceMessageDto.getIrC()).build();

            Ph ph = Ph.builder().deviceSn(thisDeviceSn)
                    .PhA(deviceMessageDto.getPhA())
                    .PhB(deviceMessageDto.getPhB())
                    .PhC(deviceMessageDto.getPhC()).build();

            deviceMessageAMapper.insert(deviceMessageA);
            deviceMessageBMapper.insert(deviceMessageB);
            deviceMessageCMapper.insert(deviceMessageC);
            iaMapper.insert(ia);
            irMapper.insert(ir);
            phMapper.insert(ph);
            i++;
        }
        return "成功保存";
    }
    @Override
    public List<DeviceMessageA> showDataA(String deviceSn) {
        QueryWrapper<DeviceMessageA> qw = new QueryWrapper<>();
        qw.eq("deviceSn", deviceSn);
        return deviceMessageAMapper.selectList(qw);
    }

    @Override
    public List<DeviceMessageB> showDataB(String deviceSn){
        QueryWrapper<DeviceMessageB> qw = new QueryWrapper<>();
        qw.eq("deviceSn", deviceSn);
        return deviceMessageBMapper.selectList(qw);
    }

    @Override
    public List<DeviceMessageC> showDataC(String deviceSn) {
        QueryWrapper<DeviceMessageC> qw = new QueryWrapper<>();
        qw.eq("deviceSn", deviceSn);
        return deviceMessageCMapper.selectList(qw);
    }

    @Override
    public List<Ir> showDataIr(String deviceSn) {
        QueryWrapper<Ir> qw = new QueryWrapper<>();
        qw.eq("deviceSn", deviceSn);
        return irMapper.selectList(qw);
    }

    @Override
    public List<Ia> showDataIa(String deviceSn) {
        QueryWrapper<Ia> qw = new QueryWrapper<>();
        qw.eq("deviceSn", deviceSn);
        return iaMapper.selectList(qw);
    }

    @Override
    public List<Ph> showDataPh(String deviceSn) {
        QueryWrapper<Ph> qw = new QueryWrapper<>();
        qw.eq("deviceSn", deviceSn);
        return phMapper.selectList(qw);
    }

    @Override
    public List<Ir> showDataIrByTime(String deviceSn, Integer days) {
        List<Ir> allMsgs = showDataIr(deviceSn);
        List<Ir> ret = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Ir ir: allMsgs) {
            Duration duration = Duration.between(ir.getCreateTime(), now);
            if (duration.toDays() <= days) {
                ret.add(ir);
            }
        }
        return ret;
    }

    @Override
    public List<Ia> showDataIaByTime(String deviceSn, Integer days) {
        List<Ia> allMsgs = showDataIa(deviceSn);
        List<Ia> ret = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Ia ia: allMsgs) {
            Duration duration = Duration.between(ia.getCreateTime(), now);
            if (duration.toDays() <= days) {
                ret.add(ia);
            }
        }
        return ret;
    }

    @Override
    public List<Ph> showDataPhByTime(String deviceSn, Integer days) {
        List<Ph> allMsgs = showDataPh(deviceSn);
        List<Ph> ret = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Ph ph: allMsgs) {
            Duration duration = Duration.between(ph.getCreateTime(), now);
            if (duration.toDays() <= days) {
                ret.add(ph);
            }
        }
        return ret;
    }

    @Override
    public List<DeviceMessageA> showDataAByTime(String deviceSn, Integer days){
        List<DeviceMessageA> allMsgs = showDataA(deviceSn);
        List<DeviceMessageA> ret = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (DeviceMessageA deviceMessageA: allMsgs) {
            Duration duration = Duration.between(deviceMessageA.getCreateTime(), now);
            if (duration.toDays() <= days) {
                ret.add(deviceMessageA);
            }
        }
        return ret;
    }

    @Override
    public List<DeviceMessageB> showDataBByTime(String deviceSn, Integer days) {
        List<DeviceMessageB> allMsgs = showDataB(deviceSn);
        List<DeviceMessageB> ret = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (DeviceMessageB deviceMessageB: allMsgs) {
            Duration duration = Duration.between(deviceMessageB.getCreateTime(), now);
            if (duration.toDays() <= days) {
                ret.add(deviceMessageB);
            }
        }
        return ret;
    }

    @Override
    public List<DeviceMessageC> showDataCByTime(String deviceSn,  Integer days){
        List<DeviceMessageC> allMsgs = showDataC(deviceSn);
        List<DeviceMessageC> ret = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (DeviceMessageC deviceMessageC: allMsgs) {
            Duration duration = Duration.between(deviceMessageC.getCreateTime(), now);
            if (duration.toDays() <= days) {
                ret.add(deviceMessageC);
            }
        }
        return ret;
    }


    @Override
    public List<DeviceMessageA> getAllDataA() {
        return deviceMessageAMapper.selectList(null);
    }

    @Override
    public List<DeviceMessageB> getAllDataB() {
        return deviceMessageBMapper.selectList(null);
    }

    @Override
    public List<DeviceMessageC> getAllDataC() {
        return deviceMessageCMapper.selectList(null);
    }

}
