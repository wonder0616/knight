package com.vince.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author knight
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 使用accessToken加解密
     *
     * @return
     */
//    @GetMapping("/getLoginInfo")
//    public String getLoginInfo()
//    {
//        final SecureRandom random = RandomUtil.getSecureRandom("123456".getBytes());
//        final SecretKey secretKey = KeyUtil.generateKey("AES", 128, random);
//        System.out.println("======random====" + random);
//        System.out.println("======secretKey====" + secretKey);
//
//        return "";
//    }


    /**
     * 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/doLogin")
    public String doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("admin".equals(username) && "123456".equals(password)) {
            StpUtil.login(username);
            return "登录成功";
        }
        return "登录失败";
    }



    /**
     * 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
     *
     * @return
     */
    @RequestMapping("/isLogin")
    public String isLogin() {
        System.out.println(StpUtil.isLogin());
        return "当前会话是否登录：" + StpUtil.isLogin();
    }


    /**
     * 测试登出，浏览器访问： http://localhost:8081/user/logOut
     *
     * @return
     */
    @RequestMapping("/logOut")
    public String logOut() {

        StpUtil.logout();
        return null;
    }
}
