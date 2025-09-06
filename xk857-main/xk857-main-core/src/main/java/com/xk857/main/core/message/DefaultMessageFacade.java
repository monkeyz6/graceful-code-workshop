package com.xk857.main.core.message;

import com.xk857.main.api.message.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("defaultMessageFacade")
@RequiredArgsConstructor
public class DefaultMessageFacade implements MessageFacade {

    private final List<ChannelClient> channels;

    @Override
    public AggregateResult broadcast(MessageReq req) {
        Map<String, SendResult> details = new LinkedHashMap<>();
        for (ChannelClient client : channels) {
            SendResult result = safeSend(client, req);
            details.put(client.name(), result);
        }
        boolean allSuccess = details.values().stream().allMatch(SendResult::success);
        return new AggregateResult(allSuccess, details);
    }

    @Override
    public AggregateResult send(MessageReq req, SendPolicy policy) {
        Map<String, ChannelClient> dict = channels.stream().collect(Collectors.toMap(ChannelClient::name, c -> c));
        Map<String, SendResult> details = new LinkedHashMap<>();
        boolean anySuccess = false;
        for (String name : policy.preferredOrder()) {
            ChannelClient client = dict.get(name);
            if (client == null) {
                details.put(name, SendResult.fail("channel_not_found"));
                continue;
            }
            SendResult result = safeSend(client, req);
            details.put(name, result);
            if (result.success()) {
                anySuccess = true;
                if (policy.breakOnFirstSuccess()) {
                    break;
                }
            }
        }
        return new AggregateResult(anySuccess, details);
    }

    private SendResult safeSend(ChannelClient client, MessageReq req) {
        try {
            SendResult r = client.send(req);
            if (!r.success()) {
                log.warn("channel={} send failed, err={}", client.name(), r.error());
            } else {
                log.info("channel={} send ok, id={}", client.name(), r.messageId());
            }
            return r;
        } catch (Exception e) {
            log.error("channel={} exception", client.name(), e);
            return SendResult.fail(e.getMessage());
        }
    }
}

