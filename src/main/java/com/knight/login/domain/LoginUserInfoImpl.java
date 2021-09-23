package com.knight.login.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LoginUserInfoImpl {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -8588815873843118345L;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 生效时间
     */
    private String effectTime;

    /**
     * 失效时间
     */
    private String expiredTime;

    /**
     * 用户级别
     */
    private Integer userLevel;

    /**
     * 用户状态
     */
    private Integer userStatus;

    /**
     * 强制修改密码标识
     */
    private Integer modifyPwdFlag;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户自定义属性
     *
     * @return userProfiles
     */
    private Map<String, String> userProfiles;

    /**
     * 隐私声明版本号
     */
    private String privcayVersion;

    /**
     * 隐私声明签署时间
     */
    private String privcayTime;

    /**
     * 角色等级最小值
     */
    private String roleLevel;

}
