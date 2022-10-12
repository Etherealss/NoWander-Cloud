package com.nowander.favor.controller;


import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.security.annotation.AnonymousAccess;
import com.nowander.favor.domain.favor.count.FavorCountService;
import com.nowander.favor.infrastructure.enums.FavorTargetType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wtk
 * @since 2022-01-28
 */
@RestController
@RequestMapping("/likes/counts")
@ResponseAdvice
@Slf4j
@AllArgsConstructor
public class FavorCountController {

    private FavorCountService favorCountService;

    /**
     * 获取点赞数
     * @param targetType
     * @param targetId
     * @return
     */
    @AnonymousAccess
    @GetMapping("/{targetType}/{targetId}")
    public Integer getLikeCount(@PathVariable FavorTargetType targetType,
                                @PathVariable Integer targetId){
        return favorCountService.getTotalCount(targetType, targetId);
    }
}

