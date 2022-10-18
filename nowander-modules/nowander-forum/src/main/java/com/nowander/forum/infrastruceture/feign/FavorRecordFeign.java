package com.nowander.forum.infrastruceture.feign;

import com.nowander.forum.infrastruceture.enums.FavorTargetType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author wtk
 * @date 2022-10-18
 */
@FeignClient(value = "nowander-favor", path = "/favor/favors/records", contextId = "nowander-favor-record")
public interface FavorRecordFeign {

    /**
     * 点赞
     * @return
     */
    @PostMapping("/{targetType}/{targetId}/{userId}")
    void addFavor(@PathVariable("targetType") FavorTargetType targetType,
                  @PathVariable("targetId") Integer targetId,
                  @PathVariable("userId") Integer userId);

    /**
     * 取消
     * @param targetType
     * @param targetId
     */
    @DeleteMapping("/{targetType}/{targetId}/{userId}")
    void delFavor(@PathVariable("targetType") FavorTargetType targetType,
                  @PathVariable("targetId") Integer targetId,
                  @PathVariable("userId") Integer userId);

    /**
     * 是否已点赞
     * @return
     */
    @GetMapping("/{targetType}/{targetId}/{userId}")
    Boolean checkHasLike(@PathVariable("targetType") FavorTargetType targetType,
                         @PathVariable("targetId") Integer targetId,
                         @PathVariable("userId") Integer userId);
}
