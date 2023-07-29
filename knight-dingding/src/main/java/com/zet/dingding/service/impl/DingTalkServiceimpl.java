package com.zet.dingding.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.taobao.api.ApiException;
import com.zet.dingding.entity.UrlConstant;
import com.zet.dingding.service.DingTalkService;
import com.zet.dingding.utils.HttpClient;
import com.zet.dingding.utils.dingtalk.AccessTokenUtil;
import com.zet.dingding.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service("DingTalkService")
public class DingTalkServiceimpl implements DingTalkService {

    @Autowired
    HttpClient client;

//    @Value("${gam.methods}")
//    private String methods;

    @Autowired
    RedisUtil redis;



    /**
     * 钉钉登录获取当前账号信息
     *
     * @param authCode   用于获取信息的authCode
     * @param authCode4A 用于和平台4A交互的authCode
     * @return 钉钉小程序用户信息
     */
    public Map<String, Object> getLinkUserInfo(String authCode, String authCode4A) {
        // 获取access_token，注意正式代码要有异常流处理
        String accessToken = AccessTokenUtil.getToken();

        // 获取用户信息
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.URL_GET_USER_INFO);
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(authCode);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response = new OapiUserGetuserinfoResponse();
        String userId = "";
        try {
            response = client.execute(request, accessToken);
            // 查询得到当前用户的userId
            // 获得到userId之后应用应该处理应用自身的登录会话管理（session）,避免后续的业务交互（前端到应用服务端）每次都要重新获取用户身份，提升用户体验
            userId = response.getUserid();
            redis.set(authCode4A, userId);
        } catch (ApiException e) {
            log.error("login ApiException{}" + e.getMessage());
            return null;
        }
        Map<String, Object> returnMap = new HashMap<>(1024);
        returnMap.put("userId", userId);

        // 根据userid查询用户信息
        DingTalkClient talkClient = new DefaultDingTalkClient(UrlConstant.URL_TOPAPI_USER_GET);
        OapiV2UserGetRequest req1 = new OapiV2UserGetRequest();
        req1.setUserid(userId);
        req1.setLanguage("zh_CN");

        new OapiV2UserGetResponse();
        OapiV2UserGetResponse rsp;
        try {
            rsp = talkClient.execute(req1, accessToken);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        System.out.println(rsp.getBody());

        String mobile = "";

        if (rsp.getResult() != null) {
            mobile = rsp.getResult().getMobile();
        }
        String name = "";

        if (rsp.getResult() != null) {
            name = rsp.getResult().getName();
        }
        returnMap.put("userName", name);
        returnMap.put("mobile", mobile);
        return returnMap;
    }

    /**
     * 平台交互的票据校验接口
     *
     * @param authCode
     * @return
     */
    public Map<String, Object> getLinkUserInfoFor4A(String authCode) {
        // 获取access_token，注意正式代码要有异常流处理
        String accessToken = AccessTokenUtil.getToken();

        // 获取用户信息
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.URL_GET_USER_INFO);
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(authCode);
        log.debug("=======DingTalkServiceimpl.getLinkUserInfoFor4A(),authCode=========：" + authCode);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response = new OapiUserGetuserinfoResponse();
        String userId = "";
        try {
            String authCode1 = (String) redis.get(authCode);
            if (StringUtils.isNotBlank(authCode1)) {
                userId = authCode1;
            } else {
                response = client.execute(request, accessToken);
                // 查询得到当前用户的userId
                // 获得到userId之后应用应该处理应用自身的登录会话管理（session）,避免后续的业务交互（前端到应用服务端）每次都要重新获取用户身份，提升用户体验
                userId = response.getUserid();
                redis.set(authCode, userId);
            }
        } catch (ApiException e) {
            log.error("login ApiException{}" + e.getMessage());
            return null;
        }
        Map<String, Object> returnMap = new HashMap<>(1024);
        returnMap.put("userId", userId);

        // 根据userid查询用户信息
        DingTalkClient talkClient = new DefaultDingTalkClient(UrlConstant.URL_TOPAPI_USER_GET);
        OapiV2UserGetRequest req1 = new OapiV2UserGetRequest();
        req1.setUserid(userId);
        req1.setLanguage("zh_CN");

        OapiV2UserGetResponse rsp = new OapiV2UserGetResponse();
        try {
            rsp = talkClient.execute(req1, accessToken);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        log.debug("=====DingTalkServiceimpl.getLinkUserInfoFor4A(),rsp{}", rsp.getBody());

        String mobile = "";
        if (rsp.getResult() != null) {
            mobile = rsp.getResult().getMobile();
        }
        String name = "";

        if (rsp.getResult() != null) {
            name = rsp.getResult().getName();
        }
        returnMap.put("userName", name);
        returnMap.put("mobile", mobile);
        return returnMap;
    }
}