package com.xk857.main.core.pipeline.message.strategy;

import com.xk857.main.core.pipeline.message.model.user.UserProfile;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 内存模拟的用户加载
 */
@Slf4j
@Component
public class MockUserLoader implements UserLoaderStrategy {

    private final Map<Long, UserProfile> mock = new HashMap<>();

    @PostConstruct
    public void init() {
        mock.put(1L, UserProfile.builder().userId(1L).name("张三").phone("13800000001").email("zhangsan@example.com").wecomId("wx_zhangsan").tenant("acme").blacklisted(false).build());
        mock.put(2L, UserProfile.builder().userId(2L).name("李四").phone("13800000002").email("lisi@example.com").wecomId("wx_lisi").tenant("acme").blacklisted(false).build());
        mock.put(3L, UserProfile.builder().userId(3L).name("小黑屋").phone("13800000003").email("black@example.com").wecomId("wx_black").tenant("acme").blacklisted(true).build());
        log.info("MockUserLoader initialized with {} users", mock.size());
    }

    @Override
    public UserProfile load(long userId) {
        return mock.get(userId);
    }
}

