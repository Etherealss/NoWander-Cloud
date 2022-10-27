package com.nowander.common.core.mq;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

/**
 * @author wtk
 * @date 2022-10-27
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
public class MQMessage<T> {
    UUID id;
    String topic;
    String tag;
    String senderIp;
    Integer senderServerId;
    String senderServerName;
    T data;
    Date createTime;

    public MQMessage(String senderIp,
                     String topic,
                     String tag,
                     Integer senderServerId,
                     String senderServerName,
                     T data) {
        this.id = UUID.randomUUID();
        this.topic = topic;
        this.tag = tag;
        this.senderIp = senderIp;
        this.senderServerId = senderServerId;
        this.data = data;
        this.createTime = new Date();
    }
}
