package com.nowander.account.domain.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author wtk
 * @since 2022-01-05
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<SysUser> {
    /**
     *
     * @param username
     * @return
     */
    Optional<SysUser> selectByUsername(@Param("username") String username);

    /**
     * 查询用户权限
     * @param id
     * @return
     */
    List<String> selectUserPermissions(@Param("id") Integer id);

    /**
     * 更新用户头像
     * @param avatar
     * @param id
     */
    void updateAvatarById(@Param("avatar") String avatar, @Param("id") Integer id);
}
