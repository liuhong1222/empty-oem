package com.cc.oem.modules.agent.model.param;

/**
 * Description:分页参数
 *
 * @author chenzj
 * @since 2018/5/21
 */
public class PageParam {

    /**
     * 当前页，从1开始
     */
    //@NotNull(message = "当前页不能为空")
    private Integer currentPage=1;

    /**
     * 页面大小
     */
    //@NotNull(message = "页面大小不能为空")
    private Integer pageSize=10;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
