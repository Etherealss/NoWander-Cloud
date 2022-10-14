package com.nowander.auth.domain.auth.server.accessibility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ServerAccessibilityService {
    private final ServerAccessibilityMapper accessibilityMapper;

    public Set<Integer> selectAccessibleServerIds(Integer serverId) {
        return accessibilityMapper.selectAccessibleServerIds(serverId);
    }
}
