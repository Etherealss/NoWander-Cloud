package com.nowander.forum.controller;

import com.nowander.common.core.web.ResponseAdvice;
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

    @PostMapping("/records/articles/{articleId}")
    public void addArticleFavor(@PathVariable Integer articleId) {
        favorService.addFavor(
                FavorTargetType.ARTICLE,
                articleId,
                UserSecurityContextHolder.require().getUserId()
        );
    }

    @PostMapping("/records/posts/{postId}")
    public void addPostFavor(@PathVariable Integer postId) {
        favorService.addFavor(
                FavorTargetType.POSTS,
                postId,
                UserSecurityContextHolder.require().getUserId()
        );
    }

    @DeleteMapping("/records/articles/{articleId}")
    public void delArticleFavor(@PathVariable Integer articleId) {
        favorService.delFavor(
                FavorTargetType.ARTICLE,
                articleId,
                UserSecurityContextHolder.require().getUserId()
        );
    }

    @DeleteMapping("/records/posts/{postId}")
    public void delPostFavor(@PathVariable Integer postId) {
        favorService.delFavor(
                FavorTargetType.POSTS,
                postId,
                UserSecurityContextHolder.require().getUserId()
        );
    }


    @GetMapping("/records/articles/{articleId}")
    public void checkArticleFavor(@PathVariable Integer articleId) {
        favorService.checkHasFavor(
                FavorTargetType.ARTICLE,
                articleId,
                UserSecurityContextHolder.require().getUserId());
    }

    @GetMapping("/records/posts/{postId}")
    public void checkPostFavor(@PathVariable Integer postId) {
        favorService.checkHasFavor(
                FavorTargetType.POSTS,
                postId,
                UserSecurityContextHolder.require().getUserId());
    }

    @GetMapping("/counts/articles/{articleId}")
    public int getArticleFavorCount(@PathVariable Integer articleId) {
        return favorService.getFavorCount(
                FavorTargetType.ARTICLE,
                articleId
        );
    }

    @GetMapping("/counts/posts/{postId}")
    public int getPostFavorCount(@PathVariable Integer postId) {
        return favorService.getFavorCount(
                FavorTargetType.POSTS,
                postId
        );
    }
}
