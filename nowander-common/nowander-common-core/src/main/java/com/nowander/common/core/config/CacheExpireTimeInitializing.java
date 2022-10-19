package com.nowander.common.core.config;

import cn.hutool.core.util.ArrayUtil;
import com.nowander.common.core.cache.TemporaryCacheable;
import com.nowander.common.core.utils.JsonUtil;
import com.nowander.common.core.utils.SpELParserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Map;

/**
 * 根据 TemporaryCacheable 设置过期时间
 * https://juejin.cn/post/7153064200359641096#heading-4
 * @author wtk
 * @date 2022-10-19
 */
@Slf4j
@Component
public class CacheExpireTimeInitializing implements SmartInitializingSingleton, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;
    private RedisCacheManager cacheManager;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
        this.cacheManager = beanFactory.getBean(RedisCacheManager.class);
    }

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Object> beans = beanFactory.getBeansWithAnnotation(Component.class);
        if (CollectionUtils.isEmpty(beans)) {
            return;
        }
        for (Object cacheValue : beans.values()) {
            ReflectionUtils.doWithMethods(cacheValue.getClass(), method -> {
                ReflectionUtils.makeAccessible(method);
                if (method.isAnnotationPresent(TemporaryCacheable.class)) {
                    TemporaryCacheable cacheable = method.getAnnotation(TemporaryCacheable.class);
                    this.initExpireTime(cacheable, method);
                }
            });
        }
        cacheManager.initializeCaches();
        if (log.isInfoEnabled()) {
            String configs = JsonUtil.toJsonString(cacheManager.getCacheConfigurations());
            log.info("设置Redis过期时间完毕：{}", configs);
        }
    }

    public void initExpireTime(TemporaryCacheable cacheable, Method method) {
        String[] cacheNames = getCacheNames(cacheable);
        if (cacheNames.length == 0) {
            return;
        }
        Map<String, RedisCacheConfiguration> configurations = cacheManager.getCacheConfigurations();
        for (String cacheName : cacheNames) {
            String expression = cacheable.expiredMsExpression();
            long expiredMs;
            if (StringUtils.hasText(expression)) {
                SpELParserUtils.parse(method, )
            } else {
                expiredMs = cacheable.expiredMs();
            }
            RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                    .defaultCacheConfig()
                    .entryTtl(Duration.ofMillis(expiredMs));
            configurations.put(cacheName, redisCacheConfiguration);
        }
    }

    public static String[] getCacheNames(TemporaryCacheable cacheable) {
        String[] cacheNames = cacheable.cacheNames();
        if (ArrayUtil.isEmpty(cacheNames)) {
            cacheNames = cacheable.value();
        }
        return cacheNames;
    }
}
