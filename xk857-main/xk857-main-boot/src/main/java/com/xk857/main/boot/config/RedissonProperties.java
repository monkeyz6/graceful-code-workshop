package com.xk857.main.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {
    /**
     * 形如 redis://host:port 或 rediss://host:port
     */
    private String address = "redis://localhost:6379";
    /** 数据库索引 */
    private int database = 0;
    /** 可选密码，留空则不设置 */
    private String password;
    /** 可选超时时间（毫秒） */
    private Integer timeout;
}

