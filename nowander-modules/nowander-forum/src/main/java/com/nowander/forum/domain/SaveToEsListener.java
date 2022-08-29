package com.nowander.forum.domain;

import com.nowander.forum.domain.article.ArticleEsService;
import com.nowander.forum.domain.article.SaveArticleEsBlogEvent;
import com.nowander.forum.domain.posts.PostsEsService;
import com.nowander.forum.domain.posts.SavePostsEsBlogEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wang tengkun
 * @date 2022/3/8
 */
@Component
@AllArgsConstructor
@Slf4j
public class SaveToEsListener {

    private final ArticleEsService articleEsService;
    private final PostsEsService postsEsService;

    @EventListener(SaveArticleEsBlogEvent.class)
    public void handleEvent(SaveArticleEsBlogEvent event) {
        articleEsService.save(event.getArticle(), event.getArticleContent());
    }

    @EventListener(SavePostsEsBlogEvent.class)
    public void handleEvent(SavePostsEsBlogEvent event) {
        postsEsService.save(event.getPostsEntity());
    }
}
