package com.cc.oem.modules.agent.dao;


import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.AgentAccount;
import com.cc.oem.modules.agent.entity.CustomerBalance;
import com.cc.oem.modules.agent.entity.finance.CustomerRecharge;
import com.cc.oem.modules.agent.entity.finance.CustomerRechargeVo;
import com.cc.oem.modules.agent.model.data.FinanceUserRechargeData;
import com.cc.oem.modules.agent.model.data.customer.CustomerGivenRechargeNumInfo;
import com.cc.oem.modules.agent.model.param.FinanceRechargeParam;
import com.cc.oem.modules.agent.model.param.RechargeDetailParam;
import com.cc.oem.modules.job.entity.task.CustDailyRechargeConsumeInfo;
import com.cc.oem.modules.job.entity.task.StatisticsDate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface CustomerRechargeMapper extends BaseOemMapper<CustomerRecharge, Integer> {

    CustomerRechargeVo queryTotalRechargeInfo(FinanceRechargeParam param);

    /**
     * 获取今日入账金额
     */
    BigDecimal sumMoney(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<Map> selectRechargeList(RechargeDetailParam param);

    List<Map> getPackageInfo(@Param("creUserId") Long creUserId);

    List<Map> getAgentAmountByCreUserId(@Param("creUserId") Long creUserId);


    /**
     * 客户充值记录，不包括赠送金额
     */
    List<FinanceUserRechargeData> queryUserRechargeListExcludeGiftAmount(FinanceRechargeParam param);

    /**获取赠送条数*/
    List<CustomerGivenRechargeNumInfo> getGiveAmountSumByCreUserId(@Param("customerId") Long customerId);


    /**
     * 赠送次数
     * @param custId
     * @return
     */
    int countRegisterSend(@Param("custId")Long custId);

    List<CustDailyRechargeConsumeInfo> countIntervalDailyReport(StatisticsDate statisticsDate);


    int save(CustomerRecharge order);

    List<CustomerBalance> countTotalRechargeOfCust(@Param("startTime") String startTime,@Param("endTime") String endTime);


    List<AgentAccount> countTotalRechargeOfAgent(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<Long> countHaveRegisterSend(@Param("list") List<Long> custIdList);
}
