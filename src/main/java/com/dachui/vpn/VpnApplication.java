package com.dachui.vpn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.dachui.vpn.repository"})
public class VpnApplication {

    public static void main(String[] args) {
        SpringApplication.run(VpnApplication.class, args);
    }

}
