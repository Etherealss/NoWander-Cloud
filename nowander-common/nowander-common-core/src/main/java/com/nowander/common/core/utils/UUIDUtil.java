package com.nowander.common.core.utils;

import java.util.UUID;

/**
 * @author wtk
 * @date 2021-11-01
 */
public class UUIDUtil {
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
