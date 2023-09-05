package com.zet.service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.request.OapiV2UserGetuserinfoRequest;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetuserinfoResponse;
import com.zet.common.utils.AccessTokenUtil;
import com.zet.common.utils.RedisUtil;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description  用户管理
 * @Author
 * @Date  2023/8/7 16:28
 * @Version 1.0
**/
@Service
@Slf4j
public class UserManager {

    @Value("${ding-pan.urlConstant.GET_USER_INFO_URL}")
    private  String userGetUserInfoUrl;

    @Value("${ding-pan.urlConstant.USER_GET_URL}")
    private  String userGetUrl;

    @Autowired
    RedisUtil redis;

    @Autowired
    private  AccessTokenUtil accessTokenUtil;

    /**
     * 根据免登授权码获取用户id
     *
     * @param authCode 免登授权码
     * @return
     */
    public String getUserId(String authCode, String corpId) throws Exception {

        // 1. 获取access_token
        String accessToken = null;
        Map<String,String> corpIdsAndaccessTokens = AccessTokenUtil.corpIdsAndaccessTokens;
        //组织命中内存中的corpId
        accessToken = corpIdsAndaccessTokens.get(corpId);
        log.info("corpIdsAndaccessToken.get(corpId),{}",accessToken);
        if(accessToken != null) {
            // 2. 获取用户信息
            DingTalkClient client = new DefaultDingTalkClient(userGetUserInfoUrl);
            OapiV2UserGetuserinfoRequest req = new OapiV2UserGetuserinfoRequest();
            req.setCode(authCode);
            OapiV2UserGetuserinfoResponse rsp = client.execute(req, accessToken);
            // 3. 返回用户id
            return rsp.getResult().getUserid();
        }
        return null;
    }

    /**
     * 根据用户id获取用户名称
     *
     * @param userId 用户id
     * @return
     */
    public String getUserName(String userId, String corpId) throws ApiException {
        // 1. 获取access_token
        String accessToken = null;
        Map<String,String> corpIdsAndaccessTokens = AccessTokenUtil.corpIdsAndaccessTokens;
        accessToken = corpIdsAndaccessTokens.get(corpId);
        // 2. 获取用户详情
        DingTalkClient client = new DefaultDingTalkClient(userGetUrl);
        OapiV2UserGetRequest req = new OapiV2UserGetRequest();
        req.setUserid(userId);
        req.setLanguage("zh_CN");
        OapiV2UserGetResponse rsp = client.execute(req, accessToken);

        return accessToken;
    }


