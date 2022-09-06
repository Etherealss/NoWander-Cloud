package com.nowander.account.controller;

import com.nowander.account.domain.user.UserBriefDTO;
import com.nowander.account.domain.user.UserService;
import com.nowander.common.core.web.ResponseAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Slf4j
@RestController
@RequestMapping("/users/info")
@RequiredArgsConstructor
@ResponseAdvice
public class UserInfoController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public UserBriefDTO getUserBrief(@PathVariable Integer userId) {
        return userService.getBriefById(userId);
    }

    @GetMapping
    public List<UserBriefDTO> getBatchUserBrief(@RequestParam("ids") Collection<Integer> userIds) {
        return userService.getBatchBriefsByIds(userIds);
    }
}
