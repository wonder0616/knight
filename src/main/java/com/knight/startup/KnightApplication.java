package com.knight.startup;

import com.knight.config.ConfigMapInit;
import com.knight.config.TomcatConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"com.knight"})
@EnableCaching
public class KnightApplication {

    public static void main(String[] args) {
//        ConfigMapInit.readConfigMaps();
        SpringApplication.run(KnightApplication.class, args);
//        log.info("app start at :{} ", System.getProperty("SERVER_ADDRESS"));
    }

}
