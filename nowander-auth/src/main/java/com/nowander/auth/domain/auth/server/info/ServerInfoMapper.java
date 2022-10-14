package com.nowander.auth.domain.auth.server.info;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Mapper
@Repository
public interface ServerInfoMapper extends BaseMapper<ServerInfoEntity> {
}
