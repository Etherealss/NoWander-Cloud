package com.nowander.forum.domain.posts;

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
public class PostsPathVariableValidator implements PathVariableValidator {
    private final PostsService postsService;
    @Override
    public boolean validate(String... args) {
        log.info("检查 posts 是否存在");
        Objects.requireNonNull(args[0]);
        return postsService.getById(Integer.valueOf(args[0])) != null;
    }

    @Override
    public String validateTarget() {
        return "postsId";
    }
}
