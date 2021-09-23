package com.knight.login.controller;

import com.knight.base.IPUtil;
import com.knight.login.domain.LoginUserInfo;
import com.knight.login.domain.request.LoginReq;
import com.knight.login.domain.response.LoginResp;
import com.knight.login.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController {
    @Autowired
    private UserLoginService userLoginService;
    /**
     * 登录接口
     *
     * @param loginReq 登录请求参数
     * @param servletRequest 请求Request
     * @param response 响应
     * @return
     */
    @PostMapping("/login")
    public LoginResp login(@RequestBody LoginReq loginReq, HttpServletRequest servletRequest, HttpServletResponse response){
        String ip = IPUtil.getRemoteIp(servletRequest);
        LoginResp loginResp = new LoginResp("","");

        LoginUserInfo loginUserInfo = userLoginService.login(loginReq, ip);
        loginResp.setUserInfo(loginUserInfo);

        return loginResp;
    }
}
