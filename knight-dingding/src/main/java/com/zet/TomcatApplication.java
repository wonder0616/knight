package com.zet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Description tomcat启动类
 * @Author
 * @Date  2023/8/7 16:12
 * @Version 1.0
**/
@SpringBootApplication
public class TomcatApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(TomcatApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TomcatApplication.class);
    }
}
