package com.nowander.forum.domain;

import com.nowander.forum.infrastruceture.enums.FavorTargetType;
import com.nowander.forum.infrastruceture.feign.favor.FavorCountFeign;
import com.nowander.forum.infrastruceture.feign.favor.FavorRecordFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @date 2022-10-18
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FavorService {
    private final FavorRecordFeign favorRecordFeign;
    private final FavorCountFeign favorCountFeign;

    public void addFavor(FavorTargetType targetType, Integer targetId, Integer userId) {
        favorRecordFeign.addFavor(targetType, targetId, userId);
    }

    public void delFavor(FavorTargetType targetType, Integer targetId, Integer userId) {
        favorRecordFeign.delFavor(targetType, targetId, userId);
    }

    public boolean checkHasFavor(FavorTargetType targetType, Integer targetId, Integer userId) {
        return favorRecordFeign.checkHasLike(targetType, targetId, userId);
    }

    public int getFavorCount(FavorTargetType targetType, Integer targetId) {
        return favorCountFeign.getLikeCount(targetType, targetId);
    }
}
