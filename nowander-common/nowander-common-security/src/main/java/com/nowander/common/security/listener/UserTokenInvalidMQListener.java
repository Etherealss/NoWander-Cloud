package com.nowander.common.security.listener;

/**
 * 监听token失效的mq消息
 * @author wtk
 * @date 2022-10-27
 */
//@Slf4j
//@Component
//@RocketMQMessageListener(
//        topic = "${app.token.user.mq.topic}",
//        consumerGroup = "${app.mq.default-cunsumer-group}",
//        selectorExpression = "${app.token.user.mq.tags.invalid}"
//)
//public class UserTokenInvalidMQListener implements RocketMQListener<MQMessage<TokenInvalidMQData>> {
//    private CredentialCacheHandler credentialCacheHandler;
//    @Override
//    public void onMessage(MQMessage<TokenInvalidMQData> message) {
//        log.info("收到MQ消息：{}", message);
//        credentialCacheHandler.invalidCache(message.getData().getToken(), UserCredential.class);
//    }
//}
