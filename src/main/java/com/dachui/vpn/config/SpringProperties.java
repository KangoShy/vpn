package com.dachui.vpn.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/21
 * 作者：大锤
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "vpn.order")
public class SpringProperties {

    private Integer timeout;

}
