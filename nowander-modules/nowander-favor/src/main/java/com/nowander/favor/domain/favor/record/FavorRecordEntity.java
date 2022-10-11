package com.nowander.favor.domain.favor.record;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nowander.common.database.pojo.entity.BaseEntity;
import com.nowander.favor.infrastructure.enums.FavorTargetType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("like_record")
@NoArgsConstructor
public class FavorRecordEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 目标类型
     */
    private FavorTargetType targetType;
    /**
     * 点赞的目标id
     */
    private Integer targetId;
    /**
     * 点赞用户id
     */
    private Integer userId;
}
