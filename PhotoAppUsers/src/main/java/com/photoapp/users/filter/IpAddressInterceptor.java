package com.photoapp.users.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class IpAddressInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getHeader("X-Forward-For");

        if(ipAddress== null){

            ipAddress = request.getRemoteAddr();
        }
        System.out.println("IP ADDRESS" + ipAddress);
        return true;
    }
}