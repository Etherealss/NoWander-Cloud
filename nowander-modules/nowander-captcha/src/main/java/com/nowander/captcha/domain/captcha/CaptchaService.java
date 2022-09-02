package com.nowander.captcha.domain.captcha;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.core.util.StrUtil;
import com.nowander.captcha.infrastructure.config.CaptchaConfiguration;
import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.service.CaptchaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author wang tengkun
 * @date 2022/2/23
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CaptchaService {

    private final RedisTemplate<String, String> redis;
    private final CaptchaConfiguration config;

    /**
     * 获取并缓存
     * @return
     */
    public AbstractCaptcha getAndCacheCaptcha(String captchaId) {
        AbstractCaptcha captcha = CaptchaUtil.createLineCaptcha(
                config.getWidth(),
                config.getHeight(),
                config.getCharCount(),
                config.getLineCount()
        );
        setCaptchaCache(captcha, captchaId);
        return captcha;
    }

    /**
     * 缓存到 Redis 中，5分钟超时
     * @param captcha
     * @param captchaId
     */
    private void setCaptchaCache(AbstractCaptcha captcha, String captchaId) {
        String code = captcha.getCode();
        String captchacCacheKey = config.getCacheKey();
        Integer captchaTimeoutSecond = config.getTimeoutSecond();
        log.debug("缓存验证码，id：{}，code：{}", captchacCacheKey + captchaId, code);
        redis.opsForValue().set(captchacCacheKey + captchaId,
                code, captchaTimeoutSecond, TimeUnit.SECONDS);
    }

    public void validateCaptcha(String captchaId, ValidateCaptchaCommand command) {
        String userInputCaptcha = command.getUserInputCaptcha();
        String code = redis.opsForValue().getAndDelete(config.getCacheKey() + captchaId);

        //验证码是否为空
        if (StrUtil.isBlank(userInputCaptcha)) {
            throw new CaptchaException(ApiInfo.CAPTCHA_MISSING);
        }

        // TODO 用于测试
        if ("1234".equals(userInputCaptcha)) {
            return;
        }

        // 验证码失效
        if (code == null) {
            throw new CaptchaException(ApiInfo.CAPTCHA_INVALID);
        }

        // 验证码不匹配
        if (!code.equals(userInputCaptcha)) {
            throw new CaptchaException(ApiInfo.CAPTCHA_NOT_MATCH);
        }
    }

}
