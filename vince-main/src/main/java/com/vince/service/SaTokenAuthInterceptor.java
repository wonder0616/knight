//package com.vince.service;
//
//
//import cn.dev33.satoken.stp.StpUtil;
//import org.springframework.http.MediaType;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//public class SaTokenAuthInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request,
//                             HttpServletResponse response,
//                             Object handler) throws IOException {
//        // satoken判断登录接口
//        if (StpUtil.isLogin()) {
//            // 已登录，通过拦截
//            return true;
//        }
//        // 未登录，直接返回失败
//        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.getWriter().append(JSONUtil.toJsonStr(ResultVO.fail(ResultVOEnum.NO_AUTH)));
//        return false;
//    }
//
//}
