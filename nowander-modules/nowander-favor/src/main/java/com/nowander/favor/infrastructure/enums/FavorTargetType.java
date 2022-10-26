package com.nowander.favor.infrastructure.enums;

import com.nowander.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 点赞目标类型
 * @author wtk
 * @date 2022-01-27
 */
@Getter
@AllArgsConstructor
public enum FavorTargetType implements BaseEnum {
    ARTICLE(0, "article"),
    POSTS(1, "posts"),
    COMMENT(2, "comment")
    ;
    private final int code;
    private final String name;
}
