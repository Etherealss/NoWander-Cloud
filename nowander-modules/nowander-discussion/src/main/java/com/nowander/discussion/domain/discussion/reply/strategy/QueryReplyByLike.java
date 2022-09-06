package com.nowander.discussion.domain.discussion.reply.strategy;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.discussion.domain.discussion.DiscussionEntity;
import com.nowander.discussion.infrastructure.enums.DiscussionParentType;

/**
 * @author wtk
 * @date 2022-02-05
 */
public class QueryReplyByLike extends QueryReplyStrategy {
    public QueryReplyByLike(int replySize, int curPage) {
        super(replySize, curPage, true);
    }

    @Override
    protected IPage<DiscussionEntity> getReplys(int parentCommentId) {
        return discussionMapper.getReplyByLike(new Page<>(curPage, replySize),
                parentCommentId, DiscussionParentType.COMMENT.getCode());
    }
}
