package com.nowander.auth.controller;

import com.nowander.auth.domain.auth.server.RegisterServerCredentialCommand;
import com.nowander.auth.domain.auth.server.ServerAuthService;
import com.nowander.auth.domain.auth.server.info.ServerAuthCommand;
import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.security.annotation.AnonymousAccess;
import com.nowander.common.security.service.auth.server.ServerCredential;
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
@RequestMapping("/servers")
@RequiredArgsConstructor
@ResponseAdvice
public class ServerCredentialController {
    private final ServerAuthService serverAuthService;

    /**
     * 注册服务
     * @param command
     * @return
     */
    @PostMapping
    public Integer register(@Validated @RequestBody RegisterServerCredentialCommand command) {
        return serverAuthService.createAuthInfo(command);
    }

    /**
     * 创建 token
     */
    @AnonymousAccess
    @PostMapping("/credentials")
    public ServerCredential createCredential(@Validated @RequestBody ServerAuthCommand command) {
        return serverAuthService.verifySecretAndGet(command);
    }

    @AnonymousAccess
    @GetMapping("/credentials/{token}")
    public ServerCredential verify(@PathVariable String token) {
        return serverAuthService.verifyAndGet(token);
    }
}
