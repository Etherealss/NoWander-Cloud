package com.nowander.common.security.service.auth;

import com.nowander.common.security.config.TokenVerifierConfiguration;

/**
 * 有两个实现类，分别是 RemoteTokenVerifier 以及 LocalTokenHandler
 * 其中，在 nowander-account 服务中，会使用 LocalTokenHandler 调用服务本地的验证方法
 * 而在其他服务中，会使用 RemoteTokenVerifier 远程调用 nowander-account 服务来验证 token
 * @see TokenVerifierConfiguration
 * @author wtk
 * @date 2022-10-08
 */
public interface ITokenVerifier {
    <T extends Credential> T verifyToken(String token, Class<T> credentialType);
}
