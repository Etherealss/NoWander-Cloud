package com.nowander.common.security.service.auth.server;

import com.nowander.common.security.config.ServerCredentialConfig;
import com.nowander.common.security.feign.ServerCredentialFeign;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author wtk
 * @date 2022-10-17
 */
@AllArgsConstructor
public class RemoteIServerCredentialProvider implements IServerCredentialProvider {
    private final ServerCredentialFeign serverCredentialFeign;
    private final ServerCredentialConfig config;
    private final RedisTemplate<String, ServerCredential> redisTemplate;

    @Override
    public ServerCredential create() {
        ServerAuthCommand command = new ServerAuthCommand(config.getServerId(), config.getSecret());
        return serverCredentialFeign.createCredential(command);
    }
}
