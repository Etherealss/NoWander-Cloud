package com.nowander.forum.domain.article;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nowander.forum.domain.NoWanderBlog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author wtk
 * @since 2022-01-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@TableName("article")
public class ArticleEntity extends NoWanderBlog {

    /**
     * 收藏数
     */
    private Integer collected;
}