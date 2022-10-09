package com.nowander.discussion.domain.discussion;

import com.nowander.discussion.infrastructure.enums.DiscussionParentType;
import com.nowander.discussion.infrastructure.enums.DiscussionType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2022-10-09
 */
public class DiscussionCommand {

    /**
     * 表示该记录在哪个文章（帖子）或评论之下，可用于表示评论和回复
     */
    @NotNull
    private Integer parentId;

    /**
     * 该记录指向的目标Id，可以指向评论或回复对象
     */
    @NotNull
    private Integer targetId;

    /**
     * 评论/回复内容
     */
    @NotEmpty
    private String content;

    /**
     * 文章为0，问贴为1
     */
    @NotNull
    private DiscussionParentType parentType;

    /**
     * 评论为0，回复为1，子回复为2
     */
    @NotNull
    private DiscussionType discussionType;
}
