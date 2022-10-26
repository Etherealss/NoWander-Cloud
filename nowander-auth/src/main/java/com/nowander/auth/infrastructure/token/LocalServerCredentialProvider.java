package com.nowander.auth.infrastructure.token;

import com.nowander.auth.domain.auth.server.ServerAuthService;
import com.nowander.auth.domain.auth.server.info.ServerAuthCommand;
import com.nowander.common.security.config.ServerCredentialConfig;
import com.nowander.common.security.service.auth.server.IServerCredentialProvider;
import com.nowander.common.security.service.auth.server.ServerCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2022-10-17
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class LocalServerCredentialProvider implements IServerCredentialProvider {
    private final ServerCredentialConfig serverCredentialConfig;
    private final ServerAuthService serverAuthService;

    @Override
    public ServerCredential create() {
        ServerAuthCommand command = new ServerAuthCommand(
                serverCredentialConfig.getSecret()
        );
        return serverAuthService.verifySecretAndGet(serverCredentialConfig.getServerId(), command);
    }
}
