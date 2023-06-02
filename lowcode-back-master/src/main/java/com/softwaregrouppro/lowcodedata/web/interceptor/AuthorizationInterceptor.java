package com.softwaregrouppro.lowcodedata.web.interceptor;

import com.softwaregrouppro.lowcodedata.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qing
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    public static final String USER_KEY = "userId";


    private final JwtUtils jwtUtils;


    public AuthorizationInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        System.out.println(request.getHeaderNames());
        System.out.println("request");
        Long userId=-1l;
        // 设置userId到request里，后续根据userId，获取用户信息
        if(request.getHeader("userId")!=null){
            userId=Long.parseLong(request.getHeader("userId"));
        }
        String test=request.getHeader("test");
        System.out.println("test:"+test);

        request.setAttribute(USER_KEY, userId);
        return true;
    }
}
