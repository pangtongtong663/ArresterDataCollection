package com.example.device.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.device.po.Ph;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface PhMapper extends BaseMapper<Ph> {
}
