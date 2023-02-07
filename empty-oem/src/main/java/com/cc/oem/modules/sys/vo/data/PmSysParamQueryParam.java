package com.cc.oem.modules.sys.vo.data;

import com.cc.oem.modules.agent.model.param.TimesParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 参数管理列表查询参数
 * @author xiaoybb
 * @date 2021-03-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmSysParamQueryParam extends TimesParam {
    /**
     * 参数名
     */
    private String paramKey;

}
