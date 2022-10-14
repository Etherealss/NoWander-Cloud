package com.nowander.auth.domain.auth.server;

import com.nowander.auth.domain.auth.server.accessibility.ServerAccessibilityMapper;
import com.nowander.auth.domain.auth.server.info.ServerInfoEntity;
import com.nowander.auth.domain.auth.server.info.ServerInfoMapper;
import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.service.AuthenticationException;
import com.nowander.common.core.exception.service.NotFoundException;
import com.nowander.common.security.service.auth.server.ServerCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ServerAuthService {
    private final ServerInfoMapper infoMapper;
    private final ServerAccessibilityMapper accessibilityMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public ServerCredential validateAndGetServerCredential(Integer serverId, String secret) {
        ServerInfoEntity serverInfoEntity = validateAndGet(serverId, secret);
        return getServerCredential(serverId, serverInfoEntity);
    }

    public ServerCredential getServerCredential(Integer serverId) {
        ServerInfoEntity serverInfoEntity = infoMapper.selectById(serverId);
        return getServerCredential(serverId, serverInfoEntity);
    }

    private ServerCredential getServerCredential(Integer serverId, ServerInfoEntity serverInfoEntity) {
        Set<Integer> accessibleServerIds =
                accessibilityMapper.selectAccessibleServerIds(serverId);
        ServerCredential serverCredential = new ServerCredential();
        serverCredential.setServerId(serverInfoEntity.getId());
        serverCredential.setServerName(serverInfoEntity.getServerName());
        serverCredential.setAccessibleServiceIds(accessibleServerIds);
        return serverCredential;
    }

    public void validate(Integer serverId, String secret) {
        validateAndGet(serverId, secret);
    }

    private ServerInfoEntity validateAndGet(Integer serverId, String secret) {
        ServerInfoEntity serverInfoEntity = infoMapper.selectById(serverId);
        if (serverInfoEntity == null) {
            throw new NotFoundException("服务：" + serverId + "不存在");
        }
        if (passwordEncoder.matches(secret, serverInfoEntity.getSecret())) {
            throw new AuthenticationException(ApiInfo.PASSWORD_ERROR, "服务秘钥错误");
        }
        return serverInfoEntity;
    }
}
