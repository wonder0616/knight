package com.vince.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigurerAdapter implements WebMvcConfigurer {

    /**
     * 跨域请求被拦截问题
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(false)
                .allowedMethods("POST","GET","DELETE","PUT","OPTIONS")
                .allowedOrigins("*")
        ;
        // 对所有跨域请求进行放行
    }
}
