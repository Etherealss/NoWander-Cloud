package com.nowander.discussion.domain.discussion;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nowander.common.database.pojo.SimplePage;
import com.nowander.discussion.domain.discussion.reply.ReplyDTO;
import com.nowander.discussion.domain.discussion.reply.strategy.QueryReplyStrategy;
import com.nowander.discussion.domain.discussion.strategy.QueryCommentStrategy;
import com.nowander.discussion.infrastructure.enums.DiscussionParentType;
import com.nowander.discussion.infrastructure.feign.user.info.UserBriefDTO;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略上下午
 * @author wtk
 * @date 2022-02-05
 */
@NoArgsConstructor
public class DiscussionContext {

    /**
     * 获取评论的策略
     */
    private QueryCommentStrategy commentStrategy;
    /**
     * 获取回复的策略
     */
    private QueryReplyStrategy replyStrategy;

    /**
     * 获取评论
     * @param strategy
     * @return
     */
    public static DiscussionContext build4Comment(QueryCommentStrategy strategy) {
        DiscussionContext context = new DiscussionContext();
        context.commentStrategy = strategy;
        return context;
    }

    /**
     * 获取回复
     * @param strategy
     * @return
     */
    public static DiscussionContext build4Reply(QueryReplyStrategy strategy) {
        DiscussionContext context = new DiscussionContext();
        context.replyStrategy = strategy;
        return context;
    }

    /**
     * 获取评论数据
     * @param parentId
     * @param parentIdType
     * @return 包含了页面评论数据以及作者用户信息的map，key分别是page和authors
     */
    public Map<String, Object> query(int parentId, DiscussionParentType parentIdType) {
        IPage<DiscussionDTO> page = commentStrategy.queryComments(parentId, parentIdType);
        // 完善作者信息并返回
        return complete(page);
    }

    /**
     * 获取回复数据
     * @param commentId
     * @return 包含了页面回复数据以及作者用户信息的map，key分别是page和authors
     */
    public Map<String, Object> query(int commentId) {
        IPage<ReplyDTO> page = replyStrategy.queryReply(commentId);
        // 完善作者信息并返回
        return complete(page);
    }

    /**
     * 完善作者信息并返回
     * @param page
     * @return
     */
    private Map<String, Object> complete(IPage<?> page) {
        Map<Integer, UserBriefDTO> authors = commentStrategy.getAuthorsData();
        // 初始化为2的话，在第二次put的时候会扩容，初始化为3的话会转化为4
        Map<String, Object> map = new HashMap<>(4);
        map.put("page", new SimplePage<>(page));
        map.put("authors", authors);
        return map;
    }
}
