package com.xk857.main.core.market;

import com.xk857.main.api.market.MarketDataClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.Random;

/**
 * 远程代理（JDK动态代理示例）
 */
@Slf4j
@Configuration
public class MarketDataProxyFactory {

    @Bean
    public MarketDataClient marketDataClient() {
        InvocationHandler handler = new InvocationHandler() {
            private final Random random = new Random();

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                String symbol = (String) args[0];
                // 模拟网络调用耗时与日志
                try { Thread.sleep(60); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                BigDecimal price = BigDecimal.valueOf(100 + random.nextInt(50) + random.nextDouble());
                log.info("[RemoteProxy] getPrice {} -> {}", symbol, price);
                return price;
            }
        };

        return (MarketDataClient) Proxy.newProxyInstance(
                MarketDataClient.class.getClassLoader(),
                new Class[]{MarketDataClient.class},
                handler
        );
    }
}

