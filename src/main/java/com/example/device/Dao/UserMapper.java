package com.example.device.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.device.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
