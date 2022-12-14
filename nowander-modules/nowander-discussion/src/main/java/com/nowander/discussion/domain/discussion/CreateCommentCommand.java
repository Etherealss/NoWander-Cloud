package com.nowander.discussion.domain.discussion;

import com.nowander.discussion.infrastructure.enums.DiscussionParentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2022-10-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentCommand {

    /**
     * 文章为0，问贴为1
     */
    @NotNull
    private DiscussionParentType parentType;

    /**
     * 表示该记录在哪个文章（帖子）或评论之下，可用于表示评论和回复
     */
    @NotNull
    private Integer parentId;

    @NotNull
    private Integer authorId;

    /**
     * 评论/回复内容
     */
    @NotEmpty
    private String content;
}
