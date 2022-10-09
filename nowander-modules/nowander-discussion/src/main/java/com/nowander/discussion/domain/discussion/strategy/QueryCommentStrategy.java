package com.nowander.discussion.domain.discussion.strategy;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.common.database.utils.PageUtil;
import com.nowander.discussion.domain.discussion.DiscussionDTO;
import com.nowander.discussion.domain.discussion.DiscussionEntity;
import com.nowander.discussion.domain.discussion.QueryDiscussionStrategy;
import com.nowander.discussion.infrastructure.enums.DiscussionParentType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wtk
 * @date 2022-02-05
 */
public abstract class QueryCommentStrategy extends QueryDiscussionStrategy {

    public QueryCommentStrategy(int commentSize, int replySize, int curPage, boolean getRefer) {
        super(commentSize, replySize, curPage, getRefer);
    }

    /**
     * 获取评论
     * @param parentId
     * @param parentIdType
     * @return
     */
    protected abstract IPage<DiscussionEntity> getComments(int parentId, DiscussionParentType parentIdType);

    /**
     * 获取评论下的回复记录
     * @param commentId
     * @return
     */
    protected abstract IPage<DiscussionEntity> getReplys(int commentId);

    public IPage<DiscussionDTO> queryComments(int parentId, DiscussionParentType parentIdType) {
        // 获取评论
        IPage<DiscussionEntity> comments = getComments(parentId, parentIdType);

        // 将 IPage<Comment> 转为 IPage<CommentDto>
        IPage<DiscussionDTO> page = new Page<>();
        PageUtil.copyPage(page, comments);

        // 包装评论信息，根据需要获取评论下的回复
        List<DiscussionDTO> dtos = new ArrayList<>(commentSize);
        for (DiscussionEntity discussionEntity : comments.getRecords()) {
            // 添加评论作者的id，后续会通过这些id获取User记录，保存到Map中
            this.addAuthorId2Set(discussionEntity.getAuthorId());
            DiscussionDTO dto = new DiscussionDTO();
            dto.setComment(discussionEntity);
            // 获取评论下的回复记录
            if (replySize > 0) {
                IPage<DiscussionEntity> replys = getReplys(discussionEntity.getId());
                dto.setReplys(replys);
            }
            dtos.add(dto);
        }
        page.setRecords(dtos);
        return page;
    }
}
