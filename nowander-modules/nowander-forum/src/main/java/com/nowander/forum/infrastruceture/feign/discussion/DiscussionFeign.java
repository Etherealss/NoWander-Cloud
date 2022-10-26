package com.nowander.forum.infrastruceture.feign.discussion;

import com.nowander.common.core.enums.OrderType;
import com.nowander.common.security.annotation.AnonymousAccess;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author wtk
 * @date 2022-10-25
 */
@FeignClient(
        value = "nowander-discussion",
        path = "/discussion/discussions"
)
public interface DiscussionFeign {

    @PostMapping("/comments")
    Integer publishComment(@RequestBody @Validated CreateCommentCommand command);


    /**
     * 获取评论。需要通过论坛接口内部调用本接口
     * @param curPage 当前页码
     * @param parentId 评论目标ID
     * @param parentType 评论目标类型，文章下的评论还是问贴下的评论？
     * @param orderBy 排序方式
     * @param commentSize 评论显示数
     * @param replySize 评论的回复显示数
     * @return 包含了页面评论数据以及作者用户信息的map，key分别是page和authors
     */
    @AnonymousAccess
    @GetMapping("/comments")
    Map<String, Object> pageComment(
            @RequestParam(value = "parentType") DiscussionParentType parentType,
            @RequestParam(value = "parentId") Integer parentId,
            @RequestParam(value = "curPage", defaultValue = "1") Integer curPage,
            @RequestParam(value = "orderBy", defaultValue = "1") OrderType orderBy,
            @RequestParam(value = "commentSize", defaultValue = "3") Integer commentSize,
            @RequestParam(value = "replySize", defaultValue = "3") Integer replySize);
}
