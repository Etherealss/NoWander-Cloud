package com.nowander.auth.controller;

import com.nowander.auth.domain.auth.user.UserAuthService;
import com.nowander.auth.domain.auth.user.UserLoginCommand;
import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.security.annotation.AnonymousAccess;
import com.nowander.common.security.service.auth.user.UserCredential;
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
public class UserAuthController {
    private final UserAuthService userAuthService;

    @AnonymousAccess
    @PostMapping(value = {"/login"})
    public UserCredential login(@RequestBody @Validated UserLoginCommand userLoginCommand) {
        return userAuthService.loginAndCreateCredential(userLoginCommand);
    }

    @AnonymousAccess
    @GetMapping("/credentials/{token}")
    public UserCredential verify(@PathVariable String token) {
        return userAuthService.verifyAndGet(token);
    }

    @DeleteMapping(value = {"logout"})
    public void logout(@RequestHeader("${app.token.user.header-name}") String token) {
        userAuthService.logoutAndDeleteCredential(token);
    }
}
