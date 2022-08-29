package com.nowander.forum.domain.posts;

import com.nowander.forum.infrastruceture.config.EsSearchProperties;
import com.nowander.forum.domain.NoWanderBlogEsEntity;
import com.nowander.forum.domain.NoWanderBlogEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wang tengkun
 * @date 2022/3/8
 */
@Service
@Slf4j
public class PostsEsService extends NoWanderBlogEsService {

    public PostsEsService(EsSearchProperties properties, ElasticsearchRestTemplate es) {
        super(properties, es);
    }

    public NoWanderBlogEsEntity save(PostsEntity postsEntity) {
        NoWanderBlogEsEntity esEntity = NoWanderBlogEsEntity.build(postsEntity);
        return super.save(esEntity);
    }
}
