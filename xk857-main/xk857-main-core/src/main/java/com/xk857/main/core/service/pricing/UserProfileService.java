package com.xk857.main.core.service.pricing;

import org.springframework.stereotype.Service;

/**
 * 用户画像模拟服务（不连接数据库）
 */
@Service
public class UserProfileService {

    /**
     * 模拟：偶数ID为VIP
     */
    public boolean isVip(Long userId) {
        return userId != null && userId % 2 == 0;
    }

    /**
     * 模拟：ID <= 100 视作新人
     */
    public boolean isNewUser(Long userId) {
        return userId != null && userId <= 100;
    }
}

