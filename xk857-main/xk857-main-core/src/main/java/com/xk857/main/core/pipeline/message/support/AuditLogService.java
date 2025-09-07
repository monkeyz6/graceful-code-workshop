package com.xk857.main.core.pipeline.message.support;

import com.xk857.main.api.message.MessageRequest;
import com.xk857.main.api.message.SendResult;
import com.xk857.main.core.pipeline.message.model.user.UserProfile;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 审计日志服务（内存队列模拟）
 */
@Slf4j
@Service
public class AuditLogService {
    private final Queue<AuditRecord> store = new ConcurrentLinkedQueue<>();

    public SendResult auditAndReturn(MessageRequest req, UserProfile user, SendResult result, long startNano) {
        long costMs = (System.nanoTime() - startNano) / 1_000_000;
        AuditRecord r = new AuditRecord();
        r.setAuditId(UUID.randomUUID().toString());
        r.setTs(Instant.now().toString());
        r.setChannel(String.valueOf(req.getChannel()));
        r.setUserId(req.getUserId());
        r.setRequestId(req.getRequestId());
        r.setSuccess(result.isSuccess());
        r.setCode(result.getCode());
        r.setMsg(result.getMessage());
        r.setCostMs(costMs);
        store.add(r);

        log.info("AUDIT|auditId={} channel={} userId={} requestId={} success={} code={} costMs={} msg={}",
                r.getAuditId(), r.getChannel(), r.getUserId(), r.getRequestId(), r.isSuccess(), r.getCode(), r.getCostMs(), r.getMsg());
        return result;
    }

    public Queue<AuditRecord> dump() { return store; }

    @Data
    public static class AuditRecord {
        private String auditId;
        private String ts;
        private String channel;
        private Long userId;
        private String requestId;
        private boolean success;
        private String code;
        private String msg;
        private long costMs;
    }
}

