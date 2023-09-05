package com.zet.controller;

import com.aliyun.teaopenapi.models.Config;
import com.zet.model.RpcServiceResult;
import com.zet.service.UserManager;
import com.zet.common.utils.AccessTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @Description 钉钉企业内部小程序DEMO, 实现了身份验证（免登）功能
 * @Author
 * @Date 2023/8/7 16:11
 * @Version 1.0
 **/
@CrossOrigin
@RestController
@Slf4j
public class MainController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private AccessTokenUtil accessTokenUtil;

    /**
     * 欢迎页面, 检查后端服务是否启动
     *
     * @return
     */
    @GetMapping("/welcome")
    @ResponseBody
    public String welcome(@RequestParam("num") int num, HttpServletResponse response) throws Exception {

//        int n = 3;
        long n = 1000*num;
        Thread.sleep(n);

        System.out.println("=========延迟时间============"+n+"=========请求时间============"+new Date());
        return "welcome"+n;
    }


    /**
     * 根据免登授权码, 获取登录用户身份
     *
     * @param authCode 免登授权码
     * @return
     */
    @RequestMapping(value = "/loginDingDing", method = RequestMethod.POST)
    public RpcServiceResult login(@RequestParam(value = "authCode") String authCode,@RequestParam(value = "corpId") String  corpId) {
        try {
            // 1. 获取用户id
            String userId = userManager.getUserId(authCode, corpId);

            // 2. 获取用户名称
            String userName = userManager.getUserName(userId, corpId);

            // 3. 获取用户手机号
            String userMobile = userManager.getUserMobile(userId, corpId);

            // 4. 返回用户身份
            Map<String, Object> resultMap = new HashMap<>(3);
            resultMap.put("userId", userId);
            resultMap.put("userName", userName);
            resultMap.put("userMobile", userMobile);

            return RpcServiceResult.getSuccessResult(resultMap);
        } catch (Exception ex) {
            return RpcServiceResult.getFailureResult("-1", "login exception");
        }
    }

    public static com.aliyun.dingtalkoauth2_1_0.Client authClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkoauth2_1_0.Client(config);
    }

}
