package com.nowander.account.infrasturcture.feign.captcha;

import com.nowander.common.core.pojo.Msg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wtk
 * @date 2022-09-02
 */
@FeignClient(value = "nowander-captcha", path = "/captchas")
public interface CaptchaFeign {

    @PutMapping("/{captchaId}")
    Msg<Void> validateCaptcha(@PathVariable String captchaId, @RequestBody ValidateCaptchaCommand command);
}
