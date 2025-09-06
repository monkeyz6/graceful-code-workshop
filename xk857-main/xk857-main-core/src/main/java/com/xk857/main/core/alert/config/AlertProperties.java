package com.xk857.main.core.alert.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 告警默认配置（按严重级别）
 * 说明：从配置文件读取 info/warn/critical 默认的渠道与模板，
 * 供 AlertFactory 组装桥接对象时使用，可被请求入参覆盖。
 */
@Data
@Component
@ConfigurationProperties(prefix = "alert")
public class AlertProperties {
    private LevelConfig info = new LevelConfig();
    private LevelConfig warn = new LevelConfig();
    private LevelConfig critical = new LevelConfig();

    /**
     * 单个严重级别的默认配置
     */
    @Data
    public static class LevelConfig {
        private String channel;
        private String template;
    }
}
