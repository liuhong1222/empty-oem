package com.cc.oem.modules.agent.dao.records;

import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.model.data.records.CommonDateTimeData;
import com.cc.oem.modules.agent.model.data.records.ConsumeBalanceForStastics;
import com.cc.oem.modules.agent.model.data.records.ConsumeData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <pre>
 * 客户消费 Mapper 接口
 * </pre>
 *
 */
@Mapper
public interface CustConsumeMapper extends BaseOemMapper {


    List<ConsumeData> queryTodayConsumeData(@Param("timeData") CommonDateTimeData timeData);

    List<ConsumeData> queryTodayConsumeDataOfAgent(@Param("agentId") Long agentId, @Param("timeData") CommonDateTimeData timeData);

}
