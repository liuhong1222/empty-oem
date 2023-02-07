package com.cc.oem.modules.agent.model.data;

import java.util.Date;

/**
 * @author chenzj
 * @since 2018/10/8
 */
public class NewsInfoData {

    /**
     * 新闻id
     */
    private String id;

    /**
     * 代理商名称
     */
    private String agentName;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 提交发布时间
     */
    private Date commitTime;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 审核状态码  审核状态 0-发布待审核  1-已审核 2-修改待审核  3-已驳回  -1删除
     */
    private Integer auditState;

    /**
     * 审核状态码名称
     */
    private String auditStateName;

    /**
     * 审核备注说明
     */
    private String auditRemark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public String getAuditStateName() {
        return auditStateName;
    }

    public void setAuditStateName(String auditStateName) {
        this.auditStateName = auditStateName;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }
}
