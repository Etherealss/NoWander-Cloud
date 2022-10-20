package com.nowander.favor.domain.favor.count;

import com.nowander.favor.infrastructure.enums.FavorTargetType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

/**
 * @author wtk
 * @date 2022-10-12
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FavorCountService {

    private final FavorCountCache favorCountCache;
    private final FavorCountMapper favorCountMapper;

    public void addFavor(FavorTargetType targetType, Integer targetId) {
        favorCountCache.increBufferFavor(targetType, targetId, true);
    }
    public void delFavor(FavorTargetType targetType, Integer targetId) {
        favorCountCache.increBufferFavor(targetType, targetId, false);
    }

    public int getTotalCount(FavorTargetType type, Integer targetId) {
        // TODO 布隆过滤器
        int ret = 0;
        Integer count = favorCountCache.getFavorCount(type, targetId);
        if (count == null) {
            FavorCountEntity entity = new FavorCountEntity(type, targetId);
            count = favorCountMapper.getFavorCount(entity);
            count = count == null ? 0 : count;
            favorCountCache.setCacheCount(type, targetId, count);
        }
        ret = count;
        // 获取最近新增点赞数
        Integer incre = favorCountCache.getBufferFavorCount(type, targetId);
        if (incre != null) {
            ret += incre;
        }
        return ret;
    }


    /**
     * 持久化到数据库
     */
    @Scheduled(cron = "${app.favor.count.persistent.cron}")
    public void persistentFavorCounts() {
        log.info("持久化点赞数");
        // TODO 事务
        Set<String> bufferKeys = favorCountCache.getAllKeys();
        bufferKeys.stream().parallel()
                .map(favorCountCache::getAndDelBufferCount)
                .filter(Objects::nonNull)
                .map(FavorCountEntity::build)
                .forEach(favorCountMapper::insert);
    }
}
