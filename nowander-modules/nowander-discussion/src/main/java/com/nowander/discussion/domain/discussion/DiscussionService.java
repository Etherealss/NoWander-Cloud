package com.nowander.discussion.domain.discussion;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.common.core.enums.OrderType;
import com.nowander.common.core.exception.service.NotAuthorException;
import com.nowander.common.core.exception.service.NotFoundException;
import com.nowander.common.core.pojo.Msg;
import com.nowander.discussion.domain.discussion.reply.strategy.QueryReplyByLike;
import com.nowander.discussion.domain.discussion.reply.strategy.QueryReplyByTime;
import com.nowander.discussion.domain.discussion.reply.strategy.QueryReplyStrategy;
import com.nowander.discussion.domain.discussion.strategy.QueryCommentByLike;
import com.nowander.discussion.domain.discussion.strategy.QueryCommentByTime;
import com.nowander.discussion.domain.discussion.strategy.QueryCommentStrategy;
import com.nowander.discussion.infrastructure.enums.DiscussionParentType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *
 * @author wtk
 * @since 2022-01-05
 */
@Service
@AllArgsConstructor
@Slf4j
public class DiscussionService extends ServiceImpl<DiscussionMapper, DiscussionEntity> {

    private DiscussionMapper discussionMapper;

    public Msg<IPage<DiscussionDTO>> getHotComments(Long parentId, Long userId) {
        return null;
    }

    public Map<String, Object> pageComments(
            Integer parentId, DiscussionParentType parentType, int curPage,
            int pageSize, int replySize, OrderType orderBy) {
        QueryCommentStrategy strategy;
        switch (orderBy) {
            case TIME:
                strategy = new QueryCommentByTime(pageSize, replySize, curPage);
                break;
            case LIKE:
            default:
                strategy = new QueryCommentByLike(pageSize, replySize, curPage);
        }
        Map<String, Object> map = DiscussionContext.build4Comment(strategy)
                .query(parentId, parentType);
        return map;
    }

    public Map<String, Object> pageReplys(
            Integer commentId, int curPage, int replySize, OrderType orderBy) {
        QueryReplyStrategy strategy;
        switch (orderBy) {
            case LIKE:
                strategy = new QueryReplyByLike(replySize, curPage);
                break;
            case TIME:
            default:
                strategy = new QueryReplyByTime(replySize, curPage);
        }
        Map<String, Object> map = DiscussionContext.build4Reply(strategy)
                .query(commentId);
        return map;
    }

    public void deleteDiscussion(Integer id, Integer authorId) {
        int i = discussionMapper.deleteByIdAndAuthor(id, authorId);
        if (i == 0) {
            // 没有删除
            Integer count = discussionMapper.selectCount(
                    new QueryWrapper<DiscussionEntity>().eq("id", id));
            if (count == 0) {
                throw new NotFoundException(DiscussionEntity.class, id.toString());
            } else {
                throw new NotAuthorException("id为" + authorId + "的用户不是id为" + id + "的评论的作者，无法删除");
            }
        }
    }
}
