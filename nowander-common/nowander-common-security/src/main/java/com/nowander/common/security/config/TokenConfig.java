package com.nowander.common.security.config;

/**
 * @author wtk
 * @date 2022-08-30
 */
public class TokenConfig {
    public static final int EXPIRE_MS_TOKEN = 60 * 60 * 24 * 1000;
    public static final int EXPIRE_MS_REFRESH_TOKEN = 60 * 60 * 24 * 7 * 1000;
    public static final String REDIS_PREFIX_TOKEN = "nowander:security:token:str:";
    public static final String REDIS_PREFIX_REFRESH_TOKEN = "nowander:security:refresh-token:str:";
    public static final String HEADER_TOKEN = "Authorization";
}
