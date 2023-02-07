package com.cc.oem.modules.agent.model.param;

import lombok.Data;

import java.util.List;

/**
 * 代理商查询参数
 */
@Data
public class AgentInfoParam extends TimesParam {
    /**
     * 代理商id
     */
    private Long agentId;

    /**
     * 代理商名称
     */
    private String companyName;

    /**
     * 代理商状态  0-删除  1-正常
     */
    private Integer state;

    /**
     * 手机号
     */
    private String linkmanPhone;

    private Integer officialWeb;

    private List<Long> agentIdList;
}
