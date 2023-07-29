package com.zet.dingding.controller;
import com.alibaba.fastjson2.JSONObject;
import com.zet.dingding.model.RpcLinkServiceResult;
import com.zet.dingding.model.RpcServiceResult;
import com.zet.dingding.service.DingTalkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 企业内部应用免登及页面初始化
 *
 * @author zet
 */
@RestController
@RequestMapping("dingding")
@Slf4j
public class DingAuthController {

    @Autowired
    private DingTalkService dingTalkService;

    /**
     * 欢迎页面，通过 /welcome 访问，判断后端服务是否启动
     *
     * @return 字符串 welcome
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    /**
     * 钉钉免登陆接口，获取用户信息
     *
     * @param authCode authCode
     * @return RpcServiceResult
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public RpcServiceResult login(@RequestParam(value = "authCode", required = false) String authCode,
                                  @RequestParam(value = "authCode1", required = false) String authCode4A) throws Exception {
        log.info("DingAuthController.login() begin,authCode{},authCode4A{}", authCode, authCode4A);

        if (StringUtils.isBlank(authCode4A) || StringUtils.isBlank(authCode)) {
            return RpcServiceResult.getFailureResult("1", "请求参数不正确，请检查");
        }
        Map<String, Object> rsp = dingTalkService.getLinkUserInfo(authCode, authCode4A);

        if (rsp == null) {
            return RpcServiceResult.getFailureResult("1", "登陆失败");
        }

        return RpcServiceResult.getSuccessResult(rsp);
    }

    /**
     * 和平台的票据认证，用于钉钉和平台的交互
     *
     * @param object
     * @return
     */
    @PostMapping("/terminal/serviceValidate")
    public RpcLinkServiceResult serviceValidate(@RequestBody JSONObject object) {

        String authCode = object.getString("ticket");

        log.info("=====DingAuthController.serviceValidate=====,object{}", object.toJSONString());
        Map<String, Object> rsp = dingTalkService.getLinkUserInfoFor4A(authCode);
        rsp.put("userAccount", rsp.get("mobile"));
        rsp.put("userName", rsp.get("userName"));
        rsp.put("ticket", authCode);

        if (StringUtils.isBlank(rsp.get("mobile").toString())) {
            return RpcLinkServiceResult.getFailureResult("1", rsp.toString());
        }
        return RpcLinkServiceResult.getSuccessResult(rsp);
    }

}