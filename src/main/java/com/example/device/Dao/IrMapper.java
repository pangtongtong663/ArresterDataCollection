package com.example.device.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.device.po.Ir;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface IrMapper extends BaseMapper<Ir> {
}
