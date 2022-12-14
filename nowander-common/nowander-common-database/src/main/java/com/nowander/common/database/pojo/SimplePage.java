package com.nowander.common.database.pojo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

/**
 * 用于简化 IPage 接口返回给前端的数据
 * @author wtk
 * @date 2022-08-14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplePage<T> {

    /**
     * 查询数据列表
     */
    List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    long total;

    /**
     * 每页显示条数，默认 10
     */
    long size;

    /**
     * 当前页
     */
    long current;

    public SimplePage() {
    }

    public SimplePage(IPage<T> iPage) {
        this(iPage.getRecords(), iPage.getTotal(), iPage.getSize(), iPage.getCurrent());
    }

    public SimplePage(List<T> records, long total, long size, long current) {
        this.records = records;
        this.total = total;
        this.size = size;
        this.current = current;
    }
}
