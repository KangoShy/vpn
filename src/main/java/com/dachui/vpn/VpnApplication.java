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
//@ComponentScan({"com.dachui.vpn.common"})
public class VpnApplication {

    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password", "KangoShyVpn500");
        SpringApplication.run(VpnApplication.class, args);
    }

}
