package com.cc.oem.modules.agent.service.records.impl;

import com.cc.oem.common.utils.DateUtils;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.dao.*;
import com.cc.oem.modules.agent.entity.*;
import com.cc.oem.modules.agent.model.data.userManage.CustomerExtQueryData;
import com.cc.oem.modules.agent.model.param.AuthInfoParam;
import com.cc.oem.modules.agent.model.param.CustInfoParam;
import com.cc.oem.modules.agent.model.param.userManage.CustextAuditParam;
import com.cc.oem.modules.agent.model.param.userManage.CustomerVerifyQueryParam;
import com.cc.oem.modules.agent.service.CustInfoService;
import com.cc.oem.modules.agent.service.records.CustExtService;
import com.cc.oem.modules.sys.entity.SysUserEntity;
import com.cc.oem.modules.sys.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenzj
 * @since 2018/5/23
 */

@Service
public class CustExtServiceImpl implements CustExtService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CustInfoMapper custInfoMapper;

    /**
     * 审核客户提交的认证申请
     * @param param
     * @return
     */
    public R auditCustExtVerify(CustextAuditParam param){
        int result = custInfoMapper.auditCustExtVerify(param);
        if(result!=1){
            return R.error();
        }
        return R.ok();
    }

    /**
     * 认证信息分页展示
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public R getCustomerExtPageList(CustomerVerifyQueryParam param) throws Exception {
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        List<CustomerExtQueryData> list = custInfoMapper.queryAuditingCustExtList(param);
        PageInfo<CustomerExtQueryData> info = new PageInfo<>(list);
        return R.ok(info);
    }

    /**
     * 认证信息待审核列表
     *
     */
    public R queryAuditingCustExtList(CustomerVerifyQueryParam param) throws Exception {
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        param.setState(0);
        List<CustomerExtQueryData> list = custInfoMapper.queryAuditingCustExtList(param);
        PageInfo<CustomerExtQueryData> info = new PageInfo<>(list);
        return R.ok(info);
    }



}
