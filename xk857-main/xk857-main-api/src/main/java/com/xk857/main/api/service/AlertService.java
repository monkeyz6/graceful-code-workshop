package com.xk857.main.api.service;

import com.xk857.main.api.dto.AlertRequestDTO;
import com.xk857.main.api.dto.AlertResponseDTO;

/**
 * 告警服务接口
 * 说明：提供对外的告警发送能力，隐藏内部桥接装配（严重级别×渠道×模板）的实现细节。
 */
public interface AlertService {
    AlertResponseDTO send(AlertRequestDTO request);
}
