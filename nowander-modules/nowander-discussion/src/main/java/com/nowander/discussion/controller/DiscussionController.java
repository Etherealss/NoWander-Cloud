package com.nowander.discussion.controller;

import com.nowander.common.core.enums.OrderType;
import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.security.annotation.AnonymousAccess;
import com.nowander.common.security.annotation.InternalAuth;
import com.nowander.common.security.service.auth.user.UserSecurityContextHolder;
import com.nowander.discussion.domain.discussion.CreateCommentCommand;
import com.nowander.discussion.domain.discussion.CreateReplyCommand;
import com.nowander.discussion.domain.discussion.DiscussionEntity;
import com.nowander.discussion.domain.discussion.DiscussionService;
import com.nowander.discussion.infrastructure.enums.DiscussionParentType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
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

    @AnonymousAccess
    @GetMapping("/{discussionId}")
    public DiscussionEntity get(@PathVariable Integer discussionId) {
        return discussionService.getById(discussionId);
    }


    @DeleteMapping("/{discussionId}")
    public void delete(@PathVariable("discussionId") Integer id) {
        discussionService.deleteDiscussion(id, UserSecurityContextHolder.require().getUserId());
    }

    @InternalAuth
    @PostMapping("/comments")
    public Integer publishComment(@RequestBody @Validated CreateCommentCommand commentEntity) {
        return discussionService.createComment(
                commentEntity,
                UserSecurityContextHolder.require().getUserId()
        );
    }

    /**
     * 获取评论。需要通过论坛接口内部调用本接口
     * @param curPage 当前页码
     * @param parentId 评论目标ID
     * @param parentType 评论目标类型，文章下的评论还是问贴下的评论？
     * @param orderBy 排序方式
     * @param commentSize 评论显示数
     * @param replySize 评论的回复显示数
     * @return 包含了页面评论数据以及作者用户信息的map，key分别是page和authors
     */
    @AnonymousAccess
    @GetMapping("/comments")
    public Map<String, Object> pageComment(
            @RequestParam(value = "parentType") DiscussionParentType parentType,
            @RequestParam(value = "parentId") Integer parentId,
            @RequestParam(value = "curPage", defaultValue = "1") Integer curPage,
            @RequestParam(value = "orderBy", defaultValue = "1") OrderType orderBy,
            @RequestParam(value = "commentSize", defaultValue = "3") Integer commentSize,
            @RequestParam(value = "replySize", defaultValue = "3") Integer replySize) {
        return discussionService.pageComments(parentId, parentType, curPage, commentSize, replySize, orderBy);
    }

    @PostMapping("/comments/{commentId}/replys")
    public Integer publishReply(@RequestBody @Validated CreateReplyCommand createReplyCommand) {
        return discussionService.createReply(createReplyCommand,
                UserSecurityContextHolder.require().getUserId());
    }

    /**
     * 获取评论下的回复
     * @param curPage 当前页码
     * @param commentId 评论ID
     * @param orderBy 排序方式
     * @param replySize 回复显示数
     * @return 包含了页面评论数据以及作者用户信息的map，key分别是page和authors
     */
    @AnonymousAccess
    @GetMapping("/comments/{commentId}/replys")
    public Map<String, Object> pageRepky(
            @PathVariable(value = "commentId") Integer commentId,
            @RequestParam(value = "curPage", defaultValue = "1") Integer curPage,
            @RequestParam(value = "orderBy", defaultValue = "1") OrderType orderBy,
            @RequestParam(value = "replySize", defaultValue = "3") Integer replySize) {
        return discussionService.pageReplys(commentId, curPage, replySize, orderBy);
    }
}

