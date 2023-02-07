package com.cc.oem.modules.agent.entity;

import java.util.Date;

public class BusinessLicenceInfo {
    private Integer id;

    private Integer creUserId;

    private String regnum;

    private String name;

    private String person;


    private String effectdate;


    private String expiredate;

    private String address;

    private String captial;

    private String elbem;

    private String title;

    private String stamp;

    private String regAuthority;

    private String qrcode;

    private String pictureUrl;

    private Integer flag;

    private Date createtime;

    private String createby;

    private Date lasttime;

    private String lastrepair;

    private String legalPersonId;

    private String business;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(Integer creUserId) {
        this.creUserId = creUserId;
    }

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum == null ? null : regnum.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person == null ? null : person.trim();
    }

    public String getEffectdate() {
        return effectdate;
    }

    public void setEffectdate(String effectdate) {
        this.effectdate = effectdate;
    }

    public String getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(String expiredate) {
        this.expiredate = expiredate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCaptial() {
        return captial;
    }

    public void setCaptial(String captial) {
        this.captial = captial == null ? null : captial.trim();
    }

    public String getElbem() {
        return elbem;
    }

    public void setElbem(String elbem) {
        this.elbem = elbem == null ? null : elbem.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp == null ? null : stamp.trim();
    }

    public String getRegAuthority() {
        return regAuthority;
    }

    public void setRegAuthority(String regAuthority) {
        this.regAuthority = regAuthority == null ? null : regAuthority.trim();
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode == null ? null : qrcode.trim();
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl == null ? null : pictureUrl.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby == null ? null : createby.trim();
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public String getLastrepair() {
        return lastrepair;
    }

    public void setLastrepair(String lastrepair) {
        this.lastrepair = lastrepair == null ? null : lastrepair.trim();
    }

    public String getLegalPersonId() {
        return legalPersonId;
    }

    public void setLegalPersonId(String legalPersonId) {
        this.legalPersonId = legalPersonId == null ? null : legalPersonId.trim();
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business == null ? null : business.trim();
    }
}