package com.nowander.common.security.feign;

import com.nowander.common.security.service.auth.server.ServerAuthCommand;
import com.nowander.common.security.service.auth.server.ServerCredential;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wtk
 * @date 2022-10-08
 */
@FeignClient(
        value = "nowander-auth",
        contextId = "nowander-auth-servers-tokens",
        path = "/auth/servers",
        configuration = NoServerTokenFeignConfiguration.class
)
public interface ServerCredentialFeign {

    @GetMapping("/{serverId}/credentials/{token}")
    ServerCredential verify(@PathVariable Integer serverId, @PathVariable String token);

    /**
     * 获取token
     */
    @PostMapping("/{serverId}/credentials")
    ServerCredential createCredential(@PathVariable Integer serverId,
                                      @Validated @RequestBody ServerAuthCommand command);
}
