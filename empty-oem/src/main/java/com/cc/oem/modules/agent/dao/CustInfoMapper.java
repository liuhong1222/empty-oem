package com.cc.oem.modules.agent.dao;

import com.cc.oem.modules.agent.entity.CustDetailInfo;
import com.cc.oem.modules.agent.entity.CustInfo;
import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.model.data.CustExportData;
import com.cc.oem.modules.agent.model.data.userManage.CustomerExtQueryData;
import com.cc.oem.modules.agent.model.param.CustInfoParam;
import com.cc.oem.modules.agent.model.param.userManage.CustextAuditParam;
import com.cc.oem.modules.agent.model.param.userManage.CustomerVerifyQueryParam;
import com.cc.oem.modules.agent.service.CustInfoService;
import com.cc.oem.modules.job.entity.task.AgentDailyInfo;
import com.cc.oem.modules.job.entity.task.CustDailyInfo;
import com.cc.oem.modules.job.entity.task.StatisticsDate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
@Mapper
public interface CustInfoMapper extends BaseOemMapper<CustInfo, Long> {

    List<CustExportData> selectListCustParam(CustInfoParam param);

    Integer countCustNumOfAgent(@Param("agentId")Long agentId);

    List<CustomerExtQueryData> queryAuditingCustExtList(CustomerVerifyQueryParam param);

    int auditCustExtVerify(CustextAuditParam param);

    List<Map> findUserIdMobileByMobileAndAgentId(@Param("userPhone") String userPhone, @Param("agentId") Long agentId);

    List<String> selectMobileListByCustIds(@Param("list") List<String> list);

    CustDetailInfo detailById(Long id);

    List<Long> selectCustIdListByAgentId(Map map);

    CustInfo selectById(Long custId);

    Integer countRealtimeCustNums(Long agentId);

    List<AgentDailyInfo> countIntervalAddCustNums(StatisticsDate statisticsDate);

    List<AgentDailyInfo> countIntervalAddCustNumsByTime(@Param("invokeDate") Long invokeDate, @Param("endDate") String endDate);

    int setApiStateByCustId(@Param("custId") Long custId, @Param("state")Integer state);

    int updateCustAuthenLevel(@Param("custId") Long custId,@Param("level") Integer authenLevel);

    int updateCustAuthenLevelOfAgent(@Param("agentId")Long agentId, @Param("level") Integer authenLevel);

    List<CustInfo> selectCustInfoListByAgentId(@Param("agentId")Long agentId);

    List<Long> selectCustIdListByCustName(@Param("agentId")Long agentId,@Param("customerName") String customerName);

    CustInfo findByPhone(@Param("phone") String phone, @Param("agentId")Long agentId);

    int saveCustOfAgent(CustInfo custInfo);

    CustInfo queryByAgentIdAndName(@Param("agentId")Long agentId,@Param("name") String name);
}