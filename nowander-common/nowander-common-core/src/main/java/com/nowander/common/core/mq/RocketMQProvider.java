package com.nowander.common.core.mq;

import com.nowander.common.core.config.SystemConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2022-10-27
 */
@Component
@Slf4j
@Validated
@RequiredArgsConstructor
public class RocketMQProvider {
    private final RocketMQTemplate rocketMQTemplate;

    private final String IP = SystemConfig.IP;

    @Value("${app.token.server.server-id}")
    @NotEmpty
    private String curServerName;
    @Value("${app.token.server.server-name}")
    @NotNull
    private Integer curServerId;

    public <T> void convertAndSend(String topic, String tag, T data) throws MessagingException {
        String desc = buildDesc(topic, tag);
        MQMessage<T> mqMessage = buildMQMessage(topic, tag, data);
        rocketMQTemplate.convertAndSend(desc, mqMessage);
    }

    private <T> MQMessage<T> buildMQMessage(String topic, String tag, T data) {
        return new MQMessage<>(IP, topic, tag, curServerId, curServerName, data);
    }

    private String buildDesc(String topic, String tag) {
        if (StringUtils.hasLength(tag)) {
            return topic + ":" + tag;
        } else {
            return topic;
        }
    }
}
