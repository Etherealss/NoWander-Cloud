package com.nowander.favor.infrastructure.utils;

import com.nowander.favor.infrastructure.enums.FavorTargetType;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author wtk
 * @date 2022-10-11
 */
public class FavorKeyBuilder {

    public static String buildCacheKey(String keyPrefix, FavorTargetType targetType, Integer targetId, Integer userId) {
        if (!StringUtils.hasLength(keyPrefix)) {
            throw new NullPointerException("keyPrefix 不能为空");
        }
        return keyPrefix + ":" + buildBufferKey(targetType, targetId, userId);
    }

    public static String buildBufferKey(FavorTargetType targetType, Integer targetId, Integer userId) {
        Objects.requireNonNull(targetType, "tagetType 不能为空");
        Objects.requireNonNull(targetId, "targetId 不能为空");
        Objects.requireNonNull(userId, "userId 不能为空");
        return targetType.getCode() + ":" + targetId + ":" + userId;
    }

    public static String buildCacheKey(String keyPrefix, FavorTargetType targetType, Integer targetId) {
        if (!StringUtils.hasLength(keyPrefix)) {
            throw new NullPointerException("keyPrefix 不能为空");
        }
        return keyPrefix + ":" + buildBufferKey(targetType, targetId);
    }

    public static String buildBufferKey(FavorTargetType targetType, Integer targetId) {
        Objects.requireNonNull(targetType, "tagetType 不能为空");
        Objects.requireNonNull(targetId, "targetId 不能为空");
        return targetType.getCode() + ":" + targetId;
    }
}
