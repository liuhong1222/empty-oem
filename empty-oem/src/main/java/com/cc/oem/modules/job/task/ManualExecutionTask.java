package com.cc.oem.modules.job.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.oem.common.utils.message.DingDingMessage;
import com.cc.oem.modules.agent.dao.*;
import com.cc.oem.modules.agent.dao.finance.CustomerRefundMapper;
import com.cc.oem.modules.agent.entity.AgentAccount;
import com.cc.oem.modules.agent.entity.CustomerBalance;
import com.cc.oem.modules.agent.service.CustInfoService;
import com.cc.oem.modules.agent.service.records.AgentDailyStasticService;
import com.cc.oem.modules.job.entity.task.StatisticsDate;
import com.cc.oem.modules.job.service.impl.CommonTaskMethodServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 定时任务
 * 汇总统计客户每天的消耗情况，日充值情况
 * 根据结果统计得到相关的代理商情况
 */
@Component("manualExecutionTask")
public class ManualExecutionTask {
    private final static Logger logger = LoggerFactory.getLogger(ManualExecutionTask.class);

    @Autowired
    private AgentDailyStasticService agentDailyStasticService;
    @Autowired
    private CommonTaskMethodServiceImpl commonTaskMethodService;

    @Autowired
    private CustInfoService custInfoService;
    @Autowired
    private DingDingMessage dingDingMessage;
    @Autowired
    private AgentRechargeMapper agentRechargeMapper;
    @Autowired
    private AgentAccountMapper agentAccountMapper;
    @Autowired
    private CustomerRechargeMapper customerRechargeMapper;
    @Autowired
    private CustomerBalanceMapper customerBalanceMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private CustomerRefundMapper customerRefundMapper;

    /**
     * 手动执行代理商统计功能
     */
    //2021-01-03格式,时间跨度为1，代表两天
    public void agentDailyHandle(String invoke) {
        String[] split = invoke.split(",");
        String startTime = split[0];
        String dayCount = split[1];
        List<StatisticsDate> list = commonTaskMethodService.getStatisticsList(startTime, Integer.valueOf(dayCount));
        for(StatisticsDate eachDate:list) {
            try{
                logger.info("QuartzEntity：{}，手动执行代理商统计功能,执行区间{}",new Date(),eachDate);
                agentDailyStasticService.generateDailyAgentStatistic(eachDate);
            } catch (Exception e) {
                logger.error("------手动执行代理商统计功能执行异常，统计时间：{},info:------", JSON.toJSONString(eachDate), e);
                dingDingMessage.sendMessage("手动执行代理商统计功能执行异常，统计时间参数：{}"+JSONObject.toJSONString(eachDate));
            }

        }
    }

    //2021-01-03格式,时间跨度为1，代表两天
    /**
     * 手动执行客户充值统计功能
     */
    public void custDailyRechargeHandle(String invoke) {
        String[] split = invoke.split(",");
        String startTime = split[0];
        String dayCount = split[1];
        List<StatisticsDate> list = commonTaskMethodService.getStatisticsList(startTime, Integer.valueOf(dayCount));
        for(StatisticsDate eachDate:list) {
            try{
                logger.info("QuartzEntity：{}，手动执行客户充值统计功能,执行区间{}",new Date(),eachDate);
                custInfoService.generateDailyCustStatistic(eachDate);
            } catch (Exception e) {
                logger.error("------手动执行客户充值统计功能执行异常，统计时间：{},info:------", JSON.toJSONString(eachDate), e);
                dingDingMessage.sendMessage("手动执行客户充值统计功能执行异常，统计时间参数：{}"+JSONObject.toJSONString(eachDate));
            }

        }
    }

    //2021-01-03格式,时间跨度为1，代表两天

