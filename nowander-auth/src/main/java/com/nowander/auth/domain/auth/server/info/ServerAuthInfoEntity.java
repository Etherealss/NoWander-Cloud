package com.nowander.auth.domain.auth.server.info;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nowander.common.database.pojo.entity.IdentifiedEntity;
import lombok.*;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@TableName("server_info")
public class ServerAuthInfoEntity extends IdentifiedEntity {
    String serverName;
    String secret;
}
