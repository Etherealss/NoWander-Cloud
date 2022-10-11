package com.nowander.favor.infrastructure.utils;

import com.nowander.favor.domain.favor.count.FavorCountEntity;
import com.nowander.favor.domain.favor.record.FavorRecordEntity;
import com.nowander.favor.domain.favor.record.FavorRecordVO;

/**
 * @author wtk
 * @date 2022-10-11
 */
public class FavorKeyBuilder {
    public static String buildBufferKey(FavorRecordVO favorRecord) {
        return favorRecord.getTargetType().getCode() + ":"
                + favorRecord.getTargetId() + ":"
                + favorRecord.getUserId();
    }

    public static String buildCacheKey(FavorRecordVO favorRecord, String keyPrefix) {
        return keyPrefix + ":"
                + favorRecord.getTargetType().getCode() + ":"
                + favorRecord.getTargetId() + ":"
                + favorRecord.getUserId();
    }

    public static String buildBufferKey(FavorRecordEntity favorRecord) {
        return favorRecord.getTargetType().getCode() + ":"
                + favorRecord.getTargetId() + ":"
                + favorRecord.getUserId();
    }

    public static String buildCacheKey(FavorRecordEntity favorRecord, String keyPrefix) {
        return keyPrefix + ":"
                + favorRecord.getTargetType().getCode() + ":"
                + favorRecord.getTargetId() + ":"
                + favorRecord.getUserId();
    }

    public static String buildBufferKey(FavorCountEntity favorCount) {
        return favorCount.getTargetType().getCode() + ":"
                + favorCount.getTargetId();
    }

    public static String buildCacheKey(FavorCountEntity favorCount, String keyPrefix) {
        return keyPrefix + ":"
                + favorCount.getTargetType().getCode() + ":"
                + favorCount.getTargetId();
    }
}
