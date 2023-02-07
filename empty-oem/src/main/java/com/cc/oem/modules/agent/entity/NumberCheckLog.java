package com.cc.oem.modules.agent.entity;

import java.util.Date;

/**
 * 空号检查消费记录
 */
public class NumberCheckLog {
    private String id;

    private String consumptionnum;

    private Integer userid;

    private Integer type;

    private Integer count;

    private String menu;

    private Integer status;

    private Integer dayint;

    private Date createtime;

    private Date updatetime;

    private String source;

    private String creUserName;

    private String creUserPhone;

    private Integer creUserType;

    private Long agentId;

    private String agentCompanyName;

    private String agentMobile;

    private Integer updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getConsumptionnum() {
        return consumptionnum;
    }

    public void setConsumptionnum(String consumptionnum) {
        this.consumptionnum = consumptionnum == null ? null : consumptionnum.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu == null ? null : menu.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDayint() {
        return dayint;
    }

    public void setDayint(Integer dayint) {
        this.dayint = dayint;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getCreUserName() {
        return creUserName;
    }

    public void setCreUserName(String creUserName) {
        this.creUserName = creUserName == null ? null : creUserName.trim();
    }

    public String getCreUserPhone() {
        return creUserPhone;
    }

    public void setCreUserPhone(String creUserPhone) {
        this.creUserPhone = creUserPhone == null ? null : creUserPhone.trim();
    }

    public Integer getCreUserType() {
        return creUserType;
    }

    public void setCreUserType(Integer creUserType) {
        this.creUserType = creUserType;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getAgentCompanyName() {
        return agentCompanyName;
    }

    public void setAgentCompanyName(String agentCompanyName) {
        this.agentCompanyName = agentCompanyName == null ? null : agentCompanyName.trim();
    }

    public String getAgentMobile() {
        return agentMobile;
    }

    public void setAgentMobile(String agentMobile) {
        this.agentMobile = agentMobile == null ? null : agentMobile.trim();
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }
}
