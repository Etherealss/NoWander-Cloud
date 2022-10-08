package com.nowander.common.security.feign;

import com.nowander.common.core.feign.FeignConfiguration;
import com.nowander.common.security.UserCredential;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wtk
 * @date 2022-10-08
 */
@FeignClient(value = "nowander-oss", path = "/users/tokens", configuration = FeignConfiguration.class)
public interface UserTokenFeign {

    @GetMapping("/{token}")
    UserCredential verifyToken(@PathVariable String token);
}
