package com.nowander.common.core.feign;

import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

/**
 * @author wtk
 * @date 2022-09-02
 */
public class FeignConfiguration {

    @Bean
    public Decoder feignDecoder() {
        return new FeignResultDecoder();
    }

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new FeignResultErrorDecoder(new ErrorDecoder.Default());
    }
}
