package com.knight.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 功能描述
 *
 * @author knight
 * @since 2021-10-17
 */
@Slf4j
@Configuration
@Primary
public class TomcatConfig {
    /**
     * 定制部分web服务配置
     *
     * @return WebServerFactoryCustomizer
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        log.info("webServerFactoryCustomizer::{}");
        return factory -> {
            try {
                factory.setAddress(InetAddress.getByName(getLocalIp()));
            } catch (UnknownHostException e) {
                log.info("webServerFactoryCustomizer::{}", e.getMessage());
            }
        };
    }

    /**
     * 获取IP
     *
     * @return String
     */
    public static String getLocalIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> ips = networkInterfaces.nextElement().getInetAddresses();
                while (ips.hasMoreElements()) {
                    InetAddress inetAddress = ips.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()
                        && inetAddress.getHostAddress().startsWith("10.")) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            log.error("getLocalIp:", e);
        }
        return null;
    }
}