package com.nowander.common.core.utils;

import java.util.UUID;

/**
 * @author wtk
 * @date 2021-11-01
 */
public class UUIDUtil {
    public static String getUuidString() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static UUID getUuid() {
        return UUID.randomUUID();
    }
}
