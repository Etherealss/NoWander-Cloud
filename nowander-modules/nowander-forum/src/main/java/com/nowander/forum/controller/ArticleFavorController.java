package com.nowander.forum.controller;

import com.nowander.common.core.interceptor.pathvariable.PathVariableValidated;
import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.security.annotation.AnonymousAccess;
import com.nowander.common.security.service.auth.user.UserSecurityContextHolder;
import com.nowander.forum.domain.favor.FavorService;
import com.nowander.forum.infrastruceture.enums.FavorTargetType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Slf4j
@RestController
@RequestMapping("/articles/{articleId}/favors")
@AllArgsConstructor
@ResponseAdvice
public class ArticleFavorController {

    private final FavorService favorService;

    @PathVariableValidated
    @PostMapping("/records")
    public void addArticleFavor(@PathVariable Integer articleId) {
        favorService.addFavor(
                FavorTargetType.ARTICLE,
                articleId,
                UserSecurityContextHolder.require().getUserId()
        );
    }

    @PathVariableValidated
    @DeleteMapping("/records")
    public void delArticleFavor(@PathVariable Integer articleId) {
        favorService.delFavor(
                FavorTargetType.ARTICLE,
                articleId,
                UserSecurityContextHolder.require().getUserId()
        );
    }

    @PathVariableValidated
    @GetMapping("/records")
    public boolean checkArticleFavor(@PathVariable Integer articleId) {
        return favorService.checkHasFavor(
                FavorTargetType.ARTICLE,
                articleId,
                UserSecurityContextHolder.require().getUserId());
    }

    @PathVariableValidated
    @AnonymousAccess
    @GetMapping("/counts")
    public int getArticleFavorCount(@PathVariable Integer articleId) {
        return favorService.getFavorCount(
                FavorTargetType.ARTICLE,
                articleId
        );
    }
}

