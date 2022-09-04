package com.nowander.forum.domain;

import com.nowander.common.database.pojo.entity.IdentifiedEntity;
import com.nowander.forum.infrastruceture.enums.Module;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.Set;

/**
 * @author wang tengkun
 * @date 2022/3/3
 */
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@ToString(callSuper = true)
public abstract class NoWanderBlog extends IdentifiedEntity {

    /**
     * 分区
     */
    protected Module module;
    /**
     * 分类
     */
    protected Integer category;

    /**
     * 作者Id
     */
    protected Integer authorId;

    /**
     * 标题
     */
    protected String title;

    /**
     * 标签
     */
    protected Set<String> labels = Collections.emptySet();
}
