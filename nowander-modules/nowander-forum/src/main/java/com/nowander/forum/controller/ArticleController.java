package com.nowander.forum.controller;

import com.nowander.common.core.enums.OrderType;
import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.database.pojo.SimplePage;
import com.nowander.common.security.annotation.AnonymousAccess;
import com.nowander.common.security.service.auth.user.UserSecurityContextHolder;
import com.nowander.forum.domain.article.ArticleDetailCommand;
import com.nowander.forum.domain.article.ArticleDetailDTO;
import com.nowander.forum.domain.article.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Slf4j
@RestController
@RequestMapping("/articles")
@AllArgsConstructor
@ResponseAdvice
public class ArticleController {

    private ArticleService articleService;

    @GetMapping("/{articleId}")
    @AnonymousAccess
    public ArticleDetailDTO getArticleDetail(@PathVariable Integer articleId) {
        return articleService.getDetailById(articleId);
    }

    @PostMapping
    public Integer publishArticle(@RequestBody @Validated ArticleDetailCommand command) {
        return articleService.save(command, UserSecurityContextHolder.require().getUserId());
    }

    @PutMapping("/{articleId}")
    public void updateArticle(@PathVariable Integer articleId,
                              @RequestBody @Validated ArticleDetailCommand article) {
        articleService.update(articleId, article, UserSecurityContextHolder.require().getUserId());
    }

    @DeleteMapping("/{articleId}")
    public void deleteArticle(@PathVariable Integer articleId) {
        articleService.deleteById(articleId);
    }

    /**
     * 分页获取
     * @param curPage
     * @param orderBy 排序方式
     * @return
     */
    @GetMapping
    @AnonymousAccess
    public SimplePage<ArticleDetailDTO> getPageCompetition(
            @RequestParam(value = "curPage", defaultValue = "1") int curPage,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "orderBy", defaultValue = "time") OrderType orderBy) {
        log.debug("获取分页数据：当前页curPage = {}, orderBy = {}", curPage, orderBy);
        return articleService.pageDetails(curPage, size, orderBy);
    }
}

