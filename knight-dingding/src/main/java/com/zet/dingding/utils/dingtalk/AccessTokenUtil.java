package com.zet.dingding.utils.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import com.zet.dingding.entity.UrlConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取access_token工具类
 * @author knight
 */
public class AccessTokenUtil {
    private static final Logger bizLogger = LoggerFactory.getLogger(AccessTokenUtil.class);

    /**
     * 开发者后台->应用开发-企业内部应用->选择您创建的小程序->应用首页-查看详情->查看AppKey
     */
    public static final String APP_KEY = "dingg0g5rdo6lf7iiwga";
    /**
     * 开发者后台->应用开发-企业内部应用->选择您创建的小程序->应用首页-查看详情->查看AppSecret
     */
    public static final String APP_SECRET="uW9UsjjUHelyQ59MRCrsx-HNOi_ti6WtCDNwg2g6s8c0PKVx2qVI9pduPdozRUy-";

    // TODO 需要修改上面的配置
    public static String getToken() throws RuntimeException {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(UrlConstant.URL_GET_TOKKEN);
            OapiGettokenRequest request = new OapiGettokenRequest();

            request.setAppkey(APP_KEY);
            request.setAppsecret(APP_SECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            String accessToken = response.getAccessToken();
            return accessToken;
        } catch (ApiException e) {
            bizLogger.error("getAccessToken failed", e);
            throw new RuntimeException();
        }

    }

    public static void main(String[] args)throws ApiException{
        String accessToken = AccessTokenUtil.getToken();
        System.out.println(accessToken);
    }
}
