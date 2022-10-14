package com.nowander.auth.controller;

import com.nowander.auth.domain.auth.server.RegisterServerCredentialCommand;
import com.nowander.auth.domain.auth.server.ServerAuthService;
import com.nowander.auth.domain.auth.server.info.ServerAuthCommand;
import com.nowander.common.core.web.ResponseAdvice;
import com.nowander.common.security.annotation.AnonymousAccess;
import com.nowander.common.security.annotation.InternalAuth;
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
@RequestMapping("/auth/servers")
@RequiredArgsConstructor
@ResponseAdvice
public class ServerAuthController {
    private final ServerAuthService serverAuthService;

    @InternalAuth
    @PostMapping("/credentials")
    public Integer register(@Validated @RequestBody RegisterServerCredentialCommand command) {
        return serverAuthService.createAuthInfo(command);
    }

    /**
     * 获取token
     */
    @AnonymousAccess
    @PostMapping("/credentials/{id}")
    public ServerCredential getCredential(@PathVariable Integer id,
                                          @Validated @RequestBody ServerAuthCommand command) {
        return serverAuthService.verifyAndGetServerCredential(id, command.getSecret());
    }
}
