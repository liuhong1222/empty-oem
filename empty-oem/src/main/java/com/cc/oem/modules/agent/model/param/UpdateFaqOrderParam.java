package com.cc.oem.modules.agent.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 修改常见问题排序查询参数
 *
 * @author liuh
 * @since 2019/03/25
 */
@Data
public class UpdateFaqOrderParam {
	
    /**
     * 产品id
     */
    @NotNull(message = "问题id不能为空")
    private Long productFaqId;

    /**
     * 排序
     */
    private Integer orderNum;
    
    /**
     *状态 0-上架 1-下架
     */
    private Integer shelfStatus;
    
}
