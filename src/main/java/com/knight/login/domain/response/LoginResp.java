package com.knight.login.domain.response;

import com.knight.base.KnightResponse;
import com.knight.login.domain.LoginUserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResp extends KnightResponse {

    LoginUserInfo userInfo;

    private String csrfToken;

    private String csrfHeader;

    private String cnonce;

    public LoginResp(String resultCode, String resultInfo) {
        super(resultCode, resultInfo);
    }
}
