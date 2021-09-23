package com.knight.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"com.knight"})
@EnableCaching
public class KnightApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnightApplication.class, args);
//        URL filePath = KnightApplication.class.getResource("/");
//        System.setProperty("com.huawei.security.validator", filePath.getPath());
        SpringApplication.run(KnightApplication.class, args);
    }

}
