package com.nowander.forum.domain.article;

import com.nowander.common.core.pojo.Converter;
import com.nowander.forum.infrastruceture.enums.Module;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wang tengkun
 * @date 2022/3/3
 */
@Data
public class ArticleDetailCommand implements Converter<ArticleEntity> {

    /**
     * 分类
     */
    @NotNull
    private Integer category;

    /**
     * 分区
     */
    @NotNull
    private Module module;

    /**
     * 标题
     */
    @NotBlank
    @Length(min = 5, max = 30)
    private String title;

    /**
     * 文章内容
     */
    @NotNull
    @Length(min = 10)
    private String content;

    /**
     * 标签
     */
    private List<String> labels;

}
