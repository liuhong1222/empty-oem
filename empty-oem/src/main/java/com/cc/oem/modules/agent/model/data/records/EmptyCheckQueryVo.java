package com.cc.oem.modules.agent.model.data.records;

import com.cc.oem.modules.agent.entity.records.EmptyCheck;
import lombok.Data;

@Data
public class EmptyCheckQueryVo extends EmptyCheck {

    private String phone;

    private String customerName;
}
