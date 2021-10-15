package com.dachui.vpn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@MapperScan(basePackages = {"com.dachui.vpn.repository"})
//@ComponentScan(basePackages = {"com.dachui.vpn.config"})
public class VpnApplication {

    public static void main(String[] args) {
        SpringApplication.run(VpnApplication.class, args);
    }

}
