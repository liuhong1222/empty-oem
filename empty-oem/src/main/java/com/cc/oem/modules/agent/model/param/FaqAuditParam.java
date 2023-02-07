package com.cc.oem.modules.agent.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 常见问题审核参数
 *
 */
@Data
public class FaqAuditParam {
    /**
     * 常见问题id
     */
    @NotNull(message = "常见问题id不能为空")
    private Long productFaqId;

    /**
     * 审核状态  1-已审核 2-新增驳回  4-修改驳回
     */
    @NotNull(message = "审核状态不能为空")
    private Integer auditState;

    /**
     * 审核备注信息
     */
    private String auditRemark;


}
