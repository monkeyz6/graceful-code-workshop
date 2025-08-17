package com.xk857.main.boot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 主启动类
 */
@SpringBootApplication(scanBasePackages = "com.xk857")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("""
                ========================================
                🎉 优雅代码实战工坊启动成功！
                📖 访问地址: http://localhost:18080
                🔍 测试接口: http://localhost:18080/hello
                👥 在线测试: http://localhost:18080/doc.html
                ========================================
                """);
    }
}
