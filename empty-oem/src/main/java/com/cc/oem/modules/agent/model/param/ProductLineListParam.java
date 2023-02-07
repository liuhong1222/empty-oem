package com.cc.oem.modules.agent.model.param;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * author wade
 */
@Data
public class ProductLineListParam extends TimesParam {
    /**
     * 代理商id
     */
    private Long agentId;
    /**
     * -1全部 '0上架，1下架
     */
    private Integer state;

    /**
     * -1全部 0创建待审核  1已通过 2创建驳回 3修改待审核 4修改驳回 5 待审核 6 驳回
     */
    private Integer applyState;

    private Long productLineId;

}
