package com.xk857.main.core.service;

import com.xk857.main.api.dto.AlertRequestDTO;
import com.xk857.main.api.dto.AlertResponseDTO;
import com.xk857.main.api.enums.AlertSeverity;
import com.xk857.main.api.service.AlertService;
import com.xk857.main.core.alert.bridge.Alert;
import com.xk857.main.core.alert.channel.SendResult;
import com.xk857.main.core.alert.factory.AlertFactory;
import com.xk857.main.core.alert.template.AlertContext;
import org.springframework.stereotype.Service;

/**
 * 告警服务实现
 * 说明：对外服务入口，委托 AlertFactory 组装桥接对象并发送，
 * 同时构建响应信息用于接口返回与排查记录。
 */
@Service
public class AlertServiceImpl implements AlertService {
    private final AlertFactory alertFactory;

    public AlertServiceImpl(AlertFactory alertFactory) {
        this.alertFactory = alertFactory;
    }

    @Override
    public AlertResponseDTO send(AlertRequestDTO request) {
        AlertSeverity severity = AlertSeverity.from(request.getSeverity());
        Alert alert = alertFactory.create(severity, request.getChannel(), request.getTemplate());
        SendResult result = alert.notify(new AlertContext(request.getTitle(), request.getSummary(), severity.name()));
        return new AlertResponseDTO(result.isSuccess(),
                result.getMessageId(),
                result.getError(),
                pick(request.getChannel()),
                severity.name()
        );
    }

    private static String pick(String s) {
        return (s == null || s.isBlank()) ? "(by-config)" : s;
    }
}
