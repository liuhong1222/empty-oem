package com.cc.oem.modules.agent.model.param.userManage;

import lombok.Data;

@Data
public class CustextAuditParam {

    private Long customerId;

    /**
     * 0，待审核，1：驳回，9已认证
     */
    private Integer state;
}
