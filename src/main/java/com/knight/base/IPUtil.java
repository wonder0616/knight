package com.knight.base;


import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class IPUtil {
    private static String realIpHeaderName;

    @Value("${ssf.realIpHeaderName:HTTP_X_FORWARDED_FOR}")
    public void setRealIpHeaderName(String realIpHeaderName) {
        initRealIpHeaderName(realIpHeaderName);
    }

    /**
     * 设置读取IP配置
     *
     * @param configValue String
     */
    public static void initRealIpHeaderName(String configValue) {
        IPUtil.realIpHeaderName = configValue;
    }

    /**
     * 获取客户端IP
     *
     * @param request http请求对象
     * @return 客户端ip
     */
    public static String getRemoteIp(HttpServletRequest request) {
        if (StringUtils.isBlank(realIpHeaderName)) {
            return request.getRemoteAddr();
        }
        String ip = request.getHeader(realIpHeaderName);
        return StringUtils.isNotEmpty(ip) ? ip : request.getRemoteAddr();
    }
}
