package com.nowander.favor.infrastructure.utils;

import com.nowander.favor.domain.favor.count.FavorCountEntity;
import com.nowander.favor.domain.favor.count.FavorCountVO;
import com.nowander.favor.domain.favor.record.FavorRecordEntity;
import com.nowander.favor.domain.favor.record.FavorRecordVO;
import com.nowander.favor.infrastructure.enums.FavorTargetType;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author wtk
 * @date 2022-10-11
 */
public class FavorKeyBuilder {
    public static String buildBufferKey(FavorRecordVO r) {
        return buildBufferKey(r.getTargetType(), r.getTargetId(), r.getUserId());
    }

    public static String buildCacheKey(FavorRecordVO r, String keyPrefix) {
        return buildCacheKey(keyPrefix, r.getTargetType(), r.getTargetId(), r.getUserId());
    }

    public static String buildBufferKey(FavorRecordEntity r) {
        return buildBufferKey(r.getTargetType(), r.getTargetId(), r.getUserId());
    }

    public static String buildCacheKey(FavorRecordEntity r, String keyPrefix) {
        return buildCacheKey(keyPrefix, r.getTargetType(), r.getTargetId(), r.getUserId());
    }

    private static String buildCacheKey(String keyPrefix, FavorTargetType targetType, Integer targetId, Integer userId) {
        if (!StringUtils.hasLength(keyPrefix)) {
            throw new NullPointerException("keyPrefix 不能为空");
        }
        return keyPrefix + ":" + buildBufferKey(targetType, targetId, userId);
    }

    private static String buildBufferKey(FavorTargetType targetType, Integer targetId, Integer userId) {
        Objects.requireNonNull(targetType, "tagetType 不能为空");
        Objects.requireNonNull(targetId, "targetId 不能为空");
        Objects.requireNonNull(userId, "userId 不能为空");
        return targetType.getCode() + ":" + targetId + ":" + userId;
    }

    public static String buildBufferKey(FavorCountEntity c) {
        return buildBufferKey(c.getTargetType(), c.getTargetId());
    }

    public static String buildCacheKey(FavorCountEntity c, String keyPrefix) {
        return buildCacheKey(keyPrefix, c.getTargetType(), c.getTargetId());
    }

    public static String buildBufferKey(FavorCountVO c) {
        return buildBufferKey(c.getTargetType(), c.getTargetId());
    }

    public static String buildCacheKey(FavorCountVO c, String keyPrefix) {
        return buildCacheKey(keyPrefix, c.getTargetType(), c.getTargetId());
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
