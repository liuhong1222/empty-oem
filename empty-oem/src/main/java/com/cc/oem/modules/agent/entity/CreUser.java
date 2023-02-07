package com.cc.oem.modules.agent.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

@TableName("cre_user")
public class CreUser {
    private Integer id;

    private Integer clAccountId;

    private Integer companyId;

    private String userName;

    private String userPhone;

    private String userPassword;

    private String userEmail;

    private String userQq;

    private Integer userDepartment;

    private Integer userPosition;

    private String userThumbs;

    private String wxOpenid;

    private Date lastLoginTime;

    private String lastLoginIp;

    private Integer deleteStatus;

    private Integer version;

    private Integer createUser;

    private Date createTime;

    private Date updateTime;

    private Integer agentId;

    private String nickName;

    private Integer bitOnoff;

    private Integer userType;

    private Integer sourceType;

    private Integer salesmanId;

    private String salesman;

    private Integer isAuth;

    private Integer isFakeAuth;

    private String authMemo;

    private Integer isCarrersAuth;

    private Integer authId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClAccountId() {
        return clAccountId;
    }

    public void setClAccountId(Integer clAccountId) {
        this.clAccountId = clAccountId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserQq() {
        return userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq == null ? null : userQq.trim();
    }

    public Integer getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(Integer userDepartment) {
        this.userDepartment = userDepartment;
    }

    public Integer getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(Integer userPosition) {
        this.userPosition = userPosition;
    }

    public String getUserThumbs() {
        return userThumbs;
    }

    public void setUserThumbs(String userThumbs) {
        this.userThumbs = userThumbs == null ? null : userThumbs.trim();
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid == null ? null : wxOpenid.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Integer getBitOnoff() {
        return bitOnoff;
    }

    public void setBitOnoff(Integer bitOnoff) {
        this.bitOnoff = bitOnoff;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(Integer salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman == null ? null : salesman.trim();
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    public Integer getIsFakeAuth() {
        return isFakeAuth;
    }

    public void setIsFakeAuth(Integer isFakeAuth) {
        this.isFakeAuth = isFakeAuth;
    }

    public String getAuthMemo() {
        return authMemo;
    }

    public void setAuthMemo(String authMemo) {
        this.authMemo = authMemo == null ? null : authMemo.trim();
    }

    public Integer getIsCarrersAuth() {
        return isCarrersAuth;
    }

    public void setIsCarrersAuth(Integer isCarrersAuth) {
        this.isCarrersAuth = isCarrersAuth;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }
}