package com.cc.oem.modules.agent.service.records.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.constants.AgentConstant;
import com.cc.oem.modules.agent.dao.*;
import com.cc.oem.modules.agent.entity.Agent;
import com.cc.oem.modules.agent.entity.CustInfo;
import com.cc.oem.modules.agent.entity.agentSettings.Goods;
import com.cc.oem.modules.agent.entity.records.CustomerVerifyQueryVo;
import com.cc.oem.modules.agent.entity.records.MailVo;
import com.cc.oem.modules.agent.model.data.PageInfoWithTotalInfo;
import com.cc.oem.modules.agent.model.data.customer.CustomerBalanceInfo;
import com.cc.oem.modules.agent.model.data.records.CustomerExtQueryVo;
import com.cc.oem.modules.agent.model.data.userManage.CustomerExtQueryData;
import com.cc.oem.modules.agent.model.param.AuthInfoParam;
import com.cc.oem.modules.agent.model.param.CustRechargeParam;
import com.cc.oem.modules.agent.model.param.records.QueryAgentDailyParam;
import com.cc.oem.modules.agent.model.param.userManage.CustomerVerifyQueryParam;
import com.cc.oem.modules.agent.service.CustOrderService;
import com.cc.oem.modules.agent.service.RefreshCacheService;
import com.cc.oem.modules.agent.service.records.AgentDailyStasticService;
import com.cc.oem.modules.job.dao.AgentDailyInfoMapper;
import com.cc.oem.modules.job.entity.task.AgentDailyInfo;
import com.cc.oem.modules.job.entity.task.StatisticsDate;
import com.cc.oem.modules.sys.dao.SysUserDao;
import com.cc.oem.modules.sys.vo.data.AgentAccountData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wade
 */

@Service
public class AuthServiceImpl{
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustInfoMapper custInfoMapper;
    @Autowired
    private CustOrderService custOrderService;
    @Autowired
    AuthInfoMapper authInfoMapper;
    @Autowired
    private RefreshCacheService refreshCacheService;
    @Autowired
    private MailServiceImpl mailService;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private CustomerBalanceMapper customerBalanceMapper;

    public R companyAuthList(AuthInfoParam param) {
        if (param.getAgentId() == null) {
            return R.error("代理商ID不能为空");
        }
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        List<CustomerVerifyQueryVo> list = authInfoMapper.getCustomerVerifyPageList(param);
        PageInfo<CustomerVerifyQueryVo> pageInfo = new PageInfo<>(list);
        return R.ok(pageInfo);
    }

    /**
     * 认证信息待审核列表
     *
     */
//    public R queryAuditingCustExtList(CustomerVerifyQueryParam param) throws Exception {
//        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
//        param.setState(0);
//        List<CustomerExtQueryData> list = authInfoMapper.queryAuditingCustExtList(param);
//        PageInfo<CustomerExtQueryData> info = new PageInfo<>(list);
//        return R.ok(info);
//    }

    public R getCustomerExtById(Long id) {
        CustomerExtQueryVo customerExtById = authInfoMapper.getCustomerExtById(id);
        return R.ok(customerExtById);
    }

    public R auditCustExt(Long id,Integer state,Long custId,Long agentId) {
        CustomerExtQueryVo customerExtById = authInfoMapper.getCustomerExtById(id);
        //审批状态，0：待审核，9：已认证，1：已驳回
        int i = authInfoMapper.auditCustomerExtState(custId, state);
        if(i!=1){
            return R.error("审核客户认证失败");
        }
        CustInfo custInfo = custInfoMapper.selectById(custId);
        refreshCacheService.reflushCustExt(custId,agentId,custInfo.getPhone());
        if(state==9){
            //判断客户认证等级,假如为等级2，则拥有认证赠送大礼包，系统赠送
            if(AgentConstant.AUTHLIME_LEVEL_2==custInfo.getAuthenticationLimitLevel()){
                CustomerBalanceInfo customerBalanceInfo = customerBalanceMapper.selectAccountInfoByCustId(custId);
                if(customerBalanceInfo.getEmptyRechargeNum()>0||customerBalanceInfo.getRealtimeRechargeNum()>0){
                    CustRechargeParam param = new CustRechargeParam();
                    param.setAmount(BigDecimal.ZERO);
                    param.setCategory(0);
                    param.setGoodsId(0l);
                    param.setNumber(5000);
                    param.setCustId(custId);
                    param.setPayType(10);
                    param.setPrice(BigDecimal.ZERO);
                    param.setRemark("认证有礼赠送");
                    Goods goods = new Goods();
                    goods.setName("认证有礼");
                    custOrderService.commonPresentRechargeOperation(goods,param,agentId);
                }
            }
            List<AgentAccountData> agentAccountList = sysUserDao.queryAgentAccountList(AgentConstant.AGENT_ROLE_ID.toString(), custInfo.getAgentId(), null);
            MailVo mailVo = new MailVo();
            List<String> emailCollect = agentAccountList.stream().map(AgentAccountData::getEmail).collect(Collectors.toList());
            mailVo.setTo(String.join(",",emailCollect));
            mailVo.setSubject("客户认证审核通过通知邮件");
            mailVo.setText(String.format("尊敬的客户，您号码为%s的用户已经通过认证审核",custInfo.getPhone() ));
            mailService.sendMail(mailVo);
        }

        // 驳回了则初始化客户状态
        if(state==1) {
        	i = authInfoMapper.auditCustomerBackState(custId);
        	if(i!=1){
                return R.error("驳回客户认证失败");
            }
        	
        	i = authInfoMapper.deleteOne(id);
        	if(i!=1){
                return R.error("驳回客户认证失败");
            }
        }

        return R.ok();
    }
}
