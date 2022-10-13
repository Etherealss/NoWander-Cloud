package com.nowander.favor.controller;


import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.security.annotation.InternalAuth;
import com.nowander.common.security.service.auth.user.UserSecurityContextHolder;
import com.nowander.favor.application.FavorApplicationService;
import com.nowander.favor.infrastructure.enums.FavorTargetType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 前端控制器
 * @author wtk
 * @since 2022-01-05
 */
@Slf4j
@RestController
@ResponseAdvice
@RequestMapping("/favors/records")
@AllArgsConstructor
public class FavorRecordController {

    private FavorApplicationService favorApplicationService;

    /**
     * 点赞
     * @return
     */
    @PostMapping("/{targetType}/{targetId}")
    @InternalAuth
    public void addFavor(@PathVariable("targetType") FavorTargetType targetType,
                       @PathVariable("targetId") Integer targetId) {
        favorApplicationService.addFavor(targetType, targetId, UserSecurityContextHolder.require().getUserId());
    }

    /**
     * 取消
     * @param targetType
     * @param targetId
     */
    @DeleteMapping("/{targetType}/{targetId}")
    public void delFavor(@PathVariable("targetType") FavorTargetType targetType,
                       @PathVariable("targetId") Integer targetId) {
        favorApplicationService.addFavor(targetType, targetId, UserSecurityContextHolder.require().getUserId());
    }

    /**
     * 是否已点赞
     * @return
     */
    @GetMapping("/{targetType}/{targetId}")
    public Boolean checkHasLike(@PathVariable("targetType") FavorTargetType targetType,
                                @PathVariable("targetId") Integer targetId) {
        return favorApplicationService.checkHasFavor(
                targetType, targetId, UserSecurityContextHolder.require().getUserId()
        );
    }
}

