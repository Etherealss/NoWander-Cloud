package com.nowander.auth.controller;

import com.nowander.auth.domain.auth.server.ServerAuthService;
import com.nowander.auth.domain.auth.server.info.ServerAuthCommand;
import com.nowander.common.core.web.ResponseAdvice;
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
    @GetMapping("/credentials/{id}")
    public ServerCredential getServerCredential(@PathVariable Integer id) {
        return serverAuthService.getServerCredential(id);
    }

    @InternalAuth
    @PostMapping("/credentials/{id}")
    public void validate(@PathVariable Integer id, @Validated @RequestBody ServerAuthCommand command) {
        serverAuthService.validate(id, command.getSecret());
    }
}
