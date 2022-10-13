package com.nowander.common.security.service.auth.server;

import com.nowander.common.security.service.auth.Credential;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServerCredential extends Credential {

    UUID serverId;

    String serverName;

    /**
     * 可访问的服务
     */
    Set<UUID> accessibleServiceIds;
}
