package com.zet.dingding.service;

import com.alibaba.fastjson2.JSONObject;

import java.util.Map;



/**
 * 钉钉的服务
 * @author renxiaoming
 */
public interface DingTalkService {
    public Map<String, Object> getLinkUserInfo(String authCode ,String authCode4A);
    public Map<String, Object> getLinkUserInfoFor4A(String authCode );

}