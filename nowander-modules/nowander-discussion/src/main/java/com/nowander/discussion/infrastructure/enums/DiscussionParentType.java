package com.nowander.discussion.infrastructure.enums;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.nowander.common.core.enums.BaseEnum;
import com.nowander.common.core.exception.rest.EnumIllegalException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 表示文章下的评论还是问贴下的评论
 * @author wtk
 * @date 2022-04-26
 */
@AllArgsConstructor
@Getter
public enum DiscussionParentType implements BaseEnum {

    ARTICLE(0, "article"),
    POSTS(1, "posts"),
    COMMENT(2, "comment"),
    ;
    private final int code;
    private final String name;

    public static DiscussionParentType fromName(@JsonProperty("name") Object name) {
        for (DiscussionParentType object : DiscussionParentType.class.getEnumConstants()) {
            if (name.equals(object.getName())) {
                return object;
            }
        }
        throw new EnumIllegalException(DiscussionParentType.class, name);
    }
}
