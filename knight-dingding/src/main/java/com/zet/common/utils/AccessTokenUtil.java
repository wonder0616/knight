package com.zet.common.utils;

import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.aliyun.tea.TeaException;
import com.zet.config.DingPanConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取access_token工具类
 *
 * @author
 */

@EnableScheduling
@Slf4j
@Component
@Configuration
@Data
public class AccessTokenUtil {


    @Value("${ding-pan.urlConstant.GET_ACCESS_TOKEN_URL}")
    private String getAccessTokenUrl;

//    @Value("${ding-pan.appConstant.APP_KEY}")
//    private String appKey;
//
//    @Value("${ding-pan.appConstant.APP_SECRET}")
//    private String appSecret;

//    public static String accessToken;

    public static Map<String, String> corpIdsAndaccessTokens = new HashMap<>();

    @Autowired
    public DingPanConfig dingpanConfig;

    /**
     * 定期获取钉钉accessToken,项目初始化时启动
     *
     * @throws Exception
     */
    @Scheduled(cron = "0 40 */2 * * ?")
//    @Scheduled(cron = "1/10 * * * * *") //每10s一次
    @PostConstruct
    public void task() {

        try {

            System.out.println("xxxxxxx");
            if (dingpanConfig != null) {
//                 1.先清除内存中的token
                for (int k = corpIdsAndaccessTokens.size() - 1; k >= 0; k--) {
                    corpIdsAndaccessTokens.remove(k);
                }

                log.info("StartcorpIdsAndaccessTokens,{}", corpIdsAndaccessTokens);

                System.out.println("配置个数" + dingpanConfig.getGroupConstants().size());

                for (int i = 0; i < dingpanConfig.getGroupConstants().size(); i++) {
                    Map<String, String> groupConstant = dingpanConfig.getGroupConstants().get(i);
//                    Map<String, String> corpIdAndaccessToken = new HashMap<>();
                    String corpId = groupConstant.get("corpId");
                    String appKey = groupConstant.get("APP_KEY");
                    String appSecret = groupConstant.get("APP_SECRET");
                    String accessToken = getAccessTokenYunPan(appKey, appSecret);

                    corpIdsAndaccessTokens.put(corpId, accessToken);
                }

            }
            log.info("FinalcorpIdsAndaccessTokens,{}" + corpIdsAndaccessTokens);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get token task,{}", e.getMessage());
        }

    }

    public com.aliyun.dingtalkoauth2_1_0.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkoauth2_1_0.Client(config);
    }

    public String getAccessTokenYunPan(String appKey, String appSecret) throws Exception {

        com.aliyun.dingtalkoauth2_1_0.Client client = createClient();
        com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest getAccessTokenRequest = new com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest().
                setAppKey(appKey)
                .setAppSecret(appSecret);

        try {
            GetAccessTokenResponse response = client.getAccessToken(getAccessTokenRequest);
            log.info("getAccessToken expireIn:{},{}", response.getBody().getExpireIn(), appKey);
            return response.getBody().getAccessToken();
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("getAccessToken,{}", err.code);
                log.error("getAccessToken ,{}", err.message);
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("getAccessToken,{}", err.code);
                log.error("getAccessToken ,{}", err.message);
            }

        }

        return null;
    }

}