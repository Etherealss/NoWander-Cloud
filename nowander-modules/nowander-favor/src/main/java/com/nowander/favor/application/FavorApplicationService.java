package com.nowander.favor.application;

import com.nowander.favor.domain.favor.count.FavorCountService;
import com.nowander.favor.domain.favor.record.FavorRecordService;
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
public class FavorApplicationService {
    private final FavorCountService countService;
    private final FavorRecordService recordService;

    @Transactional(rollbackFor = Exception.class)
    public void addFavor(FavorTargetType targetType, Integer targetId, Integer userId) {
        recordService.addFavor(targetType, targetId, userId);
        countService.addFavor(targetType, targetId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delFavor(FavorTargetType targetType, Integer targetId, Integer userId) {
        recordService.addFavor(targetType, targetId, userId);
        countService.addFavor(targetType, targetId);
    }

    public boolean checkHasFavor(FavorTargetType targetType, Integer targetId, Integer userId) {
        return recordService.checkHasFavor(targetType, targetId, userId);
    }
}
