package com.nowander.common.security.feign;

import com.nowander.common.security.config.ServerCredentialConfig;
import com.nowander.common.security.service.auth.server.IServerCredentialProvider;
import com.nowander.common.security.service.auth.server.ServerCredential;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 在每一次进行服务间请求前，在 header 中加上 serverToken
 * @author wtk
 * @date 2022-10-17
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ServerTokenFeignRequestInterceptor implements RequestInterceptor {

    private final ServerCredentialConfig config;
    private final IServerCredentialProvider serverCredentialProvider;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServerCredential serverCredential = serverCredentialProvider.get();
        requestTemplate.header(config.getHeaderName(), serverCredential.getToken());
    }
}
