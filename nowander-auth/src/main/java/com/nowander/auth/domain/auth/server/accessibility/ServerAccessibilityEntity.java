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
    private Integer serverId;
    private Integer accessibleServerId;
}
