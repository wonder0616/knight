package com.knight.login.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq {
    /**
     * 登录用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String verifyCode;
}
