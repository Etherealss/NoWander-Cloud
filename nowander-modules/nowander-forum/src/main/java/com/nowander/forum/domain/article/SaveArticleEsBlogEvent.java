package com.nowander.forum.domain.article;

import com.nowander.common.core.pojo.DomainEvent;
import com.nowander.forum.domain.article.content.ArticleContentEntity;
import lombok.Getter;

/**
 * @author wang tengkun
 * @date 2022/4/26
 */
@Getter
public class SaveArticleEsBlogEvent extends DomainEvent {
    private final ArticleEntity article;
    private final ArticleContentEntity articleContent;
    public SaveArticleEsBlogEvent(Integer userId, ArticleEntity article, ArticleContentEntity articleContent) {
        super(userId);
        this.article = article;
        this.articleContent = articleContent;
    }
}
