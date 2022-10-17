package com.nowander.auth.domain.auth.server.accessibility;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nowander.common.database.pojo.entity.IdentifiedEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TableName("server_accessibility")
public class ServerAccessibilityEntity extends IdentifiedEntity {
    /**
     * 服务id
     */
    private Integer serverId;
    /**
     * 允许访问 serverId 的服务id
     * accessibleServerId ---√--> serverId
     * accessibleServerId <--x--- serverId
     */
    private Integer accessibleServerId;
}
