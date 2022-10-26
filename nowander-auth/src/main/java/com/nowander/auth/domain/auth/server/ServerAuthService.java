package com.nowander.auth.domain.auth.server;

import com.nowander.auth.domain.auth.server.accessibility.ServerAccessibilityService;
import com.nowander.auth.domain.auth.server.info.ServerAuthCommand;
import com.nowander.auth.domain.auth.server.info.ServerAuthInfoEntity;
import com.nowander.auth.domain.auth.server.info.ServerAuthInfoService;
import com.nowander.auth.infrastructure.config.ServerCredentialCacheConfig;
import com.nowander.auth.infrastructure.token.ITokenHandler;
import com.nowander.common.core.exception.rest.ParamErrorException;
import com.nowander.common.security.service.auth.server.ServerCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ServerAuthService {
    private final ServerAuthInfoService authInfoService;
    private final ServerAccessibilityService accessibilityService;
    private final ITokenHandler tokenHandler;
    private final ServerCredentialCacheConfig credentialCacheConfig;

    public ServerCredential verifySecretAndGet(Integer serverId, ServerAuthCommand command) {
        ServerAuthInfoEntity serverInfoEntity = authInfoService.verifyAndGet(
                serverId, command.getSecret()
        );
        return get(serverId, serverInfoEntity);
    }

    private ServerCredential get(Integer serverId, ServerAuthInfoEntity serverInfoEntity) {
        Set<Integer> accessibleServerIds =
                accessibilityService.selectAccessibleServerIds(serverId);
        ServerCredential serverCredential = new ServerCredential();
        serverCredential.setServerId(serverInfoEntity.getId());
        serverCredential.setServerName(serverInfoEntity.getServerName());
        serverCredential.setAccessibleServiceIds(accessibleServerIds);
        tokenHandler.createToken(serverCredential, credentialCacheConfig);
        return serverCredential;
    }

    public ServerCredential verifyAndGet(Integer serverId, String token) {
        ServerCredential serverCredential = tokenHandler.verifyToken(token, ServerCredential.class, credentialCacheConfig);
        if (!serverId.equals(serverCredential.getServerId())) {
            throw new ParamErrorException("serverId 不匹配");
        }
        return serverCredential;
    }


    @Transactional(rollbackFor = Exception.class)
    public Integer createAuthInfo(RegisterServerCredentialCommand command) {
        return authInfoService.createAuthInfo(command);
    }
}
