package com.nowander.forum.infrastruceture.feign.discussion;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wtk
 * @date 2022-10-25
 */
@FeignClient(
        value = "nowander-discussion",
        path = "/discussion/comments"
)
public interface DiscussionFeign {

    @PostMapping
    Integer publish(@RequestBody @Validated DiscussionCommand commentEntity);
}
