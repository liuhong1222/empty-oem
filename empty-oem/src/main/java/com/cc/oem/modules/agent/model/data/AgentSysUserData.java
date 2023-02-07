package com.cc.oem.modules.agent.model.data;

import com.cc.oem.common.annotation.ExcelField;

import java.util.Date;

/**
 * 代理商账号列表信息
 *
 * @author chenzj
 * @since 2018/8/17
 */
public class AgentSysUserData {
    /**
     * 账号id
     */
    @ExcelField(value = "用户编号", order = 1)
    private Long userId;

    /**
     * 用户姓名
     */
    @ExcelField(value = "用户名称", order = 2)
    private String realName;

    /**
     * 账号类型标识  0-主账号   1-副账号
     */
    private Integer flag;

    /**
     * 手机号
     */
    @ExcelField(value = "手机号", order = 3)
    private String mobile;

    /**
     * 邮箱
     */
    @ExcelField(value = "邮箱", order = 4)
    private String email;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 状态名称
     */
    @ExcelField(value = "状态", order = 5)
    private String statusName;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
