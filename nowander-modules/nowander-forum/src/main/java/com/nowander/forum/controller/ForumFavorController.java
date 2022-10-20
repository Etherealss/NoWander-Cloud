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
 * @date 2022-10-18
 */
@Slf4j
@RestController
@RequestMapping("/favors")
@AllArgsConstructor
@ResponseAdvice
public class ForumFavorController {
    private final FavorService favorService;

    @PathVariableValidated
    @PostMapping("/records/articles/{articleId}")
    public void addArticleFavor(@PathVariable Integer articleId) {
        favorService.addFavor(
                FavorTargetType.ARTICLE,
                articleId,
                UserSecurityContextHolder.require().getUserId()
        );
    }

    @PathVariableValidated
    @PostMapping("/records/posts/{postId}")
    public void addPostFavor(@PathVariable Integer postId) {
        favorService.addFavor(
                FavorTargetType.POSTS,
                postId,
                UserSecurityContextHolder.require().getUserId()
        );
    }

    @PathVariableValidated
    @DeleteMapping("/records/articles/{articleId}")
    public void delArticleFavor(@PathVariable Integer articleId) {
        favorService.delFavor(
                FavorTargetType.ARTICLE,
                articleId,
                UserSecurityContextHolder.require().getUserId()
        );
    }

    @PathVariableValidated
    @DeleteMapping("/records/posts/{postId}")
    public void delPostFavor(@PathVariable Integer postId) {
        favorService.delFavor(
                FavorTargetType.POSTS,
                postId,
                UserSecurityContextHolder.require().getUserId()
        );
    }


    @PathVariableValidated
    @GetMapping("/records/articles/{articleId}")
    public boolean checkArticleFavor(@PathVariable Integer articleId) {
       return favorService.checkHasFavor(
                FavorTargetType.ARTICLE,
                articleId,
                UserSecurityContextHolder.require().getUserId());
    }

    @PathVariableValidated
    @GetMapping("/records/posts/{postId}")
    public boolean checkPostFavor(@PathVariable Integer postId) {
        return favorService.checkHasFavor(
                FavorTargetType.POSTS,
                postId,
                UserSecurityContextHolder.require().getUserId());
    }

    @AnonymousAccess
    @GetMapping("/counts/articles/{articleId}")
    public int getArticleFavorCount(@PathVariable Integer articleId) {
        return favorService.getFavorCount(
                FavorTargetType.ARTICLE,
                articleId
        );
    }

    @AnonymousAccess
    @GetMapping("/counts/posts/{postId}")
    public int getPostFavorCount(@PathVariable Integer postId) {
        return favorService.getFavorCount(
                FavorTargetType.POSTS,
                postId
        );
    }
}
