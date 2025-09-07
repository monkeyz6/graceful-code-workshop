package com.xk857.main.core.pipeline.message.strategy;

import com.xk857.main.core.pipeline.message.model.user.UserProfile;

/**
 * 用户加载策略
 */
public interface UserLoaderStrategy {
    UserProfile load(long userId);
}

