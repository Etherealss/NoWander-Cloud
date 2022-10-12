package com.nowander.favor.domain.favor.record;

import com.nowander.favor.infrastructure.config.FavorConfig;
import com.nowander.favor.infrastructure.enums.FavorTargetType;
import com.nowander.favor.infrastructure.utils.FavorKeyBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void addBuffer(FavorTargetType targetType, Integer targetId, boolean isFavor) {
        redis.opsForHash().put(
                favorConfig.getRecordBufferKey(),
                FavorKeyBuilder.buildBufferKey(targetType, targetId),
                isFavor ? "1" : "0"
        );
    }

    public void addCache(FavorTargetType targetType, Integer targetId, Boolean isFavor) {
        redis.opsForValue().set(
                FavorKeyBuilder.buildCacheKey(favorConfig.getRecordCacheKey(), targetType, targetId),
                isFavor ? "1" : "0",
                Duration.ofMillis(favorConfig.getRecordCacheExpireMs())
        );
    }

    /**
     * 是否已有点赞记录（与数据库的数据一致）
     */
    public Boolean getCacheFavor(FavorTargetType targetType, Integer targetId) {
        String res = redis.opsForValue().get(FavorKeyBuilder.buildCacheKey(
                favorConfig.getRecordCacheKey(), targetType, targetId
        ));
        return "1".equals(res);
    }

    /**
     * 是否有点赞记录（该记录尚未持久化到数据库）
     */
    public Boolean getBufferFavor(FavorTargetType targetType, Integer targetId) {
        return "1".equals(redis.opsForHash().get(
                favorConfig.getRecordBufferKey(),
                FavorKeyBuilder.buildBufferKey(targetType, targetId)
        ));
    }

    public void delCacheFavor(FavorTargetType targetType, Integer targetId) {
        redis.opsForValue().getAndDelete(
                FavorKeyBuilder.buildCacheKey(favorConfig.getRecordCacheKey(), targetType, targetId)
        );
    }

    public void delBufferLike(FavorTargetType targetType, Integer targetId) {
        redis.opsForHash().delete(
                favorConfig.getRecordBufferKey(),
                FavorKeyBuilder.buildBufferKey(targetType, targetId)
        );
    }

    /**
     * 获取并删除所有 只有Recent点赞记录需要持久化
     * @return
     */
    public List<FavorRecordVO> getAndDelAllBufferRecord() {
        HashOperations<String, String, String> hash = redis.opsForHash();
        String hashKey = favorConfig.getRecordBufferKey();
        Set<String> keys = hash.keys(hashKey);
        return keys.stream().map((key) -> {
            boolean state = "1".equals(hash.get(hashKey, key));
            hash.delete(hashKey, key);
            return FavorRecordVO.buildByBufferKey(key, state);
        }).collect(Collectors.toList());
    }
}
