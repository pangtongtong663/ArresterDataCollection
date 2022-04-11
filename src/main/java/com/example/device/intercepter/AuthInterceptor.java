package com.example.device.intercepter;

import com.example.device.Dao.UserMapper;
import com.example.device.Dto.UserBasicInfo;
import com.example.device.Exceptions.LoginException;
import com.example.device.annotation.LoginRequired;
import com.example.device.po.User;
import com.example.device.utils.JwtUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static com.example.device.Exceptions.ResultCode.FORBIDDEN;

public class AuthInterceptor implements HandlerInterceptor {
    // 使用完currentUser后请务必释放currentUser否则容易出现内存泄漏
    private static final ThreadLocal<UserBasicInfo> currentUserBasicInfo = new ThreadLocal<>();
    private static UserMapper userMapper;
    private static JwtUtils jwt;

    public AuthInterceptor(JwtUtils jwtUtils, UserMapper userMapper) {
        this.jwt = jwtUtils;
        this.userMapper = userMapper;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (!(handler instanceof HandlerMethod)) return true;

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(LoginRequired.class)) {
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            String token = request.getHeader("Authorization");
            boolean res = jwt.checkToken(token);
            if (res) {
                User user = userMapper.selectById(jwt.getUserIdFromToken(token));
                currentUserBasicInfo.set(new UserBasicInfo(user.getId()));
                if (loginRequired.needAdmin()) {
                    return authAdmin(user);
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean authAdmin(User user) {
        if (!user.isAdmin()) {
            throw new LoginException(FORBIDDEN);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        currentUserBasicInfo.remove();
    }

    public static User getCurrentUser() {
        return userMapper.selectById(currentUserBasicInfo.get().getUserId());
    }
}
