package com.nowander.forum.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleCommentCommand {

    /**
     * 评论内容
     */
    @NotEmpty
    private String content;
}