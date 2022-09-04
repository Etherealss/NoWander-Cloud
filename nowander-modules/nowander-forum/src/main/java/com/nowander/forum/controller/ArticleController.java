package com.nowander.forum.controller;

import com.nowander.common.core.enums.OrderType;
import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.database.pojo.SimplePage;
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

    public static final int ARTICLE_PAGE_SIZE = 10;

    @GetMapping("/{articleId}/details")
    public ArticleDetailDTO getArticleDetail(@PathVariable Integer articleId) {
        return articleService.getDetailById(articleId);
    }

    @PostMapping("/publish")
    public Integer publishArticle(@RequestBody @Validated ArticleDetailCommand command, Integer userId) {
        return articleService.save(command, userId);
    }

    @PutMapping("/{articleId}")
    public void updateArticle(@PathVariable Integer articleId,
                              @RequestBody @Validated ArticleDetailCommand article,
                              Integer userId) {
        articleService.update(articleId, article, userId);
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
    public SimplePage<ArticleDetailDTO> getPageCompetition(
            @RequestParam(value = "curPage", defaultValue = "1") int curPage,
            @RequestParam(value = "orderBy", defaultValue = "time") OrderType orderBy) {
        log.debug("获取分页数据：当前页curPage = {}, orderBy = {}", curPage, orderBy);
        return articleService.pageDetails(curPage, ARTICLE_PAGE_SIZE, orderBy);
    }
}

