package com.xk857.main.boot.config;

import com.xk857.main.api.message.MessageReq;
import com.xk857.main.api.message.SendPolicy;
import com.xk857.main.core.message.AsyncMessageFacade;
import com.xk857.main.core.message.DefaultMessageFacade;
import com.xk857.main.core.message.bus.EventBus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MessageBusConfig {

    private final EventBus eventBus;
    private final DefaultMessageFacade defaultMessageFacade;

    @PostConstruct
    public void registerConsumers() {
        eventBus.register(AsyncMessageFacade.TOPIC_BROADCAST, payload -> {
            try {
                defaultMessageFacade.broadcast((MessageReq) payload);
            } catch (Exception e) {
                log.error("broadcast consume error", e);
            }
        });

        eventBus.register(AsyncMessageFacade.TOPIC_SEND_WITH_POLICY, payload -> {
            try {
                AsyncMessageFacade.SendPayload p = (AsyncMessageFacade.SendPayload) payload;
                defaultMessageFacade.send(p.req(), p.policy());
            } catch (Exception e) {
                log.error("send consume error", e);
            }
        });
    }
}

