package com.knight.login.service.impl;

import com.knight.login.domain.LoginUserInfo;
import com.knight.login.domain.LoginUserInfoImpl;
import com.knight.login.domain.request.LoginReq;
import com.knight.login.mapper.LoginUserMapper;
import com.knight.login.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private LoginUserMapper loginUserMapper;

    /**
     * login
     *
     * @param loginReq 登陆请求类封装
     * @param ip IP
     * @return LoginUserInfo
     */
    @Override
    public LoginUserInfo login(LoginReq loginReq, String ip) {
        // 查询用户信息
        LoginUserInfoImpl loginUserInfo = queryUserInfo(loginReq.getUsername(), null);

        return getLoginUserInfo(loginUserInfo);
    }

    private LoginUserInfo getLoginUserInfo(LoginUserInfoImpl loginUserInfo) {
        LoginUserInfo userInfo = new LoginUserInfo();
        return userInfo;
    }

    /**
     * 查询用户信息，如果不存在构造一个空的对象
     *
     * @param userName 用户名
     * @param phone 手机号码
     * @return LoginUserInfoImpl
     */
    public LoginUserInfoImpl queryUserInfo(String userName, String phone) {
        LoginUserInfoImpl loginUserInfo;
        if (userName != null) {
            loginUserInfo = loginUserMapper.queryUserByUsername(userName);
        } else {
            loginUserInfo = loginUserMapper.queryUserByPhone(phone);
        }
        if (loginUserInfo != null) {
            return loginUserInfo;
        }
        loginUserInfo = new LoginUserInfoImpl();
        loginUserInfo.setUsername(userName);
        loginUserInfo.setPhone(phone);
        return loginUserInfo;
    }
}
