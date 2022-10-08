package com.nowander.account.infrasturcture.config;

/**
 * @author wtk
 * @date 2022-08-30
 */
public class RedisTokenCacheConfig {
    public static final int EXPIRE_MS_TOKEN = 60 * 60 * 24 * 1000;
    public static final int EXPIRE_MS_REFRESH_TOKEN = 60 * 60 * 24 * 7 * 1000;
    public static final String REDIS_PREFIX_TOKEN = "nowander:account:token:str:";
    public static final String REDIS_PREFIX_REFRESH_TOKEN = "nowander:account:refresh-token:str:";
}
