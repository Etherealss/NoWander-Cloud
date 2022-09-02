package com.nowander.captcha.controller;


import cn.hutool.captcha.AbstractCaptcha;
import com.nowander.captcha.domain.captcha.CaptchaService;
import com.nowander.captcha.domain.captcha.CreateCaptcheCommand;
import com.nowander.captcha.domain.captcha.ValidateCaptchaCommand;
import com.nowander.common.core.pojo.Msg;
import com.nowander.common.core.utils.UUIDUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wtk
 * @description
 * @date 2021-10-05
 */
@RestController
@RequestMapping("/captchas")
@AllArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;

    /**
     * 获取验证码 字节流传输
     */
    @PostMapping("/byte")
    public void captcha(HttpServletResponse response, @RequestBody CreateCaptcheCommand command) throws IOException {
        AbstractCaptcha captcha = captchaService.getAndCacheCaptcha(command.getCaptchaId());
        // 关闭浏览器的缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        captcha.write(response.getOutputStream());
    }

    /**
     * 获取验证码 返回Base64
     * @param response
     * @return Base64
     * @throws IOException
     */
    @PostMapping("/base64")
    public Msg<Map<String, String>> captcha4Base64(HttpServletResponse response) {
        String uuid = UUIDUtil.getUuid().toString();
        String imageBase64 = captchaService.getAndCacheCaptcha(uuid).getImageBase64();
        Map<String, String> data = new HashMap<>(4);
        data.put("image", imageBase64);
        data.put("captchaId", uuid);
        return Msg.ok(data);
    }

    @PutMapping("/{captchaId}")
    public void validateCaptcha(@PathVariable String captchaId, @RequestBody ValidateCaptchaCommand command) {
        captchaService.validateCaptcha(captchaId, command);
    }
}
