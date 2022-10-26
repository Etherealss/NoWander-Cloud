package com.nowander.forum.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nowander.common.core.enums.OrderType;
import com.nowander.common.core.interceptor.pathvariable.PathVariableValidated;
import com.nowander.common.security.annotation.AnonymousAccess;
import com.nowander.common.security.service.auth.user.UserSecurityContextHolder;
import com.nowander.forum.domain.NoWanderBlog;
import com.nowander.forum.domain.FavorService;
import com.nowander.forum.domain.posts.PostsEntity;
import com.nowander.forum.domain.posts.PostsService;
import com.nowander.forum.infrastruceture.enums.FavorTargetType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author wtk
 * @since 2022-01-05
 */
@Slf4j
@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostsController {
    private final PostsService postsService;
    private final FavorService favorService;


    @GetMapping("/{postsId}")
    public NoWanderBlog getById(@PathVariable Integer postsId) {
        return postsService.getById(postsId);
    }

    /**
     * 分页获取
     * @param curPage
     * @param orderBy 排序方式
     * @return
     */
    @GetMapping
    public IPage<PostsEntity> getPageCompetition(
            @RequestParam(value = "curPage", defaultValue = "1") int curPage,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "orderBy",defaultValue = "time") OrderType orderBy) {
        return postsService.page(curPage, size, orderBy);
    }

    /* ******************    点赞    ****************** */

    @PathVariableValidated
    @PostMapping("/{postId}/favors/records")
    public void addPostFavor(@PathVariable Integer postId) {
        favorService.addFavor(
                FavorTargetType.POSTS,
                postId,
                UserSecurityContextHolder.require().getUserId()
        );
    }

    @PathVariableValidated
    @DeleteMapping("/{postId}/favors/records")
    public void delPostFavor(@PathVariable Integer postId) {
        favorService.delFavor(
                FavorTargetType.POSTS,
                postId,
                UserSecurityContextHolder.require().getUserId()
        );
    }


    @PathVariableValidated
    @GetMapping("/{postId}/favors/records")
    public boolean checkPostFavor(@PathVariable Integer postId) {
        return favorService.checkHasFavor(
                FavorTargetType.POSTS,
                postId,
                UserSecurityContextHolder.require().getUserId());
    }

    @AnonymousAccess
    @GetMapping("/{postId}/favors/counts")
    public int getPostFavorCount(@PathVariable Integer postId) {
        return favorService.getFavorCount(
                FavorTargetType.POSTS,
                postId
        );
    }
}

