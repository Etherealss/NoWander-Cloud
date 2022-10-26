package com.nowander.discussion.infrastructure.feign.user.info;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @author wtk
 * @date 2022-09-06
 */
@FeignClient(value = "nowander-account", path = "/account/users")
public interface AccountInfoFeign {

    @GetMapping("/{userId}")
    UserBriefDTO getUserBrief(@PathVariable Integer userId);

    @GetMapping
    List<UserBriefDTO> getBatchUserBrief(@RequestParam("ids") Collection<Integer> userIds);
}
