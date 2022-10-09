package com.nowander.favor.domain.favor.record;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Mapper
@Repository
public interface FavorRecordMapper extends BaseMapper<FavorRecordEntity> {

    int countFavorRecord(@Param("favorRecord") FavorRecordEntity favorRecord);

    void delete(@Param("favorRecord") FavorRecordEntity favorRecord);

}
