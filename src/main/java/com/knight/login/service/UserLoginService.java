package com.knight.login.service;

import com.knight.login.domain.LoginUserInfo;
import com.knight.login.domain.request.LoginReq;

public interface UserLoginService {
    /**
     * 用户登录
     *
     * @param loginReq 登陆请求类封装
     * @param ip 登录的IP
     * @return LoginUserInfo
     */
    LoginUserInfo login(LoginReq loginReq, String ip);

}
