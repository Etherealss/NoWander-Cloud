package com.nowander.favor.domain.favor.record;

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

    @CacheLock(key = ":#{command.getTargetType().getCode() + ':' + command.getTargetId() + ':' + userId}")
    @Transactional(rollbackFor = Exception.class)
    public void addFavor(FavorRecordCommand command, Integer userId) {
        FavorRecordEntity entity = command.convert();
        entity.setUserId(userId);

    }

    public boolean checkHasFavor(FavorRecordCommand command, Integer userId) {
        return false;
    }

}
