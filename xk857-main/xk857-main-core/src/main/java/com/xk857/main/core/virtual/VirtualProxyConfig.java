package com.xk857.main.core.virtual;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 提供Chart虚拟代理Bean
 */
@Slf4j
@Configuration
public class VirtualProxyConfig {
    @Bean
    public Chart chart() {
        return new ChartVirtualProxy(() -> new HeavyChart("demo-dataset"));
    }
}

