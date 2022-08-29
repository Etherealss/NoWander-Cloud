package com.nowander.forum.domain.posts;

import com.nowander.forum.domain.NoWanderBlogMapper;
import com.nowander.forum.domain.NoWanderBlogService;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Service
public class PostsService extends NoWanderBlogService<PostsEntity> {
    private final PostsMapper postsMapper;

    public PostsService(NoWanderBlogMapper<PostsEntity> blogMapper, PostsMapper postsMapper) {
        super(blogMapper);
        this.postsMapper = postsMapper;
    }
}
