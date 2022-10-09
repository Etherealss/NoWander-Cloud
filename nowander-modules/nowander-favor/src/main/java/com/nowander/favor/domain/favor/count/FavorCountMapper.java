package com.nowander.favor.domain.favor.count;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @since 2022-01-28
 */
@Mapper
@Repository
public interface FavorCountMapper extends BaseMapper<FavorCountEntity> {

    /**
     * 获取点赞数，用于更新
     * @param favorCount
     */
    Integer getFavorCount(@Param("favorCount") FavorCountEntity favorCount);

    /**
     * 获取点赞数，用于更新
     * @param favorCount
     */
    Integer getFavorCountForUpdate(@Param("favorCount") FavorCountEntity favorCount);

    /**
     * 更新点赞数
     * @param favorCount
     */
    void updateFavorCount(@Param("favorCount") FavorCountEntity favorCount);
}
