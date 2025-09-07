package com.xk857.main.core.pipeline.message.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模拟的用户资料
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private Long userId;
    private String name;
    private String phone;
    private String email;
    private String wecomId;
    private String tenant;
    private boolean blacklisted;
}