    /**
     * 根据用户id获取用户手机号
     *
     * @param userId 用户id
     * @return
     */
    public String getUserMobile(String userId, String corpId) throws ApiException {
        // 1. 获取access_token
        String accessToken = null;
        Map<String,String> corpIdsAndaccessTokens = AccessTokenUtil.corpIdsAndaccessTokens;
        //组织命中内存中的corpId
        accessToken = corpIdsAndaccessTokens.get(corpId);
        if(accessToken != null){
            // 2. 获取用户详情
            DingTalkClient client = new DefaultDingTalkClient(userGetUrl);
            OapiV2UserGetRequest req = new OapiV2UserGetRequest();
            req.setUserid(userId);
            req.setLanguage("zh_CN");
            OapiV2UserGetResponse rsp = client.execute(req, accessToken);

            // 3. 返回用户名称
            return rsp.getResult().getMobile();
        }
        return accessToken;
    }
//    /**
//     * 钉钉登录获取当前账号信息
//     *
//     * @param authCode   用于获取信息的authCode
//     * @param authCode4A 用于和平台4A交互的authCode
//     * @return 钉钉小程序用户信息
//     */
//    public Map<String, Object> getLinkUserInfo(String authCode , String authCode4A) throws ApiException {
//        // 获取access_token，注意正式代码要有异常流处理
//        String accessToken = AccessTokenUtil.accessToken;
//
//        // 获取用户信息
//        DingTalkClient client = new DefaultDingTalkClient(userGetUrl);
//        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
//        request.setCode(authCode);
//        request.setHttpMethod("GET");
//        OapiUserGetuserinfoResponse response = new OapiUserGetuserinfoResponse();
//        String userId = "";
//        try {
//            response = client.execute(request, accessToken);
//            // 查询得到当前用户的userId
//            // 获得到userId之后应用应该处理应用自身的登录会话管理（session）,避免后续的业务交互（前端到应用服务端）每次都要重新获取用户身份，提升用户体验
//            userId = response.getUserid();
//            redis.set(authCode4A, userId);
//        } catch (ApiException e) {
//            log.error("login ApiException{}" + e.getMessage());
//            return null;
//        }
//        Map<String, Object> returnMap = new HashMap<>(1024);
//        returnMap.put("userId", userId);
//
//        // 根据userid查询用户信息
//        DingTalkClient talkClient = new DefaultDingTalkClient(userGetUrl);
//        OapiV2UserGetRequest req1 = new OapiV2UserGetRequest();
//        req1.setUserid(userId);
//        req1.setLanguage("zh_CN");
//
//        new OapiV2UserGetResponse();
//        OapiV2UserGetResponse rsp;
//        try {
//            rsp = talkClient.execute(req1, accessToken);
//        } catch (ApiException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(rsp.getBody());
//
//        String mobile = "";
//
//        if (rsp.getResult() != null) {
//            mobile = rsp.getResult().getMobile();
//        }
//        String name = "";
//
//        if (rsp.getResult() != null) {
//            name = rsp.getResult().getName();
//        }
//        returnMap.put("userName", name);
//        returnMap.put("mobile", mobile);
//        return returnMap;
//    }
//    /**
//     * 平台交互的票据校验接口
//     *
//     * @param authCode
//     * @return
//     */
//    public Map<String, Object> getLinkUserInfoFor4A(String authCode) throws ApiException {
//        // 获取access_token，注意正式代码要有异常流处理
//        String accessToken = AccessTokenUtil.accessToken;
//
//        // 获取用户信息
//        DingTalkClient client = new DefaultDingTalkClient(userGetUserInfoUrl);
//        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
//        request.setCode(authCode);
//        log.debug("=======DingTalkServiceimpl.getLinkUserInfoFor4A(),authCode=========：" + authCode);
//        request.setHttpMethod("GET");
//        OapiUserGetuserinfoResponse response = new OapiUserGetuserinfoResponse();
//        String userId = "";
//        try {
//            String authCode1 = (String) redis.get(authCode);
//            if (StringUtils.isNotBlank(authCode1)) {
//                userId = authCode1;
//            } else {
//                response = client.execute(request, accessToken);
//                // 查询得到当前用户的userId
//                // 获得到userId之后应用应该处理应用自身的登录会话管理（session）,避免后续的业务交互（前端到应用服务端）每次都要重新获取用户身份，提升用户体验
//                userId = response.getUserid();
//                redis.set(authCode, userId);
//            }
//        } catch (ApiException e) {
//            log.error("login ApiException{}" + e.getMessage());
//            return null;
//        }
//        Map<String, Object> returnMap = new HashMap<>(1024);
//        returnMap.put("userId", userId);
//
//        // 根据userid查询用户信息
//        DingTalkClient talkClient = new DefaultDingTalkClient(userGetUrl);
//        OapiV2UserGetRequest req1 = new OapiV2UserGetRequest();
//        req1.setUserid(userId);
//        req1.setLanguage("zh_CN");
//
//        OapiV2UserGetResponse rsp = new OapiV2UserGetResponse();
//        try {
//            rsp = talkClient.execute(req1, accessToken);
//        } catch (ApiException e) {
//            throw new RuntimeException(e);
//        }
//        log.debug("=====DingTalkServiceimpl.getLinkUserInfoFor4A(),rsp{}", rsp.getBody());
//
//        String mobile = "";
//        if (rsp.getResult() != null) {
//            mobile = rsp.getResult().getMobile();
//        }
//        String name = "";
//
//        if (rsp.getResult() != null) {
//            name = rsp.getResult().getName();
//        }
//        returnMap.put("userName", name);
//        returnMap.put("mobile", mobile);
//        return returnMap;
//    }
}
