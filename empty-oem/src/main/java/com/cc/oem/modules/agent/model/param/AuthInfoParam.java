package com.cc.oem.modules.agent.model.param;

import lombok.Data;

import java.io.Serializable;

/**
  * author zhangx
  * date  2018/5/21 17:01
 */
@Data
public class AuthInfoParam extends TimesParam implements Serializable {

    private static final long serialVersionUID = 4033496595333595777L;
    private String phone;
    private Integer state;


    private String companyName;

    private Long agentId;

}
