package com.knight.login.mapper;

import com.knight.login.domain.LoginUserInfoImpl;
import org.apache.ibatis.annotations.Param;

public interface LoginUserMapper {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户对象
     */
    LoginUserInfoImpl queryUserByUsername(@Param("username") String username);

    /**
     * 查询用户信息
     *
     * @param phone 手机号
     * @return LoginUserInfoImpl
     */
    LoginUserInfoImpl queryUserByPhone(String phone);


}
