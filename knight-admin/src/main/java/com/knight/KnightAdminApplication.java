package com.knight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author knight
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class KnightAdminApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(KnightAdminApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  xxx启动成功   ლ(´ڡ`ლ)" );
    }
}
