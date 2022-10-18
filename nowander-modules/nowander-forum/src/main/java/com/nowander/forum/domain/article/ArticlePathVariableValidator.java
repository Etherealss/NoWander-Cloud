package com.nowander.forum.domain.article;

import com.nowander.common.core.interceptor.pathvariable.PathVariableValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 检验articleId是否存在
 * @author wtk
 * @date 2022-04-26
 */
@Component
@Slf4j
@AllArgsConstructor
public class ArticlePathVariableValidator implements PathVariableValidator {
    private final ArticleService articleService;
    @Override
    public boolean validate(String... args) {
        log.debug("检查 article 是否存在");
        Objects.requireNonNull(args[0]);
        return articleService.getById(Integer.valueOf(args[0])) != null;
    }

    @Override
    public String validateTarget() {
        return "articleId";
    }
}
