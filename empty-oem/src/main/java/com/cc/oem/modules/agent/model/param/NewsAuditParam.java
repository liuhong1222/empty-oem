package com.cc.oem.modules.agent.model.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新闻审核参数
 *
 * @author chenzj
 * @since 2018/10/7
 */
public class NewsAuditParam {
    /**
     * 新闻id
     */
    @NotBlank(message = "新闻id不能为空")
    private String newsId;

    /**
     * 审核状态 0-发布待审核  1-已审核 2-修改待审核  3-已驳回  -1删除
     */
    @NotNull(message = "审核状态不能为空")
    private Integer auditState;

    /**
     * 审核备注信息
     */
    private String auditRemark;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }
}
