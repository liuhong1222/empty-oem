package com.cc.oem.modules.agent.service.records.impl;

import com.alibaba.fastjson.JSON;
import com.cc.oem.common.utils.R;
import com.cc.oem.common.utils.message.DingDingMessage;
import com.cc.oem.modules.agent.dao.CustInfoMapper;
import com.cc.oem.modules.agent.dao.CustomerBalanceMapper;
import com.cc.oem.modules.agent.dao.records.CustConsumeMapper;
import com.cc.oem.modules.agent.model.data.PageInfoWithTotalInfo;
import com.cc.oem.modules.agent.model.data.records.CustDailyPageData;
import com.cc.oem.modules.agent.model.param.records.DailyQueryParam;
import com.cc.oem.modules.agent.service.AgentInfoService;
import com.cc.oem.modules.agent.service.records.AgentDailyStasticService;
import com.cc.oem.modules.job.dao.AgentDailyInfoMapper;
import com.cc.oem.modules.job.dao.CustDailyInfoMapper;
import com.cc.oem.modules.job.dao.CustDailyReAndConMapper;
import com.cc.oem.modules.job.entity.task.*;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wade
 */

@Service
public class AgentDailyStasticServiceImpl implements AgentDailyStasticService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AgentDailyInfoMapper agentDailyInfoMapper;
    @Autowired
    CustInfoMapper custInfoMapper;
    @Autowired
    CustConsumeMapper custConsumeMapper;
    @Autowired
    CustDailyReAndConMapper custDailyReAndConMapper;

    @Autowired
    private CustDailyInfoMapper custDailyInfoMapper;
    
    @Autowired
    private CustomerBalanceMapper customerBalanceMapper;
    
    @Autowired
    private AgentInfoService agentInfoService;
    
    @Autowired
    private DingDingMessage dingDingMessage;

    @Override
    public R queryAgentDailyList(DailyQueryParam param) {
        if(StringUtils.isNotEmpty(param.getStartTime())){
            param.setStartTime(param.getStartTime().replace("-",""));
        }
        if(StringUtils.isNotEmpty(param.getEndTime())){
            param.setEndTime(param.getEndTime().replace("-",""));
        }
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        //查找客户消费记录
        List<AgentDailyInfo> list = agentDailyInfoMapper.queryAgentDailyList(param);
        PageInfoWithTotalInfo<AgentDailyInfo> info = new PageInfoWithTotalInfo<>(list);
        if(list.size()<1){
            return R.ok(info);
        }
        info.setTotalInfo(agentDailyInfoMapper.queryTotalInfo(param));
        return R.ok(info);
    }


    /**
     * 查看客户的日常统计
     * @param param
     * @return
     */
    @Override
    public R queryCustDailyList(DailyQueryParam param) {
        if(StringUtils.isNotEmpty(param.getStartTime())){
            param.setStartTime(param.getStartTime().replace("-",""));
        }
        if(StringUtils.isNotEmpty(param.getEndTime())){
            param.setEndTime(param.getEndTime().replace("-",""));
        }
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        //查找客户消费记录
        List<CustDailyPageData> result = custDailyInfoMapper.findByCustAndDay(param.getAgentId(), param.getStartTime(),param.getEndTime(),param.getPhone());
        PageInfoWithTotalInfo info = new PageInfoWithTotalInfo(result);
        if(result.size()<1){
            return R.ok(info);
        }
        CustDailyInfo custDailyInfo = custDailyInfoMapper.countEachConsumeTotal(param);
        info.setTotalInfo(custDailyInfo);
        return R.ok(info);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateDailyAgentStatistic(StatisticsDate statisticsDate) {
        long st = System.currentTimeMillis();
        logger.info("------客户充值消耗日统计任务开始执行，统计时间：{}------", JSON.toJSONString(statisticsDate));
        try {
        	// 充值统计map
            Map<Long,AgentDailyInfo> rechargeMap = new HashMap<Long, AgentDailyInfo>();
            // 空号消耗统计map
            Map<Long,AgentDailyInfo> emptyConsumeMap = new HashMap<Long, AgentDailyInfo>();
            // 实时消耗统计map
            Map<Long,AgentDailyInfo> realtimeConsumeMap = new HashMap<Long, AgentDailyInfo>();
            // 国际消耗统计map
            Map<Long,AgentDailyInfo> internationalConsumeMap = new HashMap<Long, AgentDailyInfo>();
            // 国际定向消耗统计map
            Map<Long,AgentDailyInfo> intDirectConsumeMap = new HashMap<Long, AgentDailyInfo>();
            // 新增客户统计map
            Map<Long,AgentDailyInfo> addCusNumMap = new HashMap<Long, AgentDailyInfo>();
            // 客户数量统计map
            Map<Long,AgentDailyInfo> cusNumMap = new HashMap<Long, AgentDailyInfo>();
            // 客户余额统计map
            Map<Long,AgentDailyInfo> balanceMap = new HashMap<Long, AgentDailyInfo>();
            // 获取所有代理商id
            List<Long> agentIdList = agentInfoService.findAgentIdList();
            if(CollectionUtils.isEmpty(agentIdList)) {
            	logger.error("代理商日统计任务执行失败，代理商表数据为空");
            	return ;
            }
            
            // 获取指定时间段的充值数量总数
            List<AgentDailyInfo> rechargeList = agentDailyInfoMapper.countIntervalRecharge(statisticsDate);
            if(!CollectionUtils.isEmpty(rechargeList)){
            	rechargeMap = rechargeList.stream().collect(Collectors.toMap(AgentDailyInfo::getAgentId, adi -> adi));
            }
            
            // 获取指定时间段的空号消耗统计数据
            List<AgentDailyInfo> emptyConsumeList = agentDailyInfoMapper.countIntervalEmptyConsume(statisticsDate);
            if(!CollectionUtils.isEmpty(emptyConsumeList)){
            	emptyConsumeMap = emptyConsumeList.stream().collect(Collectors.toMap(AgentDailyInfo::getAgentId, adi -> adi));
            }
            
            // 获取指定时间段的实时消耗统计数据
            List<AgentDailyInfo> realtimeConsumeList = agentDailyInfoMapper.countIntervalRealtimeConsume(statisticsDate);
            if(!CollectionUtils.isEmpty(realtimeConsumeList)){
            	realtimeConsumeMap = realtimeConsumeList.stream().collect(Collectors.toMap(AgentDailyInfo::getAgentId, adi -> adi));
            }
            
            // 获取指定时间段的国际消耗统计数据
            List<AgentDailyInfo> internationalConsumeList = agentDailyInfoMapper.countIntervalInternationalConsume(statisticsDate);
            if(!CollectionUtils.isEmpty(internationalConsumeList)){
            	internationalConsumeMap = internationalConsumeList.stream().collect(Collectors.toMap(AgentDailyInfo::getAgentId, adi -> adi));
            }
            
            // 获取指定时间段的国际定向消耗统计数据
            List<AgentDailyInfo> intDirectConsumeList = agentDailyInfoMapper.countIntervalIntDirectConsume(statisticsDate);
            if(!CollectionUtils.isEmpty(intDirectConsumeList)){
            	intDirectConsumeMap = intDirectConsumeList.stream().collect(Collectors.toMap(AgentDailyInfo::getAgentId, adi -> adi));
            }

            // 获取指定时间段的新增客户统计数据
            List<AgentDailyInfo> addCustConsume = custInfoMapper.countIntervalAddCustNums(statisticsDate);
            if(!CollectionUtils.isEmpty(addCustConsume)){
            	addCusNumMap = addCustConsume.stream().collect(Collectors.toMap(AgentDailyInfo::getAgentId, adi -> adi));
            }

            // 获取代理商客户数量统计数据
            List<AgentDailyInfo> custNumsOfAgents = custInfoMapper.countIntervalAddCustNumsByTime(statisticsDate.getInvokeDate(),statisticsDate.getEndDate());
            if(!CollectionUtils.isEmpty(custNumsOfAgents)){
            	cusNumMap = custNumsOfAgents.stream().collect(Collectors.toMap(AgentDailyInfo::getAgentId, adi -> adi));
            }
            
            // 获取代理商余额统计数据
            List<AgentDailyInfo> customerBalanceList = customerBalanceMapper.getAgentTotalCounts();
            if(!CollectionUtils.isEmpty(customerBalanceList)) {
            	balanceMap = customerBalanceList.stream().collect(Collectors.toMap(AgentDailyInfo::getAgentId, adi -> adi));
            }
            
            List<AgentDailyInfo> list = new ArrayList<AgentDailyInfo>();
            for(Long agentId : agentIdList) {
            	AgentDailyInfo agentDailyInfo = new AgentDailyInfo();
            	agentDailyInfo.setAgentId(agentId);
            	agentDailyInfo.setDayInt(statisticsDate.getInvokeDate());
            	
            	AgentDailyInfo rechargeTemp = rechargeMap.get(agentId);
            	if(rechargeTemp != null) {
            		agentDailyInfo.setEmptyRechargeNum(rechargeTemp.getEmptyRechargeNum());
            		agentDailyInfo.setEmptyRechargeMoney(rechargeTemp.getEmptyRechargeMoney());
            		agentDailyInfo.setRealtimeRechargeNum(rechargeTemp.getRealtimeRechargeNum());
            		agentDailyInfo.setRealtimeRechargeMoney(rechargeTemp.getRealtimeRechargeMoney());
            		agentDailyInfo.setInternationalRechargeNum(rechargeTemp.getInternationalRechargeNum());
            		agentDailyInfo.setInternationalRechargeMoney(rechargeTemp.getInternationalRechargeMoney());
            		agentDailyInfo.setDirectCommonRechargeNum(rechargeTemp.getDirectCommonRechargeNum());
            		agentDailyInfo.setDirectCommonRechargeMoney(rechargeTemp.getDirectCommonRechargeMoney());
            		agentDailyInfo.setLineDirectRechargeNum(rechargeTemp.getLineDirectRechargeNum());
            		agentDailyInfo.setLineDirectRechargeMoney(rechargeTemp.getLineDirectRechargeMoney());
            	}
            	
            	AgentDailyInfo emptyConsumeTemp = emptyConsumeMap.get(agentId);
            	if(emptyConsumeTemp != null) {
            		agentDailyInfo.setEmptyConsume(emptyConsumeTemp.getEmptyConsume());
            	}
            	
            	AgentDailyInfo realtimeConsumeTemp = realtimeConsumeMap.get(agentId);
            	if(realtimeConsumeTemp != null) {
            		agentDailyInfo.setRealtimeConsume(realtimeConsumeTemp.getRealtimeConsume());
            	}
            	
            	AgentDailyInfo internationalConsumeTemp = internationalConsumeMap.get(agentId);
            	if(internationalConsumeTemp != null) {
            		agentDailyInfo.setInternationalConsume(internationalConsumeTemp.getInternationalConsume());
            	}
            	
            	AgentDailyInfo intDirectConsumeTemp = intDirectConsumeMap.get(agentId);
            	if(intDirectConsumeTemp != null) {
            		agentDailyInfo.setDirectCommonConsume(intDirectConsumeTemp.getDirectCommonConsume());
            		agentDailyInfo.setLineDirectConsume(intDirectConsumeTemp.getLineDirectConsume());
            	}
            	
            	AgentDailyInfo addCusNumTemp = addCusNumMap.get(agentId);
            	if(addCusNumTemp != null) {
            		agentDailyInfo.setDailyAddCustNum(addCusNumTemp.getDailyAddCustNum());
            	}
            	
            	AgentDailyInfo cusNumTemp = cusNumMap.get(agentId);
            	if(cusNumTemp != null) {
            		agentDailyInfo.setCustNum(cusNumTemp.getCustNum());
            	}
            	
            	AgentDailyInfo balanceTemp = balanceMap.get(agentId);
            	if(balanceTemp != null) {
            		agentDailyInfo.setEmptyCounts(balanceTemp.getEmptyCounts());
            		agentDailyInfo.setRealtimeCounts(balanceTemp.getRealtimeCounts());
            		agentDailyInfo.setInternationalCounts(balanceTemp.getInternationalCounts());
            		agentDailyInfo.setDirectCommonCounts(balanceTemp.getDirectCommonCounts());
            		agentDailyInfo.setLineDirectCounts(balanceTemp.getLineDirectCounts());
            	}
            	
            	list.add(agentDailyInfo);
            }
            
            insertByCollection(list);
            logger.info("------代理商日统计任务执行完成，统计时间：{}, useTime:{}------", JSON.toJSONString(statisticsDate),System.currentTimeMillis()-st);
		} catch (Exception e) {
			logger.error("oem_agent_daily_info 统计任务执行异常，info:",e);
			dingDingMessage.sendMessage(String.format("警告：oem_agent_daily_info 统计任务执行异常，info:%s", e));
		}
        

    }


    private void insertByCollection(Collection<AgentDailyInfo> values){
        int i = agentDailyInfoMapper.batchSave(new ArrayList<>(values));
        if(i!=values.size()){
        	logger.error("{}, oem_agent_daily_info 统计任务执行异常，数据库入库失败，info:{}",JSON.toJSONString(values));
            throw new RuntimeException("oem_agent_daily_info 统计任务执行异常，数据库入库失败");
        }
    }
}
