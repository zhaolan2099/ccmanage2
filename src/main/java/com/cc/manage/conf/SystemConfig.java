package com.cc.manage.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：
 * @time ：Created in 2020/9/3 9:37
 */
@Configuration
@ConfigurationProperties(prefix = "system")
@Data
public class SystemConfig {
    /** 通信端端口 */
    private String clientPort;
}
