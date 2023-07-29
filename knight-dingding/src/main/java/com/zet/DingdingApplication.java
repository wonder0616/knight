package com.zet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 *
 * @author knight
 */
@SpringBootApplication
@ServletComponentScan
@EnableScheduling
@Slf4j
public class DingdingApplication
{
    public static void main( String[] args ) {
        log.info("DingdingApplication is running...");
        SpringApplication.run(DingdingApplication.class, args);
    }
}
