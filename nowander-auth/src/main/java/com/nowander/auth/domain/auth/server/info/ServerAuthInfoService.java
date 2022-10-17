package com.nowander.auth.domain.auth.server.info;

import com.nowander.auth.domain.auth.server.RegisterServerCredentialCommand;
import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.service.AuthenticationException;
import com.nowander.common.core.exception.service.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ServerAuthInfoService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final ServerInfoMapper infoMapper;

    @Transactional(rollbackFor = Exception.class)
    public ServerAuthInfoEntity verifyAndGet(Integer serverId, String secret) {
        ServerAuthInfoEntity serverInfoEntity = infoMapper.selectById(serverId);
        if (serverInfoEntity == null) {
            throw new NotFoundException("服务：" + serverId + "不存在");
        }
        if (!passwordEncoder.matches(secret, serverInfoEntity.getSecret())) {
            throw new AuthenticationException(ApiInfo.PASSWORD_ERROR, "服务秘钥错误");
        }
        return serverInfoEntity;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer createAuthInfo(RegisterServerCredentialCommand command) {
        ServerAuthInfoEntity entity = new ServerAuthInfoEntity(
                command.getServerName(),
                passwordEncoder.encode(command.getSecret())
        );
        infoMapper.insert(entity);
        return entity.getId();
    }
}
