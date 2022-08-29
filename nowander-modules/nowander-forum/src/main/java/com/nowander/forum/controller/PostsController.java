package com.nowander.forum.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nowander.common.enums.OrderType;
import com.nowander.forum.domain.NoWanderBlog;
import com.nowander.forum.domain.posts.PostsEntity;
import com.nowander.forum.domain.posts.PostsService;
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
    public static final int POSTS_PAGE_SIZE = 10;

    private final PostsService postsService;

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
            @RequestParam(value = "orderBy",defaultValue = "time") OrderType orderBy) {
        return postsService.page(curPage, POSTS_PAGE_SIZE, orderBy);
    }
}

