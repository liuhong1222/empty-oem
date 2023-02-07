package com.cc.oem.modules.agent.dao.finance;


import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.finance.CustomerRecharge;
import com.cc.oem.modules.agent.model.data.finance.QueryCustConsumeData;
import com.cc.oem.modules.agent.model.param.FinanceRechargeParam;
import com.cc.oem.modules.job.entity.task.CustDailyInfo;
import com.cc.oem.modules.job.entity.task.CustDailyRechargeConsumeInfo;
import com.cc.oem.modules.job.entity.task.StatisticsDate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomerConsumeMapper extends BaseOemMapper<CustomerRecharge, Integer> {

    /**
     * 客户退款记录
     */
    List<QueryCustConsumeData> queryCustConsumeList(FinanceRechargeParam param);

    List<CustDailyRechargeConsumeInfo> countIntervalDailyReport(StatisticsDate statisticsDate);

    //查找cvs_file_path的检测数据，其中都为上传文件检测，即空号检测
    List<CustDailyInfo> findDetailConsumeDataOfCsv(StatisticsDate statisticsDate);

    //查找empty_check中api检测数据
    List<CustDailyInfo> findDetailConsumeDataOfEmptyCheck(StatisticsDate statisticsDate);

    //查找cvs_file_path的检测数据，其中都为上传文件检测，即空号检测
    List<CustDailyInfo> findDetailConsumeDataOfrealTimeCsv(StatisticsDate statisticsDate);

    //查找empty_check中api检测数据
    List<CustDailyInfo> findDetailConsumeDataOfRealtimeCheck(StatisticsDate statisticsDate);
    
    List<CustDailyInfo> findDetailConsumeDataOfInternationalCheck(StatisticsDate statisticsDate);
    
    List<CustDailyInfo> findDetailConsumeDataOfIntDirectCheck(StatisticsDate statisticsDate);

    Long queryTotalRechargeInfo(@Param("agentId") Long agentId);
}
