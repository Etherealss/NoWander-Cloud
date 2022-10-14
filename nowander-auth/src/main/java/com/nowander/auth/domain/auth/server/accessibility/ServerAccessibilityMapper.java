package com.nowander.auth.domain.auth.server.accessibility;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nowander.auth.domain.auth.server.info.ServerAuthInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Mapper
@Repository
public interface ServerAccessibilityMapper extends BaseMapper<ServerAuthInfoEntity> {

    Set<Integer> selectAccessibleServerIds(@Param("serviceId") Integer serviceId);
}
