package com.nowander.forum.domain.posts;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.forum.domain.NoWanderBlogMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Mapper
@Repository
public interface PostsMapper extends NoWanderBlogMapper<PostsEntity> {

    /**
     * 按点赞数查询分页
     * @param page
     * @return
     */
    @Override
    IPage<PostsEntity> pageByLike(@Param("page") Page<?> page);

}
