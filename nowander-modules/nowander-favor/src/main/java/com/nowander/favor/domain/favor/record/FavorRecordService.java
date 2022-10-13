package com.nowander.favor.domain.favor.record;

import com.nowander.common.core.exception.service.ExistException;
import com.nowander.common.core.lock.CacheLock;
import com.nowander.favor.infrastructure.config.FavorConfig;
import com.nowander.favor.infrastructure.enums.FavorTargetType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wtk
 * @date 2022-10-09
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FavorRecordService {
    private final FavorConfig favorConfig;
    private final FavorRecordMapper favorRecordMapper;
    private final FavorRecordCache favorRecordCache;

    @CacheLock(key = "':' + #targetType.code + ':' + #targetId + ':' + #userId")
    @Transactional(rollbackFor = Exception.class)
    public void addFavor(FavorTargetType targetType, Integer targetId, Integer userId) {
        boolean hasFavor = this.checkHasFavor(targetType, targetId, userId);
        if (hasFavor) {
            // 重复点赞
            throw new ExistException(FavorRecordEntity.class);
        }
        favorRecordCache.addBuffer(targetType, targetId, true);
    }

    public boolean checkHasFavor(FavorTargetType targetType, Integer targetId, Integer userId) {
        /*
        分三步：
        1. 查“最近点赞记录缓存”，检查用户最近是否有点赞或取消点赞的操作并且该操作记录尚未持久化到数据库
        2. 查“点赞记录缓存”
        3. 查数据库并更新“点赞记录缓存”
         */
        Boolean recentLike = favorRecordCache.getBufferFavor(targetType, targetId);
        if (recentLike != null) {
            // 不为空，说明用户最近有点赞或取消点赞，但该数据尚未持久化到数据库
            return recentLike;
        }

        Boolean like = favorRecordCache.getCacheFavor(targetType, targetId);
        if (like != null) {
            return like;
        }
        // 从数据库查数据
        like = favorRecordMapper.countFavorRecord(targetType, targetId, userId) == 1;
        // 更新缓存
        favorRecordCache.addCache(targetType, targetId, like);
        return like;
    }

}
