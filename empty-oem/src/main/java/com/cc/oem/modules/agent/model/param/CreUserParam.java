package com.cc.oem.modules.agent.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Description:
 * User: liutao
 * Date: 2018-05-24
 * Time: 14:12
 */
@ApiModel("客户列表查询参数")
public class CreUserParam {

    @ApiModelProperty("用户类型 0-个人  1-企业")
    private Integer userType;

    @ApiModelProperty("代理商名称")
    private String companyName;

    @ApiModelProperty("客户名称，营业执照或者身份证上的姓名")
    private String userName;

    @ApiModelProperty("用户手机号")
    private String mobile;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
