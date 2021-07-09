package com.cinnabar.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName Logback.java
 * @Description
 * @createTime 2020-11-27  10:22:00
 */
@ConfigurationProperties(prefix = "test")
@Configuration
@Data
public class Logback {
    String mapperDir;

    String sqlLevel;

    String rootLevel;

    String appName;

    String logDir;
}
