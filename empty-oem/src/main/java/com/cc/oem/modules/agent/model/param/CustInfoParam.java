package com.cc.oem.modules.agent.model.param;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * author zhangx
 */
@Data
public class CustInfoParam extends PageParam implements Serializable {

    private static final long serialVersionUID = 4033496595333595777L;
    private String phone;
    private String startTimeStr;
    private String endTimeStr;
    //客户名称
    private String name;
    /**-1,全部,0-个人  1-企业,2其他*/
    private String customerType;
    private Long sysUserId;
    private Long agentId;

    /**1管理员，2代理商后台添加*/
    private Integer adminType;
    private Integer haveRecharged;
    private String ip;
    private String email;

    private List<Long> agentIdList;
    private List<Long> custIdList;

    private String agentName;

    private Integer officialWeb;

}
