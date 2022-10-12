package com.nowander.favor.domain.favor.count;

import com.nowander.common.core.enums.BaseEnum;
import com.nowander.favor.domain.favor.record.FavorRecordCommand;
import com.nowander.favor.infrastructure.enums.FavorTargetType;
import lombok.Data;

/**
 * @author wtk
 * @date 2022-10-09
 */
@Data
public class FavorCountVO {
    private FavorTargetType targetType;
    private Integer targetId;
    private Integer count;

    public FavorCountVO(FavorTargetType targetType, Integer targetId) {
        this.targetType = targetType;
        this.targetId = targetId;
    }

    public FavorCountVO(String favorCountKey, int count) {
        String[] split = favorCountKey.split("::");
        this.targetType = BaseEnum.fromCode(FavorTargetType.class, Integer.parseInt(split[1]));
        this.targetId = Integer.valueOf(split[2]);
        this.count = count;
    }

    public FavorCountVO(FavorRecordCommand command) {
        this.targetId = command.getTargetId();
        this.targetType = command.getTargetType();
    }
}
