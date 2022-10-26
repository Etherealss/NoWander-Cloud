package com.nowander.discussion.domain.discussion;


import com.baomidou.mybatisplus.annotation.TableName;
import com.nowander.common.database.pojo.entity.IdentifiedEntity;
import com.nowander.discussion.infrastructure.enums.DiscussionParentType;
import com.nowander.discussion.infrastructure.enums.DiscussionType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * 评论
 * @author wtk
 * @since 2022-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TableName("discussion")
public class DiscussionEntity extends IdentifiedEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 评论作者用户id
     */
    private Integer authorId;

    /**
     * 表示该记录在哪个文章（帖子）或评论之下，可用于表示评论和回复
     */
    private Integer parentId;

    /**
     * 该记录指向的目标Id，可以指向评论或回复对象
     */
    private Integer targetId;

    /**
     * 评论/回复内容
     */
    private String content;

    /**
     * 文章为0，问贴为1
     */
    private DiscussionParentType parentType;

    /**
     * 评论为0，回复为1，子回复为2
     */
    private DiscussionType discussionType;

    /**
     * 正常为1，已删除为0
     */
    private Integer state;

    public static DiscussionEntity build4Create(CreateCommentCommand command, DiscussionType discussionType, Integer userId) {
        DiscussionEntity entity = new DiscussionEntity();
        BeanUtils.copyProperties(command, entity);
        // 评论的 pid 和 tid 相同
        entity.setTargetId(command.getParentId());
        entity.setAuthorId(userId);
        entity.setState(1);
        entity.setDiscussionType(discussionType);
        return entity;
    }

    public static DiscussionEntity build4Create(CreateReplyCommand command, Integer userId) {
        DiscussionEntity entity = new DiscussionEntity();
        BeanUtils.copyProperties(command, entity);
        entity.setAuthorId(userId);
        entity.setState(1);
        entity.setParentType(DiscussionParentType.COMMENT);
        return entity;
    }
}
