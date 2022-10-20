package com.nowander.favor.domain.favor.record;

import com.nowander.common.core.enums.BaseEnum;
import com.nowander.common.core.exception.internal.BugException;
import com.nowander.favor.infrastructure.enums.FavorTargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wtk
 * @date 2022-10-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavorRecordVO {
    private FavorTargetType targetType;
    private Integer targetId;
    private Integer userId;
    /**
     * 点赞转为 true点赞 false取消
     */
    private Boolean state;

    public static FavorRecordVO buildByBufferKey(String bufferKey, boolean state) {
        FavorRecordVO vo = new FavorRecordVO();
        String[] split = bufferKey.split(":");
        if (split.length != 3) {
            throw new BugException("FavorRecordVO 构造失败！传入的参数不对！");
        }
        vo.targetType = BaseEnum.fromCode(FavorTargetType.class, Integer.parseInt(split[0]));
        vo.targetId = Integer.valueOf(split[1]);
        vo.userId = Integer.valueOf(split[2]);
        vo.state = state;
        return vo;
    }
}
