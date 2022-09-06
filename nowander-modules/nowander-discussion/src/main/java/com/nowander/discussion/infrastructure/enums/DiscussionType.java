package com.nowander.discussion.infrastructure.enums;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.nowander.common.core.enums.BaseEnum;
import com.nowander.common.core.exception.rest.EnumIllegalException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wtk
 * @date 2022-04-26
 */
@AllArgsConstructor
@Getter
public enum DiscussionType implements BaseEnum {

    COMMENT(0, "comment"),
    REPLY_COMMENT(1, "reply"),
    SUB_REPLY(2, "subreply"),

    ;
    private final int code;
    private final String name;

    public static DiscussionType fromName(@JsonProperty("name") Object name) {
        for (DiscussionType object : DiscussionType.class.getEnumConstants()) {
            if (name.equals(object.getName())) {
                return object;
            }
        }
        throw new EnumIllegalException(DiscussionType.class, name);
    }
}
