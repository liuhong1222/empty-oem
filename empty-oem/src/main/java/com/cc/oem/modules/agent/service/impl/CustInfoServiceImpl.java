package com.cc.oem.modules.agent.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.cc.oem.common.redis.RedisClient;
import com.cc.oem.common.utils.R;
import com.cc.oem.common.utils.message.DingDingMessage;
import com.cc.oem.modules.agent.constants.RedisKeyConstant;
import com.cc.oem.modules.agent.dao.*;
import com.cc.oem.modules.agent.dao.finance.CustomerConsumeMapper;
import com.cc.oem.modules.agent.dao.records.ApiSettingMapper;
import com.cc.oem.modules.agent.entity.*;
import com.cc.oem.modules.agent.entity.agentSettings.Goods;
import com.cc.oem.modules.agent.entity.records.ApiSettings;
import com.cc.oem.modules.agent.model.data.CustExportData;
import com.cc.oem.modules.agent.model.data.customer.CustomerBalanceInfo;
import com.cc.oem.modules.agent.model.data.customer.CustomerGivenRechargeNumInfo;
import com.cc.oem.modules.agent.model.param.CustInfoParam;
import com.cc.oem.modules.agent.model.param.CustRechargeParam;
import com.cc.oem.modules.agent.model.param.userManage.CreateCustParam;
import com.cc.oem.modules.agent.service.AgentInfoService;
import com.cc.oem.modules.agent.service.CustInfoService;
import com.cc.oem.modules.agent.service.CustOrderService;
import com.cc.oem.modules.agent.service.RefreshCacheService;
import com.cc.oem.modules.config.utils.Snowflake;
import com.cc.oem.modules.job.dao.CustDailyInfoMapper;
import com.cc.oem.modules.job.dao.CustDailyReAndConMapper;
import com.cc.oem.modules.job.entity.task.CustDailyInfo;
import com.cc.oem.modules.job.entity.task.CustDailyRechargeConsumeInfo;
import com.cc.oem.modules.job.entity.task.StatisticsDate;
import com.cc.oem.modules.sys.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenzj
 * @since 2018/5/23
 */

