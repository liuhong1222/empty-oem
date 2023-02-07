package com.cc.oem.modules.agent.service.records;


import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.records.DailyQueryParam;
import com.cc.oem.modules.agent.model.param.records.QueryAgentDailyParam;
import com.cc.oem.modules.job.entity.task.StatisticsDate;

/**
 *
 *
 * @author wade
 *
 *
 */
public interface AgentDailyStasticService {

    R queryAgentDailyList(DailyQueryParam param);

    void generateDailyAgentStatistic(StatisticsDate statisticsDate);

    R queryCustDailyList(DailyQueryParam param);
}
