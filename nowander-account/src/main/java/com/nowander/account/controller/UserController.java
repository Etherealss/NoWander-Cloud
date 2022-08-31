package com.nowander.account.controller;

import com.nowander.account.domain.user.UserBriefDTO;
import com.nowander.account.domain.user.UserService;
import com.nowander.account.domain.user.token.UserTokenService;
import com.nowander.account.domain.user.token.login.UserLoginCommand;
import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.security.UserCredential;
import com.nowander.common.security.annotation.AnonymousAccess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@ResponseAdvice
public class UserController {
    private final UserService userService;
    private final UserTokenService userTokenService;

    @AnonymousAccess
    @PostMapping("/login")
    public UserCredential login(@RequestBody @Validated UserLoginCommand userLoginCommand) {
        return userTokenService.login(userLoginCommand);
    }

    @GetMapping("/info/{userId}")
    public UserBriefDTO getUserBrief(@PathVariable Integer userId) {
        return userService.getBriefById(userId);
    }
}
