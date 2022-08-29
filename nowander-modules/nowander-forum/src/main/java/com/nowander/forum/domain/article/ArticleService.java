package com.nowander.forum.domain.article;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nowander.common.enums.OrderType;
import com.nowander.common.exception.service.NotAuthorException;
import com.nowander.common.exception.service.NotFoundException;
import com.nowander.common.pojo.SimplePage;
import com.nowander.forum.domain.NoWanderBlogEsEntity;
import com.nowander.forum.domain.NoWanderBlogMapper;
import com.nowander.forum.domain.NoWanderBlogService;
import com.nowander.forum.domain.article.content.ArticleContentEntity;
import com.nowander.forum.domain.article.content.ArticleContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wtk
 * @date 2022-01-05
 */
@Slf4j
@Service
public class ArticleService extends NoWanderBlogService<ArticleEntity> {

    private final ArticleContentService articleContentService;
    private final ArticleMapper articleMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ArticleService(NoWanderBlogMapper<ArticleEntity> noWanderBlogMapper,
                          ArticleContentService articleContentService,
                          ArticleMapper articleMapper,
                          ArticleEsService articleEsService,
                          ApplicationEventPublisher applicationEventPublisher) {
        super(noWanderBlogMapper);
        this.articleContentService = articleContentService;
        this.articleMapper = articleMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public ArticleContentEntity getContentById(Integer id) {
        return articleContentService.getById(id);
    }

    public ArticleDetailDTO getDetailById(Integer id) {
        ArticleEntity articleEntity = articleMapper.selectById(id);
        if (articleEntity == null) {
            throw new NotFoundException(ArticleEntity.class, id.toString());
        }
        ArticleContentEntity articleContentEntity = articleContentService.getById(id);
        return ArticleDetailDTO.build(articleEntity, articleContentEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer save(ArticleDetailCommand command, Integer userId) {
        ArticleEntity articleEntity = new ArticleEntity();
        BeanUtils.copyProperties(command, articleEntity);
        articleEntity.setAuthorId(userId);
        articleMapper.insert(articleEntity);

        ArticleContentEntity articleContentEntity = articleContentService.save(articleEntity.getId(), command);

        applicationEventPublisher.publishEvent(new SaveArticleEsBlogEvent(userId, articleEntity, articleContentEntity));

        return articleEntity.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Integer articleId, ArticleDetailCommand command, Integer userId) {
        ArticleEntity articleEntity = command.convert();
        ArticleEntity article = articleMapper.selectById(articleId);
        if (!article.getAuthorId().equals(userId)) {
            throw new NotAuthorException();
        }
        articleMapper.updateById(articleEntity);
        if (!StrUtil.isBlank(command.getContent())) {
            articleContentService.updateContent(articleId, command);
        }
    }

    private NoWanderBlogEsEntity buildDocEsDTO(ArticleDetailCommand detail) {
        NoWanderBlogEsEntity noWanderBlogEsEntity = new NoWanderBlogEsEntity();
        BeanUtils.copyProperties(noWanderBlogEsEntity, detail);
        return noWanderBlogEsEntity;
    }

    /**
     * 分页，包含内容
     */
    public SimplePage<ArticleDetailDTO> pageDetails(int curPage, int size, OrderType orderBy) {
        IPage<ArticleEntity> page = super.page(curPage, size, orderBy);
        List<ArticleDetailDTO> articleDetails = page.getRecords().stream().map(article -> {
            ArticleContentEntity articleContentEntity = articleContentService.getById(article.getId());
            return ArticleDetailDTO.build(article, articleContentEntity);
        }).collect(Collectors.toList());
        SimplePage<ArticleDetailDTO> detailPage = new SimplePage<>(
                articleDetails,
                page.getTotal(),
                page.getSize(),
                page.getCurrent()
        );
        return detailPage;
    }
}
