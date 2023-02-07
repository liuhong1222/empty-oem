package com.cc.oem.modules.job.dao;

import com.cc.oem.modules.agent.model.param.records.DailyQueryParam;
import com.cc.oem.modules.job.entity.task.CustDailyRechargeConsumeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 客户充值消耗统计表
 *
 * @author wade_woke
 * @date 2021-06-01
 */
@Mapper
public interface CustDailyReAndConMapper {

    List<CustDailyRechargeConsumeInfo> findListByDailyTask(String invokeTime);

    int batchSave(List<CustDailyRechargeConsumeInfo> list);

    int updateById(CustDailyRechargeConsumeInfo each);

    List<CustDailyRechargeConsumeInfo> queryCustDailyList(DailyQueryParam param);
}
