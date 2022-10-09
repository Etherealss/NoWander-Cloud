package com.nowander.favor.domain.favor.record;

import com.nowander.common.core.pojo.Converter;
import com.nowander.favor.infrastructure.enums.FavorTargetType;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2022-10-09
 */
@Data
public class FavorRecordCommand implements Converter<FavorRecordEntity> {
    @NotNull
    private FavorTargetType targetType;
    @NotNull
    private Integer targetId;
    @NotNull
    private Boolean isLike;
}
