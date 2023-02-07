package com.cc.oem.modules.agent.service;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.UpdateSysUserMobileParam;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName CreUserService
 * author   zhangx
 * date     2018/8/8 11:07
 * description
 */
public interface UpdateMobileService {

    R sendVerifyCode(String mobile);

    R updateMobile(UpdateSysUserMobileParam param);

    R checkMobile(String mobile);
}
