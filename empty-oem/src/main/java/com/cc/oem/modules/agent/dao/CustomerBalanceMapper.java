package com.cc.oem.modules.agent.dao;


import com.cc.oem.modules.agent.entity.CustomerBalance;
import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.model.data.customer.CustomerBalanceInfo;
import com.cc.oem.modules.job.entity.task.AgentDailyInfo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Mapper
public interface CustomerBalanceMapper extends BaseOemMapper<CustomerBalance, Long> {

    /**
     * 获取空号文件检查剩余条数
     */
    BigDecimal sumEmptyFileBalance();

    CustomerBalance selectNormalByCreUserId(Long userId);

    CustomerBalanceInfo selectAccountInfoByCustId(Long custId);

    int updateCustAccountByCustId(CustomerBalance modifyEntity);

    List<AgentDailyInfo> getAgentTotalCounts();

    List<CustomerBalance> countHistoryData();

    int batchSave(@Param("list") List<CustomerBalance> batchSaveList);

    int addRechargeInfo(CustomerBalance update);

    int saveOne(CustomerBalance customerBalance);
}
