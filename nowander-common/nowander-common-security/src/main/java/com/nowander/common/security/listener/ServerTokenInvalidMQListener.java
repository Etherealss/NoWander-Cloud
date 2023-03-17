package com.nowander.common.security.listener;

/**
 * 监听token失效的mq消息
 * @author wtk
 * @date 2022-10-27
 */
//@Slf4j
//@Component
//@RocketMQMessageListener(
//        topic = "${app.token.server.mq.topic}",
//        consumerGroup = "${app.mq.default-cunsumer-group}",
//        selectorExpression = "${app.token.server.mq.tags.invalid}"
//)
//public class ServerTokenInvalidMQListener implements RocketMQListener<MQMessage<String>> {
//    private CredentialCacheHandler credentialCacheHandler;
//    @Override
//    public void onMessage(MQMessage<String> message) {
//        log.info("收到MQ消息：{}", message);
//        credentialCacheHandler.invalidCache(message.getData(), ServerCredential.class);
//    }
//}
