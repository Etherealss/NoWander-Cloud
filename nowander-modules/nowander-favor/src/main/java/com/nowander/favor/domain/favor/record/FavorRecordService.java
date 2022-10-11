package com.nowander.favor.domain.favor.record;

import com.nowander.common.core.exception.service.ExistException;
import com.nowander.common.core.lock.CacheLock;
import com.nowander.favor.infrastructure.config.FavorConfig;
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

    @CacheLock(key = ":#{command.getTargetType().getCode() + ':' + command.getTargetId() + ':' + userId}")
    @Transactional(rollbackFor = Exception.class)
    public void addFavor(FavorRecordCommand command, Integer userId) {
        boolean hasFavor = this.checkHasFavor(command, userId);
        if (hasFavor) {
            // 重复点赞
            throw new ExistException(FavorRecordEntity.class);
        }
        favorRecordCache.addBuffer(FavorRecordVO.buildVO(command, userId), true);
    }

    public boolean checkHasFavor(FavorRecordCommand command, Integer userId) {
        /*
        分三步：
        1. 查“最近点赞记录缓存”，检查用户最近是否有点赞或取消点赞的操作并且该操作记录尚未持久化到数据库
        2. 查“点赞记录缓存”
        3. 查数据库并更新“点赞记录缓存”
         */
        FavorRecordVO favorRecord = FavorRecordVO.buildVO(command, userId);
        Boolean recentLike = favorRecordCache.getBufferFavor(favorRecord);
        if (recentLike != null) {
            // 不为空，说明用户最近有点赞或取消点赞，但该数据尚未持久化到数据库
            return recentLike;
        }

        Boolean like = favorRecordCache.getCacheFavor(favorRecord);
        if (like != null) {
            return like;
        }
        // 从数据库查数据
        FavorRecordEntity entity = command.convert();
        entity.setUserId(userId);
        like = favorRecordMapper.countFavorRecord(entity) == 1;
        // 更新缓存
        favorRecordCache.addCache(entity, like);
        return like;
    }

}
