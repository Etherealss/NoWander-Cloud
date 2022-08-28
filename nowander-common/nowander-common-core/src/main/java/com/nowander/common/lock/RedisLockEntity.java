package com.nowander.common.lock;

import lombok.Data;
/**
 * @author wang tengkun
 * @date 2022/2/26
 */
@Data
public class RedisLockEntity {
    private String lockKey;
    private String requestId;
}
