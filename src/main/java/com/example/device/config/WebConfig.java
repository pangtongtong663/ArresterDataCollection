package com.example.device.config;

import com.example.device.Dao.UserMapper;
import com.example.device.intercepter.AuthInterceptor;
import com.example.device.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private JwtUtils jwt;

    @Resource
    private UserMapper userMapper;


    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(jwt, userMapper)).addPathPatterns("/**");
    }
}
