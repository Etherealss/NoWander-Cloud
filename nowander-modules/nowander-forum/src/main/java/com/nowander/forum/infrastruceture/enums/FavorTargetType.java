package com.nowander.forum.infrastruceture.enums;

import com.nowander.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wtk
 */
@Getter
@AllArgsConstructor
public enum FavorTargetType implements BaseEnum {
    ARTICLE(1, "article"),
    POSTS(2, "posts"),
    ;
    private final int code;
    private final String name;
}
