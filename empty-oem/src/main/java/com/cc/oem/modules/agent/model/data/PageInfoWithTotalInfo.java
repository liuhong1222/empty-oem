package com.cc.oem.modules.agent.model.data;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author chenzj
 * @since 2018/9/6
 */
public class PageInfoWithTotalInfo<T> extends PageInfo<T> {
    /**
     * 合计数据
     */
    private Object totalInfo;

    public Object getTotalInfo() {
        return totalInfo;
    }

    public void setTotalInfo(Object totalInfo) {
        this.totalInfo = totalInfo;
    }

    public PageInfoWithTotalInfo(List<T> list) {
        super(list, 8);
    }

}
