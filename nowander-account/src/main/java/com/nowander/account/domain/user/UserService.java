package com.nowander.account.domain.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserService extends ServiceImpl<UserMapper, SysUser> {

    private final UserMapper userMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    public UserBriefDTO getBriefById(Integer userId) {
        SysUser user = userMapper.selectById(userId);
        return user.convert(UserBriefDTO.class);
    }

    public List<UserBriefDTO> getBatchBriefsByIds(Collection<Integer> userIds) {
        List<SysUser> users = userMapper.selectBatchIds(userIds);
        return users.stream()
                .map(user -> user.convert(UserBriefDTO.class))
                .collect(Collectors.toList());
    }

    public UserDetailDTO getDetailById(Integer userId) {
        SysUser user = userMapper.selectById(userId);
        return user.convert(UserDetailDTO.class);
    }
}
