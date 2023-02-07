package com.cc.oem.modules.agent.model.data;


import com.cc.oem.common.annotation.ExcelField;
import lombok.Data;

/**
 * 客户转代理商记录返回数据
 */
@Data
public class UserAgentChangeData {

    @ExcelField(value = "手机号码", order = 1)
    private String phone;
    @ExcelField(value = "客户名称", order = 2)
    private String name;
    @ExcelField(value = "注册时间", order = 3)
    private String custCreateTime;
    @ExcelField(value = "转入代理商", order = 4)
    private String sourceAgentName;
    @ExcelField(value = "转出代理商", order = 5)
    private String destAgentName;
    @ExcelField(value = "操作时间", order = 6)
    private String createTime;
    @ExcelField(value = "备注", order = 7)
    private String remark;

}
