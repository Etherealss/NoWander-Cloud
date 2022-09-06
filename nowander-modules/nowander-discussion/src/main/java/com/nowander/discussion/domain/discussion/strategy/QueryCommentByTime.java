package com.nowander.discussion.domain.discussion.strategy;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.discussion.domain.discussion.DiscussionEntity;
import com.nowander.discussion.infrastructure.enums.DiscussionParentType;

/**
 * @author wtk
 * @date 2022-02-05
 */
public class QueryCommentByTime extends QueryCommentStrategy {

    public QueryCommentByTime(int commentSize, int replySize, int curPage) {
        super(commentSize, replySize, curPage, false);
    }

    @Override
    protected IPage<DiscussionEntity> getComments(int parentId, DiscussionParentType parentIdType) {
        return discussionMapper.getCommentByTime(
                new Page<>(curPage, commentSize), parentId, parentIdType.getCode());
    }

    @Override
    protected IPage<DiscussionEntity> getReplys(int commentId) {
        return discussionMapper.getReplyByTime(
                new Page<>(0, replySize),
                commentId, DiscussionParentType.COMMENT.getCode());
    }
}
