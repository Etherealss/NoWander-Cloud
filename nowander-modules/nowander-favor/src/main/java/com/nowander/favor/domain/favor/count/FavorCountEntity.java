package com.nowander.favor.domain.favor.count;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nowander.common.database.pojo.entity.BaseEntity;
import com.nowander.favor.infrastructure.enums.FavorTargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * @author wtk
 * @date 2022-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("like_count")
@NoArgsConstructor
@AllArgsConstructor
public class FavorCountEntity extends BaseEntity {
    private FavorTargetType targetType;
    private Integer targetId;
    private Integer count;

    /**
     * @param add null值会被视为0
     */
    public void addCount(@Nullable Integer add) {
        int a = add == null ? 0 : add;
        if (count == null) {
            count = a;
        } else {
            count += a;
        }
    }

    public FavorCountEntity(FavorTargetType targetType, Integer targetId) {
        this.targetType = targetType;
        this.targetId = targetId;
    }
}
