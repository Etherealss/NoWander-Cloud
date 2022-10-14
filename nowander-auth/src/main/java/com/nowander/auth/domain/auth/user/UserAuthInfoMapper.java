package com.nowander.auth.domain.auth.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Repository
@Mapper
public interface UserAuthInfoMapper extends BaseMapper<UserAuthInfoEntity> {

}
