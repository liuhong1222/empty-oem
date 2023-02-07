/*
 *
 *  Copyright (c) wade_woke updateStatus based on pgx
 *
 */

package com.cc.oem.modules.job.dao;

import com.cc.oem.modules.agent.model.data.records.CustDailyPageData;
import com.cc.oem.modules.agent.model.data.records.DeskConsumeData;
import com.cc.oem.modules.agent.model.param.records.DailyQueryParam;
import com.cc.oem.modules.job.entity.task.CustDailyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 最终实际发送的短信模板
 *
 * @author wade_woke
 * @date 2021-06-01
 */
@Mapper
public interface CustDailyInfoMapper {

    List<CustDailyInfo> findEmptyListByDailyTask(@Param("startDate")String startDate,@Param("endDate")String endDate);
    List<CustDailyInfo> findRealtimeListByDailyTask(@Param("startDate")String startDate,@Param("endDate")String endDate);
    List<CustDailyInfo> findInternationalListByDailyTask(@Param("startDate")String startDate,@Param("endDate")String endDate);
    List<CustDailyInfo> findIntDirectListByDailyTask(@Param("startDate")String startDate,@Param("endDate")String endDate);

    int batchSaveEmptyConsume(List<CustDailyInfo> list);

    int batchSaveRealtimeConsume(List<CustDailyInfo> list);
    
    int batchSaveInternationalConsume(List<CustDailyInfo> list);
    
    int batchSaveIntDirectConsume(List<CustDailyInfo> list);

    int updateEmptyConsume(CustDailyInfo each);

    int updateRealtimeConsume(CustDailyInfo each);
    
    int updateInternationalConsume(CustDailyInfo each);
    
    int updateIntDirectConsume(CustDailyInfo each);

    CustDailyInfo countEachConsumeTotal(DailyQueryParam param);

    List<Map<String,Long>> countTotalConsume(@Param("agentId") Long agentId);
    
    List<Map<String,Long>> countTotalConsumeNew(@Param("agentId") Long agentId);
    
    List<CustDailyPageData> findByCustAndDay(@Param("agentId") Long agentId, @Param("startTime") String startTime, @Param("endTime")String endTime,@Param("phone")String phone);

    List<DeskConsumeData> getDeskConsumeByEmpty(@Param("agentId") Long agentId);
    
    List<DeskConsumeData> getDeskConsumeByRealtime(@Param("agentId") Long agentId);
    
    List<DeskConsumeData> getDeskConsumeByInternational(@Param("agentId") Long agentId);
    
    List<DeskConsumeData> getDeskConsumeByDirectCommon(@Param("agentId") Long agentId);
    
    List<DeskConsumeData> getDeskConsumeByLineDirect(@Param("agentId") Long agentId);
}
