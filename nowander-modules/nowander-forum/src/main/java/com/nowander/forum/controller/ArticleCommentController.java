package com.nowander.forum.controller;

import com.nowander.common.core.enums.OrderType;
import com.nowander.common.core.interceptor.pathvariable.PathVariableValidated;
import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.security.annotation.AnonymousAccess;
import com.nowander.common.security.service.auth.user.UserSecurityContextHolder;
import com.nowander.forum.domain.article.discussion.ArticleDiscussionService;
import com.nowander.forum.domain.article.discussion.CreateArticleCommentCommand;
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
@RequestMapping("/articles/{articleId}/discussions")
@AllArgsConstructor
@ResponseAdvice
public class ArticleCommentController {

    private final ArticleDiscussionService articleDiscussionService;

    @PathVariableValidated
    @PostMapping("/comments")
    public Integer publishComment(@PathVariable Integer articleId,
                                  @RequestBody @Validated CreateArticleCommentCommand commentCommand) {
        return articleDiscussionService.publishComment(
                articleId,
                commentCommand,
                UserSecurityContextHolder.require().getUserId()
        );
    }

    @PathVariableValidated
    @AnonymousAccess
    @GetMapping("/comments")
    public Map<String, Object> pageComment(
            @RequestParam(value = "articleId") Integer articleId,
            @RequestParam(value = "curPage", defaultValue = "1") Integer curPage,
            @RequestParam(value = "orderBy", defaultValue = "1") OrderType orderBy,
            @RequestParam(value = "commentSize", defaultValue = "3") Integer commentSize,
            @RequestParam(value = "replySize", defaultValue = "3") Integer replySize) {
        return articleDiscussionService.pageComment(
                articleId, curPage, orderBy, commentSize, replySize
        );
    }

}

