package com.nowander.forum.infrastruceture.feign.favor;

import com.nowander.forum.infrastruceture.enums.FavorTargetType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wtk
 * @date 2022-10-18
 */
@FeignClient(value = "nowander-favor", path = "/favor/favors/counts", contextId = "nowander-favor-count")
public interface FavorCountFeign {

    @GetMapping("/{targetType}/{targetId}")
    Integer getLikeCount(@PathVariable FavorTargetType targetType,
                         @PathVariable Integer targetId);
}
