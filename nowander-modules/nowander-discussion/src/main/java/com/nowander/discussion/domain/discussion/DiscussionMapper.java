package com.nowander.discussion.domain.discussion;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Mapper
@Repository
public interface DiscussionMapper extends BaseMapper<DiscussionEntity> {

    /**
     * 根据id和authorId删除
     * @param commentId
     * @param authorId
     * @return
     */
    int deleteByIdAndAuthor(@Param("commentId") Integer commentId,
                            @Param("authorId") Integer authorId);

    IPage<DiscussionEntity> getCommentByTime(IPage<DiscussionEntity> page,
                                             @Param("parentId") int parentId,
                                             @Param("parentType") int parentType);

    IPage<DiscussionEntity> getCommentByLike(IPage<DiscussionEntity> page,
                                             @Param("parentId") int parentId,
                                             @Param("parentType") int parentType);

    IPage<DiscussionEntity> getReplyByTime(IPage<DiscussionEntity> page,
                                           @Param("parentId") int parentId,
                                           @Param("parentType") int parentType);

    IPage<DiscussionEntity> getReplyByLike(IPage<DiscussionEntity> page,
                                           @Param("parentId") int parentId,
                                           @Param("parentType") int parentType);
}
