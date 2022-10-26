package com.nowander.common.database.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wtk
 * @date 2022-02-21
 */
@Data
public abstract class BaseEntity implements Serializable {
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GTM+8", shape = JsonFormat.Shape.NUMBER)
    @CreatedDate
    protected Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GTM+8", shape = JsonFormat.Shape.NUMBER)
    @LastModifiedDate
    protected Date updateTime;
}
