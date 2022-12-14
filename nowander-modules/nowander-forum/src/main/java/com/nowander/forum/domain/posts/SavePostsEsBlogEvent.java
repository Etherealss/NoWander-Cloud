package com.nowander.forum.domain.posts;

import com.nowander.common.core.pojo.DomainEvent;
import lombok.Getter;

/**
 * @author wang tengkun
 * @date 2022/4/26
 */
@Getter
public class SavePostsEsBlogEvent extends DomainEvent {
    private final PostsEntity postsEntity;

    public SavePostsEsBlogEvent(Integer userId, PostsEntity postsEntity) {
        super(postsEntity, userId);
        this.postsEntity = postsEntity;
    }
}
