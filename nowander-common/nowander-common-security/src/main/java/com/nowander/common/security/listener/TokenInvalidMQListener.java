package com.nowander.common.security.listener;

import com.nowander.common.core.mq.MQMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 监听token失效的mq消息
 * @author wtk
 * @date 2022-10-27
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "${app.token.user.mq.topic}",
        consumerGroup = "${app.mq.default-cunsumer-group}",
        selectorExpression = "${app.token.user.mq.tags.invalid}"
)
public class TokenInvalidMQListener implements RocketMQListener<MQMessage<String>> {

    @Override
    public void onMessage(MQMessage<String> message) {
        log.info("收到MQ消息：{}", message);
    }
}
