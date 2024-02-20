package com.vince;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication.run(Application.class, args);
		InetAddress localHost = InetAddress.getLocalHost();
		String ipAddress = localHost.getHostAddress();
		System.out.println("启动成功："+ipAddress+":8081");
	}
	
}
