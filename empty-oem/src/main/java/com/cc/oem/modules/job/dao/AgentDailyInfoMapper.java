/*
 *
 *  Copyright (c) wade_woke updateStatus based on pgx
 *
 */

package com.cc.oem.modules.job.dao;

import com.cc.oem.modules.agent.model.data.records.CustDailyPageData;
import com.cc.oem.modules.agent.model.param.records.DailyQueryParam;
import com.cc.oem.modules.agent.model.param.records.QueryAgentDailyParam;
import com.cc.oem.modules.job.entity.task.AgentDailyInfo;
import com.cc.oem.modules.job.entity.task.StatisticsDate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 最终实际发送的短信模板
 *
 * @author wade_woke
 * @date 2021-06-01
 */
@Mapper
public interface AgentDailyInfoMapper {

    List<AgentDailyInfo> findListByDailyTask(String invokeTime);

    int batchSave(List<AgentDailyInfo> list);

    int updateOneRecord(AgentDailyInfo info);



    List<AgentDailyInfo> countIntervalRecharge(StatisticsDate statisticsDate);

    List<AgentDailyInfo> countIntervalEmptyConsume(StatisticsDate statisticsDate);

    List<AgentDailyInfo> countIntervalRealtimeConsume(StatisticsDate statisticsDate);
    
    List<AgentDailyInfo> countIntervalInternationalConsume(StatisticsDate statisticsDate);
    
    List<AgentDailyInfo> countIntervalIntDirectConsume(StatisticsDate statisticsDate);

    List<AgentDailyInfo> queryAgentDailyList(DailyQueryParam param);

    List<CustDailyPageData> queryCustDailyEmpty(QueryAgentDailyParam param);


    AgentDailyInfo queryTotalInfo(DailyQueryParam param);
}