    /**
     * 手动执行客户消耗统计功能
     */
    public void CustConsumeDailyHandle(String invoke) {
        String[] split = invoke.split(",");
        String startTime = split[0];
        String dayCount = split[1];
        List<StatisticsDate> list = commonTaskMethodService.getStatisticsList(startTime, Integer.valueOf(dayCount));
        for(StatisticsDate eachDate:list) {
            try{
                logger.info("QuartzEntity：{}，手动执行客户消耗统计功能,执行区间{}",new Date(),eachDate);
                custInfoService.generateDailyCusEmptyConsume(eachDate);
            } catch (Exception e) {
                logger.error("------手动执行客户消耗统计功能出现异常，统计时间：{},info:------", JSON.toJSONString(eachDate), e);
                dingDingMessage.sendMessage("手动执行客户消耗统计功能出现异常，统计时间参数：{}"+JSONObject.toJSONString(eachDate));
            }

        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void refreshCustRechargeInfo(String invoke) {
        String[] split = invoke.split(",");
        String startTime = split[0];
        String endTime = split[1];
        List<CustomerBalance> list = customerRechargeMapper.countTotalRechargeOfCust(startTime,endTime);
        logger.info("共查出{}条历史充值记录",list.size());
        List<CustomerBalance> batchSaveList = new ArrayList<>();
        List<CustomerBalance> updateList = new ArrayList<>();
        List<CustomerBalance> historyData = customerBalanceMapper.countHistoryData();
        Map<Long, CustomerBalance> collect = historyData.stream().collect(Collectors.toMap(CustomerBalance::getCustomerId, v -> v));

        for(CustomerBalance eachInfo:list) {
            if(collect.containsKey(eachInfo.getCustomerId())){
                updateList.add(eachInfo);
            }else{
                batchSaveList.add(eachInfo);
            }
        }
//		if(batchSaveList.size()>0){
//			int i = customerBalanceMapper.batchSave(batchSaveList);
//			if(i!=1){
//				throw new RuntimeException();
//			}
//		}
        if(batchSaveList.size()>0){
            logger.error("手动刷新客户充值统计记录出现异常，可插入数据为：{}",JSONObject.toJSONString(batchSaveList));
            return;
        }

        if(updateList.size()>0){
            logger.info("待更新的客户记录是多少条{}",updateList.size());
            for(CustomerBalance update:updateList){
                int i = customerBalanceMapper.addRechargeInfo(update);
                if(i!=1){
                    throw new RuntimeException();
                }
            }
        }
    }




    /**
     * 手动执行插入历史代理商账号表
     *
     * 2021-01-02 00:32:23,2021-01-02 06:32:23
     */
    public void createAgentAccount() {
        List<Long> agentList = agentMapper.findAllAgent();
        List<Long> agentAccountList = agentAccountMapper.findAllAgentAccount();
        agentList.removeAll(agentAccountList);//移除之后是所有味刷入agentAccount的agent
        if(agentList.size()>0){
            batchSaveAgentAccount(agentList);
        }
    }

    private void batchSaveAgentAccount(List<Long> agentIdList) {
        int i = agentAccountMapper.batchSave(agentIdList);
        if(i!=agentIdList.size()){
            throw new RuntimeException();
        }
        logger.info("手动执行插入历史代理商账号表成功，共执行插入{}条",agentIdList.size());
    }


    //2021-01-03格式,时间跨度为1，代表两天
    /**
     * 手动执行代理商充值统计功能
     *
     * 2021-01-02 00:32:23,2021-01-02 06:32:23
     */
    public void refreshAgentAccount(String invoke) {
        createAgentAccount();
        operation(invoke);

    }

    @Transactional(rollbackFor = Exception.class)
    public void operation(String invoke) {
        String[] split = invoke.split(",");
        String startTime = split[0];
        String endTime = split[1];

        //统计代理商的充值记录
        List<AgentAccount> agentAccounts = agentRechargeMapper.calcuHistoryAgentInfo(startTime, endTime);
        for(AgentAccount newInfo:agentAccounts){
            newInfo.setEmptyRechargeNumber(newInfo.getEmptyBalance());
            newInfo.setRealtimeRechargeNumber(newInfo.getRealtimeBalance());
            int i = agentAccountMapper.updateAgentRechargeInfo(newInfo);
            if(i!=1){
                throw new RuntimeException();
            }
        }
        //统计客户的充值记录
        List<AgentAccount> list = customerRechargeMapper.countTotalRechargeOfAgent(startTime,endTime);
        logger.info("客户充值记录有{}条",list.size());
        for(AgentAccount newInfo:list){
            int i = agentAccountMapper.updateCustRechargeInfo(newInfo);
            if(i!=1){
                throw new RuntimeException();
            }
        }
        //退款记录刷入
        List<AgentAccount> refundList = customerRefundMapper.countTotalRefundOfCust(startTime,endTime);
        logger.info("退款记录有{}条",refundList.size());
        for(AgentAccount account:refundList){
            int i = agentAccountMapper.addRefundOfAgent(account);
            if(i!=1){
                throw new RuntimeException();
            }
        }
        //最终余额应为代理商充值总额-客户充值总条数+客户退款条数

        logger.info("------代理商充值消耗日统计任务执行完成，统计时间：{}------",invoke);

    }

    private void updateOneByOne(Collection<AgentAccount> values) {
        List<AgentAccount> updateList = new ArrayList<>(values);
        for(AgentAccount each:updateList){
            int i = agentAccountMapper.updateAgentAccountByAgentId(each);
            if(i!=1){
                logger.error("统计历史数据时修改历史数据出错");
                throw new RuntimeException();
            }
        }
    }


    private AgentAccount initAgentAccount(AgentAccount newInfo) {
        if(newInfo.getCategory()==0){
            newInfo.setEmptyRechargeNumber(newInfo.getEmptyRechargeNumber());
            newInfo.setEmptyBalance(newInfo.getEmptyBalance());
            newInfo.setEmptyRechargeMoney(newInfo.getEmptyRechargeMoney());
            newInfo.setRealtimeBalance(0l);
            newInfo.setRealtimeRechargeMoney(BigDecimal.ZERO);
            newInfo.setRealtimeRechargeNumber(0l);
        }else if(newInfo.getCategory()==1){
            newInfo.setEmptyRechargeNumber(0l);
            newInfo.setEmptyBalance(0l);
            newInfo.setEmptyRechargeMoney(BigDecimal.ZERO);
            newInfo.setRealtimeBalance(newInfo.getRealtimeBalance());
            newInfo.setRealtimeRechargeMoney(newInfo.getRealtimeRechargeMoney());
            newInfo.setRealtimeRechargeNumber(newInfo.getRealtimeRechargeNumber());
        }
        return newInfo;

    }

    private void updateAgentAccount(AgentAccount newInfo, AgentAccount agentAccount) {
        if(newInfo.getCategory()==0){
            agentAccount.setEmptyRechargeNumber(agentAccount.getEmptyRechargeNumber()+newInfo.getEmptyRechargeNumber());
            agentAccount.setEmptyBalance(agentAccount.getEmptyBalance()+newInfo.getEmptyBalance());
            agentAccount.setEmptyRechargeMoney(agentAccount.getEmptyRechargeMoney().add(newInfo.getEmptyRechargeMoney()));
        }else if(newInfo.getCategory()==1){
            agentAccount.setRealtimeBalance(agentAccount.getRealtimeBalance()+newInfo.getRealtimeBalance());
            agentAccount.setRealtimeRechargeMoney(agentAccount.getRealtimeRechargeMoney().add(newInfo.getRealtimeRechargeMoney()));
            agentAccount.setRealtimeRechargeNumber(agentAccount.getRealtimeRechargeNumber()+newInfo.getRealtimeRechargeNumber());
        }

    }

}
