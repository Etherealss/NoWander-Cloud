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
    ARTICLE(0, "article"),
    POSTS(1, "posts"),
    ;
    private final int code;
    private final String name;
}
