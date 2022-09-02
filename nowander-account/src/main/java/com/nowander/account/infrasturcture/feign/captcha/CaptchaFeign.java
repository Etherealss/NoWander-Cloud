package com.nowander.account.infrasturcture.feign.captcha;

import com.nowander.common.core.feign.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wtk
 * @date 2022-09-02
 */
@FeignClient(value = "nowander-captcha", path = "/captchas", configuration = FeignConfiguration.class)
public interface CaptchaFeign {

    @PutMapping("/{captchaId}")
    void validateCaptcha(@PathVariable String captchaId, @RequestBody ValidateCaptchaCommand command);
}
