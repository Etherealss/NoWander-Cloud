package com.nowander.forum.infrastruceture.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nowander.common.core.enums.BaseEnum;
import com.nowander.common.core.exception.rest.EnumIllegalException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 寒洲
 * @description 三个大分区
 * @date 2020/10/7
 */
@Getter
@AllArgsConstructor
public enum Module implements BaseEnum {
    LEARNING(0, "learning"),
    MAJOR(1, "major"),
    COLLEGE(2, "college"),
    ;

    private final int code;

    private final String name;

    public static Module fromName(@JsonProperty("name") Object name) {
        for (Module object : Module.class.getEnumConstants()) {
            if (name.equals(object.getName())) {
                return object;
            }
        }
        throw new EnumIllegalException(Module.class, name);
    }
}
