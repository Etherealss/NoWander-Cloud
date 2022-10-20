package com.nowander.common.security.service.auth.server;

import com.nowander.common.security.service.auth.Credential;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

/**
 * @author wtk
 * @date 2022-08-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServerCredential extends Credential {

    Integer serverId;

    String serverName;

    /**
     * 允许访问 serverId 的服务id列表
     * accessibleServerId ---√--> serverId
     * accessibleServerId <--x--- serverId
     */
    Set<Integer> accessibleServiceIds;
}
