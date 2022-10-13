package com.nowander.common.security.feign;

import com.nowander.common.core.utils.UUIDUtil;
import com.nowander.common.security.service.auth.server.ServerCredential;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wtk
 * @date 2022-10-08
 */
@FeignClient(
        value = "nowander-auth",
        contextId = "nowander-auth-servers-tokens",
        path = "/auth/servers/tokens"
)
public interface ServerTokenFeign {

    @GetMapping("/{token}")
    default ServerCredential verifyToken(@PathVariable String token) {
        ServerCredential credential = new ServerCredential();
        credential.setServerId(UUIDUtil.get());
        credential.setServerName("nowander-forum");

        return credential;
    }
}
