package com.nowander.forum.domain.article;

import com.nowander.forum.domain.EsSearchProperties;
import com.nowander.forum.domain.NoWanderBlogEsEntity;
import com.nowander.forum.domain.NoWanderBlogEsService;
import com.nowander.forum.domain.article.content.ArticleContentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wang tengkun
 * @date 2022/3/8
 */
@Service
@Slf4j
public class ArticleEsService extends NoWanderBlogEsService {

    public ArticleEsService(EsSearchProperties properties, ElasticsearchRestTemplate es) {
        super(properties, es);
    }

    public NoWanderBlogEsEntity save(ArticleEntity article, ArticleContentEntity articleContent) {
        NoWanderBlogEsEntity esEntity = NoWanderBlogEsEntity.build(article, articleContent);
        return super.save(esEntity);
    }
}
