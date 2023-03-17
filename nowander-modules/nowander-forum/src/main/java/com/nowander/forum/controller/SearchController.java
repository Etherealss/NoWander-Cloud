package com.nowander.forum.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nowander.common.core.exception.rest.ParamErrorException;
import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.security.annotation.AnonymousAccess;
import com.nowander.forum.domain.NoWanderBlogEsEntity;
import com.nowander.forum.domain.NoWanderBlogEsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author wang tengkun
 * @date 2022/4/26
 */
@Slf4j
@RestController
@RequestMapping("/search")
@AllArgsConstructor
@ResponseAdvice
public class SearchController {
    private final NoWanderBlogEsService noWanderBlogEsService;
    private final int TIPS_SIZE = 10;
    private final int HIGHLIGHT_SIZE = 10;

    @GetMapping("/tips/{prefixWord}")
    @AnonymousAccess
    public List<String> searchTip(@PathVariable String prefixWord) {
        return noWanderBlogEsService.searchTips(prefixWord, TIPS_SIZE);
    }

    @GetMapping("/highlight/pages/{curPage}")
    @AnonymousAccess
    public IPage<NoWanderBlogEsEntity> searchHighlight(@PathVariable Integer curPage, @RequestParam String word) {
        String decode;
        try {
            decode = URLDecoder.decode(word, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new ParamErrorException("文本无法解码为UTF-8格式：" + e.getMessage());
        }
        return noWanderBlogEsService.searchByHighLigh(decode, curPage, HIGHLIGHT_SIZE);
    }
}
