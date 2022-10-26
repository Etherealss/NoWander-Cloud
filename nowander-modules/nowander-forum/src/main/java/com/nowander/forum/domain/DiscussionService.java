package com.nowander.forum.domain;

import com.nowander.common.core.enums.OrderType;
import com.nowander.forum.infrastruceture.feign.discussion.CreateCommentCommand;
import com.nowander.forum.infrastruceture.feign.discussion.DiscussionFeign;
import com.nowander.forum.infrastruceture.feign.discussion.DiscussionParentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wtk
 * @date 2022-10-26
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DiscussionService {
    private final DiscussionFeign discussionFeign;

    public Integer publishComment(Integer articleId,
                                  DiscussionParentType discussionParentType,
                                  CreateArticleCommentCommand command,
                                  Integer userId) {
        CreateCommentCommand createCommentCommand = new CreateCommentCommand();
        createCommentCommand.setParentType(discussionParentType);
        createCommentCommand.setParentId(articleId);
        createCommentCommand.setContent(command.getContent());
        createCommentCommand.setAuthorId(userId);
        return discussionFeign.publishComment(createCommentCommand);
    }


    /**
     * 获取评论。需要通过论坛接口内部调用本接口
     * @param curPage 当前页码
     * @param articleId 要获取评论的文章id
     * @param orderBy 排序方式
     * @param commentSize 评论显示数
     * @param replySize 评论的回复显示数
     * @return 包含了页面评论数据以及作者用户信息的map，key分别是page和authors
     */
    public Map<String, Object> pageComment(
            Integer articleId,
            DiscussionParentType discussionParentType,
            Integer curPage,
            OrderType orderBy,
            Integer commentSize,
            Integer replySize) {
        return discussionFeign.pageComment(
                discussionParentType,
                articleId, curPage, orderBy, commentSize, replySize
        );
    }
}
