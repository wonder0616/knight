package com.knight.login.domain;

import lombok.Data;

@Data
public class LoginUserInfo {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户级别
     */
    private Integer userLeverl;

    /**
     * 用户状态
     */
    private Integer userStatus;

    /**
     * 生效时间
     */
    private String effectTime;

    /**
     * 失效时间
     */
    private String expiredTime;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LoginUserInfo{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", userLeverl=").append(userLeverl);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", effectTime='").append(effectTime).append('\'');
        sb.append(", expiredTime='").append(expiredTime).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
