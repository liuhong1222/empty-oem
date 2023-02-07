package com.cc.oem.modules.sys.vo.data;

import com.cc.oem.modules.sys.entity.SysUserEntity;
import lombok.Data;

@Data
public class AgentAccountData extends SysUserEntity {

    private String companyName;
    private String linkman_name;

}
