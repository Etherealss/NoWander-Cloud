package com.nowander.discussion.domain.discussion.reply;


import com.nowander.discussion.domain.discussion.DiscussionEntity;
import lombok.Data;

/**
 * @author wtk
 * @date 2022-02-05
 */
@Data
public class ReplyDTO {
    /**
     * 评论的回复
     */
    private DiscussionEntity reply;
    /**
     * 回复的引用（“被提及的评论”)
     */
    private DiscussionEntity referComment;
}