@Service
public class CustInfoServiceImpl implements CustInfoService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    IdCardInfoMapper cardInfoMapper;
    @Autowired
    BusinessLicenceInfoMapper businessLicenceInfoMapper;
    @Autowired
    CreUserMapper creUserMapper;

    @Autowired
    SysUserService sysUserService;
    @Autowired
    CustInfoMapper custInfoMapper;
    @Autowired
    CustomerRechargeMapper customerRechargeMapper;
    @Autowired
    CustomerConsumeMapper customerConsumeMapper;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    AgentAccountMapper agentAccountMapper;
    @Autowired
    CustomerBalanceMapper customerBalanceMapper;
    @Autowired
    private CustDailyInfoMapper custDailyInfoMapper;
    @Autowired
    private CustDailyReAndConMapper custDailyReAndConMapper;
    @Autowired
    private CustOrderService custOrderService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentInfoService agentInfoService;
    @Autowired
    private DingDingMessage dingDingMessage;
    @Autowired
    private RefreshCacheService refreshCacheService;
    @Autowired
    private ApiSettingMapper apiSettingMapper;
    @Autowired
    private Snowflake snowflake;
    @Autowired
    AgentSetMapper agentSetMapper;
    

    public static Integer USER_TYPE_COMPANY = 1;
    public static Integer IS_FAKE_AUTH_TRUE = 1;

    @Override
    public R custList(CustInfoParam param, boolean isExport) {
        initParam(param);
        PageHelper.startPage(param.getCurrentPage(),param.getPageSize());
        List<CustExportData> list = custInfoMapper.selectListCustParam(param);
        boolean isAdmin = sysUserService.judgeIfAdmin(sysUserService.getSysUserId());
        if(isAdmin){
            list.stream().forEach(k->k.setCanRefundFlag(false));
        }
        PageInfo<CustExportData> pageInfo = new PageInfo<>(list);
        return R.ok(pageInfo);
    }


    @Override
    public R findPersonCustById(Long customerId) {
        CustDetailInfo custDetailInfo = new CustDetailInfo();
        if (customerId != null) {
            custDetailInfo = custInfoMapper.detailById(customerId);
        }
        return R.ok(custDetailInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R presentNum(Long custId,Long agentId) {
        if (custId == null) {
            return R.error("缺少必要参数");
        }
        CustInfo custInfo = custInfoMapper.selectById(custId);
        if (agentId == null || custInfo == null || custInfo.getAgentId() == null) {
            return R.error("数据库异常，请联系客服");
        }
        if (!custInfo.getAgentId().equals(agentId)) {
            return R.error("该用户不是您的用户，没有权限操作");
        }
        int count = customerRechargeMapper.countRegisterSend(custId);
        if (count != 0) {
            return R.error("该用户已经注册赠送过，不能重复赠送");
        }
        Goods goods = new Goods();
        goods.setName("注册赠送");
        CustRechargeParam param = new CustRechargeParam();
        param.setAmount(BigDecimal.ZERO);
        param.setCategory(0);
        param.setGoodsId(0l);
        param.setNumber(5000);
        param.setCustId(custId);
        param.setPayType(2);
        param.setPrice(BigDecimal.ZERO);
        param.setRemark("注册赠送");
        return custOrderService.commonPresentRechargeOperation(goods,param,agentId);
    }

    public void initParam(CustInfoParam param) {
//        boolean isAdmin = sysUserService.judgeIfAdmin(sysUserService.getSysUserId());
//        /*1管理员，2代理商*/
//        if (isAdmin) {
//            param.setAdminType(1);
            //设置默认查询时间
            if (!StringUtils.isBlank(param.getStartTimeStr())) {
                param.setStartTimeStr(param.getStartTimeStr()+" 00:00:00");
                param.setEndTimeStr(param.getEndTimeStr()+" 23:59:59");
            }
//        } else {
//            param.setAdminType(2);

    }

    /**
     *
     * @param statisticsDate
     * 充值日统计
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateDailyCustStatistic(StatisticsDate statisticsDate) {
        logger.info("------客户充值日统计任务开始执行，统计时间：{}------", JSON.toJSONString(statisticsDate));
        try {
        	// 获取今日所有统计
            List<CustDailyRechargeConsumeInfo> rechargeList = customerRechargeMapper.countIntervalDailyReport(statisticsDate);
            if (!CollectionUtils.isEmpty(rechargeList)) {
            	insertCustReAndConsumeByCollection(rechargeList);
            }
            logger.info("------客户充值消耗日统计任务执行完成，统计时间：{}------", JSON.toJSONString(statisticsDate));
		} catch (Exception e) {
			logger.error("客户充值日统计任务执行异常，info:",e);
			dingDingMessage.sendMessage(String.format("警告：客户充值日统计任务执行异常，info:%s", e));
		}
    }

    private void insertCustReAndConsumeByCollection(Collection<CustDailyRechargeConsumeInfo> values){
        int i = custDailyReAndConMapper.batchSave(new ArrayList<>(values));
        if(i!=values.size()){
        	logger.error("充值日统计任务执行失败，数据库入库异常，info:{}",JSON.toJSONString(values));
            throw new RuntimeException("充值日统计任务执行失败，数据库入库异常");
        }
    }

    /**
     * 填充客户消耗统计数据
     * @param statisticsDate
     */
    @Transactional(rollbackFor = Exception.class)
    public void generateDailyCusEmptyConsume(StatisticsDate statisticsDate) {

        logger.info("------客户空号检测消耗日统计任务开始执行，统计时间：{}------", JSON.toJSONString(statisticsDate));
        try {
        	// 获取今日所有统计
            Map<String, CustDailyInfo> reportDailyMap = selectAllEmptyReportsByInvokeDate(statisticsDate.getStartDate().substring(0, 10).replace("-", ""),
            		statisticsDate.getEndDate().substring(0, 10).replace("-", ""));
            Map<String, CustDailyInfo> updateDailyMap = new HashMap<>();
            Map<String, CustDailyInfo> newDailyMap = new HashMap<>();
            // 获取指定时间段的message_report数量总数
            List<CustDailyInfo> emptyCheckList = customerConsumeMapper.findDetailConsumeDataOfEmptyCheck(statisticsDate);
            for(CustDailyInfo eachEmptyCheck:emptyCheckList){
                String key = getCustDimension(eachEmptyCheck);
                if(reportDailyMap.containsKey(key)){
                    updateReportEmptyConsume(reportDailyMap.get(key),eachEmptyCheck);
                    updateDailyMap.put(key,reportDailyMap.get(key));
                }else{
                    initEmptyCheck(eachEmptyCheck);
                    newDailyMap.put(key,eachEmptyCheck);
                }
            }
            List<CustDailyInfo> emptyApiCheckList = customerConsumeMapper.findDetailConsumeDataOfCsv(statisticsDate);
            for(CustDailyInfo eachEmptyCheck:emptyApiCheckList){
                String key = getCustDimension(eachEmptyCheck);
                if(reportDailyMap.containsKey(key)){
                    updateReportEmptyConsume(reportDailyMap.get(key),eachEmptyCheck);
                    updateDailyMap.put(key,reportDailyMap.get(key));
                }else{
                    initEmptyCheck(eachEmptyCheck);
                    newDailyMap.put(key,eachEmptyCheck);
                }
            }

            if (!CollectionUtils.isEmpty(newDailyMap)) {
            	custDailyInfoMapper.batchSaveEmptyConsume(new ArrayList(newDailyMap.values()));
            }

            //单个修改今日的记录
            if (!CollectionUtils.isEmpty(updateDailyMap)) {
                Collection<CustDailyInfo> updateList = reportDailyMap.values();
                for(CustDailyInfo each:updateList){
                    int i = custDailyInfoMapper.updateEmptyConsume(each);
                    if(i!=1){
                        logger.info("空号检测日统计记录修改失败，数据库update失败，修改信息：{}",each);
                        dingDingMessage.sendMessage(String.format("警告：空号检测日统计记录修改失败，数据库update失败，修改信息：%s", each));;
                    }
                }
            }

            logger.info("------代理商日统计空号消耗任务执行完成，统计时间：{}------", JSON.toJSONString(statisticsDate));
            generateDailyCustRealtimeConsume(statisticsDate);
            generateDailyCustInternationalConsume(statisticsDate);
            generateDailyCustIntDirectConsume(statisticsDate);
		} catch (Exception e) {
			logger.info("空号检测日统计任务执行异常，info:",e);
            dingDingMessage.sendMessage(String.format("警告：空号检测日统计任务执行异常，info：%s", e));;
		}
    }

    private void initEmptyCheck(CustDailyInfo eachEmptyCheck) {
        eachEmptyCheck.setEmptyTotal(eachEmptyCheck.getTotalNumber());
    }

    /**
     * 填充客户实时消耗统计数据
     * @param statisticsDate
     */
    public void generateDailyCustRealtimeConsume(StatisticsDate statisticsDate) {

        logger.info("------客户实时检测消耗日统计任务开始执行，统计时间：{}------", JSON.toJSONString(statisticsDate));
        try {
        	// 获取今日所有统计
            Map<String, CustDailyInfo> reportDailyMap = selectAllRealtimeReportsByInvokeDate(statisticsDate.getStartDate().substring(0, 10).replace("-", ""),
            		statisticsDate.getEndDate().substring(0, 10).replace("-", ""));
            Map<String, CustDailyInfo> newDailyMap = new HashMap<>();
            Map<String, CustDailyInfo> updateDailyMap = new HashMap<>();
            // 获取指定时间段的message_report数量总数
            List<CustDailyInfo> realtimeCheckList = customerConsumeMapper.findDetailConsumeDataOfRealtimeCheck(statisticsDate);
            for(CustDailyInfo eachEmptyCheck:realtimeCheckList){
                String key = getCustDimension(eachEmptyCheck);
                if(reportDailyMap.containsKey(key)){
                    updateReportDailyRealtimeConsume(reportDailyMap.get(key),eachEmptyCheck);
                    updateDailyMap.put(key,reportDailyMap.get(key));
                }else{
                    initRealtimeCheck(eachEmptyCheck);
                    newDailyMap.put(key,eachEmptyCheck);
                }
            }
            List<CustDailyInfo> emptyApiCheckList = customerConsumeMapper.findDetailConsumeDataOfrealTimeCsv(statisticsDate);
            for(CustDailyInfo realtimeCheck:emptyApiCheckList){
                String key = getCustDimension(realtimeCheck);
                if(updateDailyMap.containsKey(key)){
                    updateReportDailyRealtimeConsume(updateDailyMap.get(key),realtimeCheck);
                }else if(reportDailyMap.containsKey(key)){
                    updateReportDailyRealtimeConsume(reportDailyMap.get(key),realtimeCheck);
                    updateDailyMap.put(key,reportDailyMap.get(key));
                }else{
                    initRealtimeCheck(realtimeCheck);
                    newDailyMap.put(key,realtimeCheck);
                }
            }

            if (!CollectionUtils.isEmpty(newDailyMap)) {
            	custDailyInfoMapper.batchSaveRealtimeConsume(new ArrayList(newDailyMap.values()));
            }
            //单个修改今日的记录
            if (!CollectionUtils.isEmpty(updateDailyMap)) {
                Collection<CustDailyInfo> updateList = updateDailyMap.values();
                for(CustDailyInfo each:updateList){
                    int i = custDailyInfoMapper.updateRealtimeConsume(each);
                    if(i!=1){
                    	logger.info("实时检测日统计记录修改失败，数据库update失败，修改信息：{}",each);
                        dingDingMessage.sendMessage(String.format("警告：实时检测日统计记录修改失败，数据库update失败，修改信息：%s", each));;
                    }
                }
            }
            logger.info("------代理商日统计实时检测任务执行完成，统计时间：{}------", JSON.toJSONString(statisticsDate));
		} catch (Exception e) {
			logger.info("实时检测日统计任务执行异常，info:",e);
            dingDingMessage.sendMessage(String.format("警告：实时检测日统计任务执行异常，info：%s", e));;
		}
    }
    
    /**
     * 填充客户国际消耗统计数据
     * @param statisticsDate
     */
    public void generateDailyCustInternationalConsume(StatisticsDate statisticsDate) {
        logger.info("------客户国际检测消耗日统计任务开始执行，统计时间：{}------", JSON.toJSONString(statisticsDate));
        try {
        	// 获取今日所有统计
            Map<String, CustDailyInfo> reportDailyMap = selectAllInternationalReportsByInvokeDate(statisticsDate.getStartDate().substring(0, 10).replace("-", ""),
            		statisticsDate.getEndDate().substring(0, 10).replace("-", ""));
            Map<String, CustDailyInfo> newDailyMap = new HashMap<>();
            Map<String, CustDailyInfo> updateDailyMap = new HashMap<>();
            // 获取指定时间段的message_report数量总数
            List<CustDailyInfo> internationalCheckList = customerConsumeMapper.findDetailConsumeDataOfInternationalCheck(statisticsDate);
            for(CustDailyInfo eachInternationalCheck:internationalCheckList){
                String key = getCustDimension(eachInternationalCheck);
                if(reportDailyMap.containsKey(key)){
                	updateReportDailyInternationaleConsume(reportDailyMap.get(key),eachInternationalCheck);
                    updateDailyMap.put(key,reportDailyMap.get(key));
                }else{
                	initInternationalCheck(eachInternationalCheck);
                    newDailyMap.put(key,eachInternationalCheck);
                }
            }

            if (!CollectionUtils.isEmpty(newDailyMap)) {
            	custDailyInfoMapper.batchSaveInternationalConsume(new ArrayList(newDailyMap.values()));
            }
            //单个修改今日的记录
            if (!CollectionUtils.isEmpty(updateDailyMap)) {
                Collection<CustDailyInfo> updateList = updateDailyMap.values();
                for(CustDailyInfo each:updateList){
                    int i = custDailyInfoMapper.updateInternationalConsume(each);
                    if(i!=1){
                    	logger.info("国际检测日统计记录修改失败，数据库update失败，修改信息：{}",each);
                        dingDingMessage.sendMessage(String.format("警告：国际检测日统计记录修改失败，数据库update失败，修改信息：%s", each));;
                    }
                }
            }
            logger.info("------代理商日统计国际检测任务执行完成，统计时间：{}------", JSON.toJSONString(statisticsDate));
		} catch (Exception e) {
			logger.info("国际检测日统计任务执行异常，info:",e);
            dingDingMessage.sendMessage(String.format("警告：国际检测日统计任务执行异常，info：%s", e));;
		}
    }
    
    /**
     * 填充客户国际定向检测消耗统计数据
     * @param statisticsDate
     */
    public void generateDailyCustIntDirectConsume(StatisticsDate statisticsDate) {
        logger.info("------客户国际定向检测消耗日统计任务开始执行，统计时间：{}------", JSON.toJSONString(statisticsDate));
        try {
        	// 获取今日所有统计
            Map<String, CustDailyInfo> reportDailyMap = selectAllIntDirectReportsByInvokeDate(statisticsDate.getStartDate().substring(0, 10).replace("-", ""),
            		statisticsDate.getEndDate().substring(0, 10).replace("-", ""));
            Map<String, CustDailyInfo> newDailyMap = new HashMap<>();
            Map<String, CustDailyInfo> updateDailyMap = new HashMap<>();
            // 获取指定时间段的message_report数量总数
            List<CustDailyInfo> intDirectCheckList = customerConsumeMapper.findDetailConsumeDataOfIntDirectCheck(statisticsDate);
            for(CustDailyInfo eachIntDirectCheck:intDirectCheckList){
                String key = getCustDirectDimension(eachIntDirectCheck);
                if(reportDailyMap.containsKey(key)){
                	updateReportDailyIntDirectConsume(reportDailyMap.get(key),eachIntDirectCheck);
                    updateDailyMap.put(key,reportDailyMap.get(key));
                }else{
                	initIntDirectCheck(eachIntDirectCheck);
                    newDailyMap.put(key,eachIntDirectCheck);
                }
            }

            if (!CollectionUtils.isEmpty(newDailyMap)) {
            	custDailyInfoMapper.batchSaveIntDirectConsume(new ArrayList(newDailyMap.values()));
            }
            //单个修改今日的记录
            if (!CollectionUtils.isEmpty(updateDailyMap)) {
                Collection<CustDailyInfo> updateList = updateDailyMap.values();
                for(CustDailyInfo each:updateList){
                    int i = custDailyInfoMapper.updateIntDirectConsume(each);
                    if(i!=1){
                    	logger.info("国际定向检测日统计记录修改失败，数据库update失败，修改信息：{}",each);
                        dingDingMessage.sendMessage(String.format("警告：国际定向检测日统计记录修改失败，数据库update失败，修改信息：%s", each));;
                    }
                }
            }
            logger.info("------代理商日统计国际定向检测任务执行完成，统计时间：{}------", JSON.toJSONString(statisticsDate));
		} catch (Exception e) {
			logger.info("国际定向检测日统计任务执行异常，info:",e);
            dingDingMessage.sendMessage(String.format("警告：国际定向检测日统计任务执行异常，info：%s", e));;
		}
    }


    private void initRealtimeCheck(CustDailyInfo eachRealtimeCheck) {
        eachRealtimeCheck.setRealtimeTotal(eachRealtimeCheck.getTotalNumber());
    }
    
    private void initInternationalCheck(CustDailyInfo eachInternationalCheck) {
    	eachInternationalCheck.setInternationalTotal(eachInternationalCheck.getTotalNumber());
    }
    
    private void initIntDirectCheck(CustDailyInfo eachIntDirectCheck) {
    	eachIntDirectCheck.setDirectTotal(eachIntDirectCheck.getTotalNumber());
    }

    private Map<String, CustDailyInfo> selectAllEmptyReportsByInvokeDate(String startDate,String endDate){
        // 查询日期下已存在的记录
        List<CustDailyInfo> list = custDailyInfoMapper.findEmptyListByDailyTask(startDate,endDate);
        if(CollectionUtils.isEmpty(list)) {
            return new HashMap<String, CustDailyInfo>();
        }
        Map<String, CustDailyInfo> collect = list.stream().collect(Collectors.toMap(k -> getCustDimension(k), v -> v));
        return collect;
    }

    private Map<String, CustDailyInfo> selectAllRealtimeReportsByInvokeDate(String startDate,String endDate){
        // 查询日期下已存在的记录
        List<CustDailyInfo> list = custDailyInfoMapper.findRealtimeListByDailyTask(startDate,endDate);
        if(CollectionUtils.isEmpty(list)) {
            return new HashMap<String, CustDailyInfo>();
        }
        Map<String, CustDailyInfo> collect = list.stream().collect(Collectors.toMap(k -> getCustDimension(k), v -> v));
        return collect;
    }

    private Map<String, CustDailyInfo> selectAllInternationalReportsByInvokeDate(String startDate,String endDate){
        // 查询日期下已存在的记录
        List<CustDailyInfo> list = custDailyInfoMapper.findInternationalListByDailyTask(startDate,endDate);
        if(CollectionUtils.isEmpty(list)) {
            return new HashMap<String, CustDailyInfo>();
        }
        Map<String, CustDailyInfo> collect = list.stream().collect(Collectors.toMap(k -> getCustDimension(k), v -> v));
        return collect;
    }
    
    private Map<String, CustDailyInfo> selectAllIntDirectReportsByInvokeDate(String startDate,String endDate){
        // 查询日期下已存在的记录
        List<CustDailyInfo> list = custDailyInfoMapper.findIntDirectListByDailyTask(startDate,endDate);
        if(CollectionUtils.isEmpty(list)) {
            return new HashMap<String, CustDailyInfo>();
        }
        Map<String, CustDailyInfo> collect = list.stream().collect(Collectors.toMap(k -> getCustDirectDimension(k), v -> v));
        return collect;
    }

    private String getCustDimension(CustDailyInfo info) {
        return String.format("%s%s%s%s", info.getDayInt(),info.getAgentId(),info.getCustomerId(),info.getStaticType());
    }
    
    private String getCustDirectDimension(CustDailyInfo info) {
        return String.format("%s%s%s%s%s", info.getDayInt(),info.getAgentId(),info.getCustomerId(),info.getStaticType(),info.getProductType());
    }

    /**
     * 根据最新查找粗来的数据对已存在空号充值或者实时检测充值进行更新
     * @param original
     * @param newData
     */
    private void updateReportEmptyConsume(CustDailyInfo original, CustDailyInfo newData){
            original.setRealNumber(original.getRealNumber()+newData.getRealNumber());
            original.setSilentNumber(original.getSilentNumber()+newData.getSilentNumber());
            original.setEmptyNumber(original.getEmptyNumber()+newData.getEmptyNumber());
            original.setRiskNumber(original.getRiskNumber()+newData.getRiskNumber());
            original.setEmptyTotal(original.getEmptyTotal()+newData.getTotalNumber());
    }

    /**
     * 根据最新查找粗来的数据对已存在空号充值或者实时检测充值进行更新
     * @param original
     * @param newData
     */
    private void updateReportDailyRealtimeConsume(CustDailyInfo original, CustDailyInfo newData){
       original.setNormalNumber(original.getNormalNumber()+newData.getNormalNumber());
       original.setRealtimeEmptyNumber(original.getRealtimeEmptyNumber()+newData.getRealtimeEmptyNumber());
       original.setOncallNumber(original.getOncallNumber()+newData.getOncallNumber());
       original.setNotOnlineNumber(original.getNotOnlineNumber()+newData.getNotOnlineNumber());
       original.setShutdownNumber(original.getShutdownNumber()+newData.getShutdownNumber());
       original.setTingjiNumber(original.getTingjiNumber()+newData.getTingjiNumber());
       original.setMnpNumber(original.getMnpNumber()+newData.getMnpNumber());
       original.setMoberrNumber(original.getMoberrNumber()+newData.getMoberrNumber());
       original.setUnknownNumber(original.getUnknownNumber()+newData.getUnknownNumber());
       original.setRealtimeTotal(original.getRealtimeTotal()+newData.getTotalNumber());
    }

    private void updateReportDailyInternationaleConsume(CustDailyInfo original, CustDailyInfo newData){
       original.setActiveNumber(original.getActiveNumber()+newData.getActiveNumber());
       original.setNoRegisterNumber(original.getNoRegisterNumber()+newData.getNoRegisterNumber());
       original.setInterUnknownNumber(original.getInterUnknownNumber()+newData.getInterUnknownNumber());
       original.setInternationalTotal(original.getInternationalTotal()+newData.getTotalNumber());
    }
    
    private void updateReportDailyIntDirectConsume(CustDailyInfo original, CustDailyInfo newData){
        original.setActiveNumber(original.getActiveNumber()+newData.getActiveNumber());
        original.setNoRegisterNumber(original.getNoRegisterNumber()+newData.getNoRegisterNumber());
        original.setDirectTotal(original.getDirectTotal()+newData.getTotalNumber());
     }

    /**
     * 获取客户账户信息，包括余额等
     *
     * @param custId
     * @return
     */
    @Override
    public R getCustInfo(Long custId) {
        CustomerBalanceInfo customerInfo = customerBalanceMapper.selectAccountInfoByCustId(custId);
        if (customerInfo == null) {
            return R.error("不存在该用户!");
        }
        
        // 获取空号余额
        String  emptyRedisBalanceString= redisClient.get(RedisKeyConstant.EMPTY_BALANCE_KEY + custId);
        Long emptyCount = StringUtils.isNotBlank(emptyRedisBalanceString)?Long.valueOf(emptyRedisBalanceString):customerInfo.getEmptyCount();
        // 获取实时余额
        String  realtimeRedisBalanceString= redisClient.get(RedisKeyConstant.REALTIME_BALANCE_KEY + custId);
        Long realtimeCount = StringUtils.isNotBlank(realtimeRedisBalanceString)?Long.valueOf(realtimeRedisBalanceString):customerInfo.getRealtimeCount();
        // 获取国际余额
        String  internationalRedisBalanceString= redisClient.get(RedisKeyConstant.INTERNATIONAL_BALANCE_KEY + custId);
        Long internationalCount = StringUtils.isNotBlank(internationalRedisBalanceString)?Long.valueOf(internationalRedisBalanceString):customerInfo.getInternationalCount();
        // 获取定向通用余额
        String  directCommonRedisBalanceString= redisClient.get(RedisKeyConstant.DIRECT_COMMON_BALANCE_KEY + custId);
        Long directCommonCount = StringUtils.isNotBlank(directCommonRedisBalanceString)?Long.valueOf(directCommonRedisBalanceString):customerInfo.getDirectCommonCount();
        // 获取line定向余额
        String  lineDirectRedisBalanceString= redisClient.get(RedisKeyConstant.LINE_DIRECT_BALANCE_KEY + custId);
        Long lineDirectCount = StringUtils.isNotBlank(lineDirectRedisBalanceString)?Long.valueOf(lineDirectRedisBalanceString):customerInfo.getLineDirectCount();
        
        customerInfo.setRefundableRealTimeNum(realtimeCount);
        customerInfo.setRefundableEmptyNum(emptyCount);
        customerInfo.setRefundableInternationalNum(internationalCount);
        customerInfo.setRefundableDirectCommonNum(directCommonCount);
        customerInfo.setRefundableLineDirectNum(lineDirectCount);
        
        List<CustomerGivenRechargeNumInfo> giveAccountList = customerRechargeMapper.getGiveAmountSumByCreUserId(customerInfo.getCustomerId());
        for(CustomerGivenRechargeNumInfo eachGivenInfo:giveAccountList){
            if(eachGivenInfo.getCategory()==0){
                customerInfo.setRefundableEmptyNum(eachGivenInfo.getGivenRechargeNum()>=emptyCount?0L:emptyCount-eachGivenInfo.getGivenRechargeNum());
            }else if(eachGivenInfo.getCategory()==1){
                customerInfo.setRefundableRealTimeNum(eachGivenInfo.getGivenRechargeNum()>=realtimeCount?0L:realtimeCount-eachGivenInfo.getGivenRechargeNum());
            }else if(eachGivenInfo.getCategory()==2){
                customerInfo.setRefundableInternationalNum(eachGivenInfo.getGivenRechargeNum()>=internationalCount?0L:internationalCount-eachGivenInfo.getGivenRechargeNum());
            }else if(eachGivenInfo.getCategory()==4){
                customerInfo.setRefundableDirectCommonNum(eachGivenInfo.getGivenRechargeNum()>=directCommonCount?0L:directCommonCount-eachGivenInfo.getGivenRechargeNum());
            }else if(eachGivenInfo.getCategory()==5){
                customerInfo.setRefundableLineDirectNum(eachGivenInfo.getGivenRechargeNum()>=lineDirectCount?0L:lineDirectCount-eachGivenInfo.getGivenRechargeNum());
            }
        }
        return R.ok(customerInfo);
    }

    public R setApiState(Long custId,Integer state){
        int i = custInfoMapper.setApiStateByCustId(custId, state);
        if(i!=1){
            return R.error("设置api接口出现问题，请联系管理员");
        }
        String appId = apiSettingMapper.queryAppIdByCustId(custId);
        refreshCacheService.apiSettingsInfoRefresh(appId);
        return R.ok();
    }

    @Override
    public R setAuthenLevel(Long agentId, Long custId, Integer authenLevel) {
        //判断代理商有无权限
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        if(!agent.getAuthenticationLimitLevel().equals(2)){
            return R.error("您没有权限下降您客户的认证等级");
        }
        if(authenLevel!=1){
            return R.error("上调等级超出您的权限范围");
        }
        int i = custInfoMapper.updateCustAuthenLevel(custId, authenLevel);

        if(i!=1){
            return R.error();
        }
        CustInfo custInfo = custInfoMapper.selectById(custId);
        refreshCacheService.reflushCustExt(custId,agentId,custInfo.getPhone());

        return R.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R addCustOfAgent(CreateCustParam param) {
        if(!param.getPwd().equals(param.getSecondPwd())){
            return R.error("您第二次输入的密码与第一次输入的不一致，请重新输入");
        }
        List<Long> custList = custInfoMapper.selectCustIdListByCustName(param.getAgentId(),param.getPhone());
        if(custList.size()>0){
            return R.error("您使用的手机号已被注册");
        }
        CustInfo custInfo = new CustInfo();
        custInfo.setName(param.getPhone());
        custInfo.setPhone(param.getPhone());
        custInfo.setRemark("oem系统添加的客户");
        custInfo.setState(0);
        custInfo.setCustomerType(9);
        custInfo.setAgentId(param.getAgentId());
        String salt = RandomStringUtils.randomAlphanumeric(20);
        custInfo.setSalt(salt);
        custInfo.setPassword(DigestUtils.sha256Hex(param.getPwd()+salt));
        Agent agent = agentMapper.selectByPrimaryKey(param.getAgentId());
        custInfo.setAuthenticationLimitLevel(agent.getAuthenticationLimitLevel());
        long custId = snowflake.nextId();
        custInfo.setId(custId);
        int cu = custInfoMapper.saveCustOfAgent(custInfo);
        if(cu!=1){
            throw new RuntimeException();
        }
        
        // 2.余额表新增记录
        CustomerBalance customerBalance = new CustomerBalance();
        customerBalance.setCustomerId(custId);
        customerBalance.setEmptyCount(0L);
        customerBalance.setRealtimeCount(0L);
        customerBalance.setEmptyRechargeNum(0L);
        customerBalance.setEmptyRechargeMoney(new BigDecimal(0));
        customerBalance.setRealtimeRechargeNum(0L);
        customerBalance.setRealtimeRechargeMoney(new BigDecimal(0));
        customerBalance.setInternationalCount(0L);
        customerBalance.setInternationalRechargeNum(0L);
        customerBalance.setInternationalRechargeMoney(new BigDecimal(0));
        customerBalance.setDirectCommonCount(0L);
        customerBalance.setDirectCommonRechargeNum(0L);
        customerBalance.setDirectCommonRechargeMoney(new BigDecimal(0));
        customerBalance.setLineDirectCount(0L);
        customerBalance.setLineDirectRechargeNum(0L);
        customerBalance.setLineDirectRechargeMoney(new BigDecimal(0));
        
        customerBalance.setVersion(0l);
        int i = customerBalanceMapper.saveOne(customerBalance);
        if (i!=1) {
            throw new RuntimeException("保存客户账号出错");
        }
        custInfo.setId(custId);
        saveApiSettings(custInfo);
        return R.ok();
    }

    /**
     * 生成appId和appKey
     * @date 2021/11/15
     * @param newCustomer
     * @return void
     */
    public void saveApiSettings(CustInfo newCustomer) {
        // appId和appKey做幂等
        ApiSettings apiSettings = this.checkAppIdAppKey(System.currentTimeMillis());
        if (apiSettings == null) {
            throw new RuntimeException("生成appId以及appkey时失败");
        }
        // 新增记录
        apiSettings.setCustomerId(newCustomer.getId());
        apiSettings.setState(0);
        apiSettings.setVersion(0);
        int re = apiSettingMapper.saveApiSetting(apiSettings);
        if (re!=1) {
            throw new RuntimeException("新增客户API设置时失败");
        }
    }

    /**
     * 校验appId和appKey的幂等性
     * @date 2021/11/10
     * @param startTime 开始时间
     * @return void
     */
    public ApiSettings checkAppIdAppKey(Long startTime) {
        ApiSettings apiSettings = new ApiSettings();
        // 递归执行时间不能过长
        long endTime = System.currentTimeMillis();
        if (endTime-startTime>3000) {
            return null;
        }

        // 校验appId
        String appId = RandomUtil.randomString(8);
        String appKey = RandomUtil.randomString(8);
        apiSettings.setAppId(appId);
        ApiSettings apiSettingsAppId = apiSettingMapper.findByAppId(appId);
        if (apiSettingsAppId != null) {
            return checkAppIdAppKey(startTime);
        }

        // 校验appKey
        apiSettings.setAppId(null);
        apiSettings.setAppKey(appKey);
        ApiSettings apiSettingsAppKey = apiSettingMapper.findByAppKey(appKey);
        if (apiSettingsAppKey != null) {
            return checkAppIdAppKey(startTime);
        }
        apiSettings.setAppId(appId);
        return apiSettings;
    }

}
