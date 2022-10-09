package com.nowander.discussion.controller;

import com.nowander.common.core.enums.OrderType;
import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.security.SecurityContextHolder;
import com.nowander.common.security.annotation.AnonymousAccess;
import com.nowander.discussion.domain.discussion.DiscussionEntity;
import com.nowander.discussion.domain.discussion.DiscussionService;
import com.nowander.discussion.infrastructure.enums.DiscussionParentType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Slf4j
@RestController
@RequestMapping("/discussions")
@AllArgsConstructor
@ResponseAdvice
public class DiscussionController {

    private DiscussionService discussionService;

    @PostMapping
    public Integer publish(@RequestBody DiscussionEntity commentEntity) {
        return discussionService.saveDiscussion(commentEntity, SecurityContextHolder.require().getUserId());
    }

    @DeleteMapping("/{discussionId}")
    public void delete(@PathVariable("discussionId") Integer discussionId) {
        discussionService.deleteDiscussion(discussionId, SecurityContextHolder.require().getUserId());
    }

    /**
     * 获取评论
     * @param curPage 当前页码
     * @param parentId 评论目标ID
     * @param parentType 评论目标类型，文章下的评论还是问贴下的评论？
     * @param orderBy 排序方式
     * @param commentRows 评论显示数
     * @param replyRows 评论的回复显示数
     * @return 包含了页面评论数据以及作者用户信息的map，key分别是page和authors
     */
    @AnonymousAccess
    @GetMapping("/comments/{parentType}/{parentId}")
    public Map<String, Object> pageComment(
            @PathVariable(value = "parentType") DiscussionParentType parentType,
            @PathVariable(value = "parentId") Integer parentId,
            @RequestParam(value = "curPage", defaultValue = "1") Integer curPage,
            @RequestParam(value = "orderBy", defaultValue = "time") OrderType orderBy,
            @RequestParam(value = "commentRows", defaultValue = "3") Integer commentRows,
            @RequestParam(value = "replyRows", defaultValue = "3") Integer replyRows) {
        return discussionService.pageComments(parentId, parentType, curPage, commentRows, replyRows, orderBy);
    }

    /**
     * 获取评论下的回复
     * @param curPage 当前页码
     * @param commentId 评论ID
     * @param orderBy 排序方式
     * @param replyRows 回复显示数
     * @return 包含了页面评论数据以及作者用户信息的map，key分别是page和authors
     */
    @AnonymousAccess
    @GetMapping("/replys/{commentId}")
    public Map<String, Object> pageRepky(
            @PathVariable(value = "commentId") Integer commentId,
            @RequestParam(value = "curPage", defaultValue = "1") Integer curPage,
            @RequestParam(value = "orderBy", required = false) OrderType orderBy,
            @RequestParam(value = "replyRows", required = false) Integer replyRows) {
        return discussionService.pageReplys(commentId, curPage, replyRows, orderBy);
    }
}

