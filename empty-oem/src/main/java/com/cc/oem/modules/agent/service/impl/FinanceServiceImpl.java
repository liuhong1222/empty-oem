package com.cc.oem.modules.agent.service.impl;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.dao.AgentRechargeMapper;
import com.cc.oem.modules.agent.dao.CustInfoMapper;
import com.cc.oem.modules.agent.dao.CustomerRechargeMapper;
import com.cc.oem.modules.agent.dao.finance.CustomerConsumeMapper;
import com.cc.oem.modules.agent.dao.finance.CustomerRefundMapper;
import com.cc.oem.modules.agent.entity.finance.CustomerRechargeVo;
import com.cc.oem.modules.agent.entity.finance.CustomerRefund;
import com.cc.oem.modules.agent.enums.AgentRechargePayTypeEnum;
import com.cc.oem.modules.agent.model.data.FinanceAgentRechargeData;
import com.cc.oem.modules.agent.model.data.FinanceUserRechargeData;
import com.cc.oem.modules.agent.model.data.PageInfoWithTotalInfo;
import com.cc.oem.modules.agent.model.data.finance.QueryCustConsumeData;
import com.cc.oem.modules.agent.model.data.finance.QueryCustRefundData;
import com.cc.oem.modules.agent.model.data.records.AgentRechargeData;
import com.cc.oem.modules.agent.model.param.FinanceRechargeParam;
import com.cc.oem.modules.agent.service.FinanceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenzj
 * @since 2018/8/13
 */
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    AgentRechargeMapper agentRechargeMapper;

    @Autowired
    CustomerRechargeMapper customerRechargeMapper;
    @Autowired
    CustomerRefundMapper customerRefundMapper;

    @Autowired
    CustomerConsumeMapper customerConsumeMapper;
    @Autowired
    CustInfoMapper custInfoMapper;

    /**
     * 组装合计信息
     * @param param
     */
    private Map generateTotalMap(FinanceRechargeParam param){
        Map<String, Object> totalMap = new HashMap<>();
        //总体条数
        Long number = 0L;
        //总金额
        BigDecimal money = BigDecimal.ZERO;
        AgentRechargeData totalRechargeInfo = agentRechargeMapper.getTotalRechargeInfo(param);
        totalMap.put("number", totalRechargeInfo.getRechargeNumber());
        totalMap.put("money", totalRechargeInfo.getPaymentAmount());

        return totalMap;
    }

    /**
     * 查询代理商充值记录列表
     */
    @Override
    public PageInfo agentRechargeList(FinanceRechargeParam param) {
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        List<FinanceAgentRechargeData> list = agentRechargeMapper.queryFinanceAgentRechargeDataList(param);
        PageInfoWithTotalInfo<FinanceAgentRechargeData> pageInfo = new PageInfoWithTotalInfo<>(list);
        //组装入账方式
        pageInfo.getList().forEach(e->e.setPayTypeName(AgentRechargePayTypeEnum.getDescri(e.getPayType())));
        //组装合计信息
        if(list.size()<1){
            return pageInfo;
        }
        pageInfo.setTotalInfo(generateTotalMap(param));
        return pageInfo;
    }

    /**
     * 查询客户充值记录列表
     */
    @Override
    public PageInfo custRechargeList(FinanceRechargeParam param) {
        if(StringUtils.isNotEmpty(param.getUserName())){
            List<Long> custList = custInfoMapper.selectCustIdListByCustName(param.getAgentId(),param.getUserName());
            if(custList.size()<1){
                return new PageInfo<>(new ArrayList<>());
            }
            param.setCustIdList(custList);
        }
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        List<FinanceUserRechargeData> list = customerRechargeMapper.queryUserRechargeListExcludeGiftAmount(param);
        PageInfoWithTotalInfo<FinanceUserRechargeData> pageInfo = new PageInfoWithTotalInfo<>(list);
        //组装入账方式
        if(list.size()<1){
            return pageInfo;
        }
        CustomerRechargeVo customerRecharge = customerRechargeMapper.queryTotalRechargeInfo(param);
        //组装合计信息
        Map totalInfo = new HashMap();
        totalInfo.put("totalRechargeMoney",customerRecharge.getPaymentAmount().setScale(3, RoundingMode.HALF_UP));
        totalInfo.put("totalRechargeNum",customerRecharge.getRechargeNumber());
        pageInfo.setTotalInfo(totalInfo);
        return pageInfo;
    }

    /**
     * 查询客户退款记录列表
     */
    @Override
    public PageInfo custRefundList(FinanceRechargeParam param) {
        if(StringUtils.isNotEmpty(param.getUserName())){
            List<Long> custList = custInfoMapper.selectCustIdListByCustName(param.getAgentId(),param.getUserName());
            if(custList.size()<1){
                return new PageInfo<>(new ArrayList<>());
            }
            param.setCustIdList(custList);
        }
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        List<QueryCustRefundData> list = customerRefundMapper.queryCustRefundList(param);
        PageInfoWithTotalInfo<QueryCustRefundData> pageInfo = new PageInfoWithTotalInfo<>(list);
        if(list.size()<1){
            return pageInfo;
        }
        CustomerRefund refund = customerRefundMapper.queryTotalRechargeInfo(param);
        //组装合计信息
        Map totalInfo = new HashMap();
        totalInfo.put("totalRefundMoney",new BigDecimal(refund.getRefundAmount()).setScale(3, RoundingMode.HALF_UP));
        totalInfo.put("totalRefundNum",refund.getRefundNumber());
        pageInfo.setTotalInfo(totalInfo);
        return pageInfo;
    }


    /**
     * 查询客户消耗记录列表
     */
    @Override
    public PageInfo custConsumeList(FinanceRechargeParam param) {
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        List<QueryCustConsumeData> list = customerConsumeMapper.queryCustConsumeList(param);
        PageInfoWithTotalInfo<QueryCustConsumeData> pageInfo = new PageInfoWithTotalInfo<>(list);
        if(list.size()<1){
            return pageInfo;
        }
        //组装合计信息
        Long consume = customerConsumeMapper.queryTotalRechargeInfo(param.getAgentId());
        //组装合计信息
        Map totalInfo = new HashMap();
        totalInfo.put("totalConsumeNum",consume);
        pageInfo.setTotalInfo(totalInfo);
        return pageInfo;
    }


}
