package com.nowander.favor.domain.favor.record;

import com.nowander.favor.infrastructure.config.FavorConfig;
import com.nowander.favor.infrastructure.enums.FavorTargetType;
import com.nowander.favor.infrastructure.utils.FavorKeyBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

/**
 * 用户点赞行为缓存
 * 点赞是高频操作，如果每次一有用户点赞就缓存到数据库，那么点赞的持久化操作会非常频繁
 * 因此此处先将用户点赞的记录（也就是谁点赞了啥）缓存起来，之后定时批量写入数据库
 * @author wtk
 * @date 2022-01-26
 */
@Component
@RequiredArgsConstructor
public class FavorRecordCache {
    private final RedisTemplate<String, String> redis;
    private final FavorConfig favorConfig;

    public void setBuffer(FavorTargetType targetType, Integer targetId, Integer userId, boolean isFavor) {
        redis.opsForHash().put(
                favorConfig.getRecordBufferKey(),
                FavorKeyBuilder.buildBufferKey(targetType, targetId),
                isFavor ? "1" : "0"
        );
    }

    public void setCache(FavorTargetType targetType, Integer targetId, Integer userId, Boolean isFavor) {
        redis.opsForValue().set(
                FavorKeyBuilder.buildCacheKey(favorConfig.getRecordCacheKey(), targetType, targetId),
                isFavor ? "1" : "0",
                Duration.ofMillis(favorConfig.getRecordCacheExpireMs())
        );
    }

    /**
     * 是否已有点赞记录（与数据库的数据一致）
     */
    public Boolean getCacheFavor(FavorTargetType targetType, Integer targetId, Integer userId) {
        String res = redis.opsForValue().get(FavorKeyBuilder.buildCacheKey(
                favorConfig.getRecordCacheKey(), targetType, targetId, userId
        ));
        return "1".equals(res);
    }

    /**
     * 是否有点赞记录（该记录尚未持久化到数据库）
     */
    public Boolean getBufferFavor(FavorTargetType targetType, Integer targetId, Integer userId) {
        return "1".equals(redis.opsForHash().get(
                favorConfig.getRecordBufferKey(),
                FavorKeyBuilder.buildBufferKey(targetType, targetId, userId)
        ));
    }

    /**
     * 缓存缓存项
     * @param targetType
     * @param targetId
     */
    public void delCache(FavorTargetType targetType, Integer targetId) {
        redis.opsForValue().getAndDelete(
                FavorKeyBuilder.buildCacheKey(favorConfig.getRecordCacheKey(), targetType, targetId)
        );
    }

    /**
     * 删除缓冲项
     * @param targetType
     * @param targetId
     */
    public void delBuffer(FavorTargetType targetType, Integer targetId) {
        redis.opsForHash().delete(
                favorConfig.getRecordBufferKey(),
                FavorKeyBuilder.buildBufferKey(targetType, targetId)
        );
    }

    public Set<String> getAllKeys() {
        HashOperations<String, String, String> hash = redis.opsForHash();
        String hashKey = favorConfig.getRecordBufferKey();
        Set<String> keys = hash.keys(hashKey);
        return keys;
    }

    /**
     * 获取并删除所有 只有 Buffer 的点赞记录需要持久化
     * @return
     */
    public FavorRecordVO getAndDelBufferRecord(String bufferKey) {
        HashOperations<String, String, String> hash = redis.opsForHash();
        String res = hash.get(favorConfig.getRecordBufferKey(), bufferKey);
        if (res == null) {
            return null;
        }
        Long count = hash.delete(favorConfig.getRecordBufferKey(), bufferKey);
        if (count == 0) {
            return null;
        }
        boolean isFavor = "1".equals(res);
        return FavorRecordVO.buildByBufferKey(bufferKey, isFavor);
    }
}
