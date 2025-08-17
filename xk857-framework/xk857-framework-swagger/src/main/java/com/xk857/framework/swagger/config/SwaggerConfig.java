package com.xk857.framework.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger3 配置类（仅负责基础信息与服务器配置）
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(apiInfo())
                .servers(List.of(new Server().url("http://localhost:18080").description("本地开发环境")));
    }

    @Bean
    public OperationCustomizer globalResultOperationCustomizer() {
        return new GlobalResultOperationCustomizer();
    }

    private Info apiInfo() {
        return new Info()
                .title("优雅代码实战工坊 API")
                .description("基于Spring Boot 3 + JDK17")
                .version("1.0.0")
                .contact(new Contact().name("cv大魔王").url("https://github.com/xk857"))
                .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT"));
    }
}
