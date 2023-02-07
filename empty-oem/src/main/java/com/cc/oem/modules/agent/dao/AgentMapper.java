package com.cc.oem.modules.agent.dao;

import com.cc.oem.modules.agent.entity.Agent;
import com.cc.oem.modules.agent.model.data.AgentInfoData;
import com.cc.oem.modules.agent.model.data.records.WaitTodoData;
import com.cc.oem.modules.agent.model.param.AgentInfoParam;
import com.cc.oem.modules.agent.BaseOemMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface AgentMapper extends BaseOemMapper<Agent, Long> {
    /**
     * 查询代理商列表
     */
    List<AgentInfoData>  queryAgentInfoList(AgentInfoParam param);

    /**
     * 根据公司名称查询代理商
     */
    Agent queryOneByCompanyName(String companyName);

    /**
     * 根据营业执照编号查询代理商
     */
    Agent queryOneByLicenseNo(String licenseNo);

    /**
     * 更新代理商状态
     */
    void updateStatusById(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 更新代理商删除状态
     */
    void updateDeleteStatusById(@Param("id") Long id, @Param("status") Integer status);

    Agent selectAgentBySysUserId(@Param("sysUserId") Long sysUserId);

    Long countCreUserByAgentId(@Param("agentId") Long agentId);

    Map<String,Integer> countAgent();

    /**
     * 更新代理商审核状态
     */
    int updateAuditStateById(@Param("id") Long id, @Param("auditState") Integer auditState);

    /**
     * 根据客户id查询代理商
     */
    Agent queryOneByCreUserId(Integer creUserId);

    /**
     * 模糊查询公司名称
     */
    List<String> queryCompanyNameList(String companyName);


    List<Agent> queryListByMobile(String mobile);

    int updateRegisterGift(@Param("agentId") Long agentId,@Param("registerGift") Integer registerGift);

    List<WaitTodoData> computAdminToDoNum();
    List<WaitTodoData> computAgentToDoNum(@Param("agentId")Long agentId);

    List<Map> listAgent(@Param("name") String name,@Param("officialWeb")Integer officialWeb);

    List<Long> findAgentListByName(String name);

    int updateAuthenLevel(@Param("agentId") Long agentId,@Param("level") Integer authenLevel);

    int updateMobileCubePath(@Param("path") String path,@Param("agentId") Long agentId,@Param("fileType") String fileType);
    
    List<Long> findAgentIdList();

    List<Long> findUsingthisLevel(String level);

    List<Long> findAllAgent();
}
