package com.xk857.main.core.pipeline.message.model;

import com.xk857.main.api.message.MessageRequest;
import com.xk857.main.core.pipeline.message.model.user.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送上下文
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendContext {
    private MessageRequest request;
    private UserProfile user;
    private String renderedContent;
}

