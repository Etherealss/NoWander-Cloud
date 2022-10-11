package com.nowander.favor.domain.favor.count;

import com.nowander.favor.domain.favor.record.FavorRecordEntity;
import com.nowander.favor.infrastructure.config.FavorConfig;
import com.nowander.favor.infrastructure.utils.FavorKeyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author wtk
 * @date 2022-01-27
 */
@Component
public class FavorCountCache {
    private final RedisTemplate<String, Integer> redis;
    private final HashOperations<String, String, Integer> hash;
    private final FavorConfig favorConfig;

    @Autowired
    public FavorCountCache(RedisTemplate<String, Integer> redis, FavorConfig favorConfig) {
        this.redis = redis;
        this.hash = redis.opsForHash();
        this.favorConfig = favorConfig;
    }


    /**
     * 增加某个目标的点赞数
     */
    public void increBufferFavor(FavorRecordEntity favorRecord, Boolean isLike) {
        hash.increment(
                favorConfig.getCountBufferKey(),
                FavorKeyBuilder.buildBufferKey(favorRecord),
                isLike ? 1L : -1L
        );
    }

    /**
     * 缓存某个目标的点赞数
     */
    public void setCacheCount(FavorCountEntity favorCount) {
        redis.opsForValue().set(
                FavorKeyBuilder.buildCacheKey(favorCount, favorConfig.getCountCacheKey()),
                favorCount.getCount(),
                favorConfig.getCountCacheExpireMs()
        );
    }

    /**
     * 获取某个目标的点赞数缓存
     */
    public Integer getFavorCache(FavorCountEntity entity) {
        return redis.opsForValue().get(
                FavorKeyBuilder.buildCacheKey(entity, favorConfig.getCountCacheKey())
        );
    }

    /**
     * 获取某个目标的新增点赞数缓存
     */
    public Integer getBufferFavorCount(FavorCountEntity favorCount) {
        return hash.get(
                favorConfig.getCountBufferKey(),
                FavorKeyBuilder.buildBufferKey(favorCount)
        );
    }

    public Set<String> getAllKeys() {
        return hash.keys(favorConfig.getCountBufferKey());
    }

    /**
     * 获取并删除
     * @param key
     * @return 如果key获取不到count，则返回null
     */
    public FavorCountVO getAndDelBufferFavorCount(String key) {
        Integer count = hash.get(favorConfig.getRecordBufferKey(), key);
        if (count != null) {
            // 拿了立马删，因为可能有数据丢失的风险
            hash.delete(favorConfig.getRecordBufferKey(), key);
            return new FavorCountVO(key, count);
        }
        return null;
    }
}