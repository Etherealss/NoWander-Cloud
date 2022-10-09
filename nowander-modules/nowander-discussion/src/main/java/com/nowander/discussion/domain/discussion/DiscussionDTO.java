package com.nowander.discussion.domain.discussion;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

/**
 * @author wtk
 * @date 2022-02-04
 */
@Data
public class DiscussionDTO {

    /**
     * 顶层评论
     */
    private DiscussionEntity comment;

    /**
     * 评论的回复列表
     */
    private IPage<DiscussionEntity> replys;
}
