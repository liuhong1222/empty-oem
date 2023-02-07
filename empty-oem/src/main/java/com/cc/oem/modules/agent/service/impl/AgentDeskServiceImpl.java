package com.cc.oem.modules.agent.service.impl;

import com.cc.oem.common.utils.R;
import com.cc.oem.common.validator.Assert;
import com.cc.oem.modules.agent.dao.*;
import com.cc.oem.modules.agent.entity.*;
import com.cc.oem.modules.agent.model.data.records.DeskConsumeData;
import com.cc.oem.modules.agent.model.data.records.StaticsAgentAccountData;
import com.cc.oem.modules.agent.model.data.records.WaitTodoData;
import com.cc.oem.modules.agent.service.AgentDeskService;
import com.cc.oem.modules.agent.service.AgentLevelService;
import com.cc.oem.modules.agent.service.RefreshCacheService;
import com.cc.oem.modules.agent.service.records.CustConsumeService;
import com.cc.oem.modules.job.dao.CustDailyInfoMapper;
import com.cc.oem.modules.sys.dao.SysUserDao;
import com.cc.oem.modules.sys.entity.SysUserEntity;
import com.cc.oem.modules.sys.service.SysUserService;
import com.google.common.collect.Maps;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName CreUserServiceImpl
 * author   zhangx
 * date     2018/8/8 11:07
 * description
 */
@Service
public class AgentDeskServiceImpl implements AgentDeskService {

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    AgentLevelMapper levelMapper;
    @Autowired
    AgentAccountMapper accountMapper;
    @Autowired
    AgentMapper agentMapper;
    @Autowired
    CustomerRechargeMapper customerRechargeMapper;

    @Autowired
    SysUserService sysUserService;
    @Autowired
    AgentRechargeMapper agentRechargeMapper;
    @Autowired
    CustConsumeService custConsumeService;
    @Autowired
    CustInfoMapper custInfoMapper;
    @Autowired
    AgentLevelService agentLevelService;
    @Autowired
    AgentSetMapper agentSetMapper;
    @Autowired
    private CustDailyInfoMapper custDailyInfoMapper;
    @Autowired
    private RefreshCacheService refreshCacheService;

    @Override
    public R getAgentDeskInfo(Long agentId, Long sysUserId,Integer category) {
        Assert.isNull(sysUserId, "未登录");
        Assert.isNull(agentId, "不是代理商");
        SysUserEntity sysUserEntity = sysUserDao.selectById(sysUserId);
        Map map = Maps.newHashMap();
        map.put("mobile", sysUserEntity.getPhone());
        map.put("email", sysUserEntity.getEmail());
        map.put("domain",agentSetMapper.selectDomainByAgentId(agentId));
        map.put("custNums",custInfoMapper.countCustNumOfAgent(agentId));
        
        Agent agent = agentMapper.selectAgentBySysUserId(sysUserId);
        agent.setWarningsNumber(agent.getWarningsNumber()/10000);
        agent.setRealWarningsNumber(agent.getRealWarningsNumber()==null?0L:(agent.getRealWarningsNumber()/10000));
        agent.setInternationalWarningsNumber(agent.getInternationalWarningsNumber()==null?0L:(agent.getInternationalWarningsNumber()/10000));
        agent.setDirectCommonWarningsNumber(agent.getDirectCommonWarningsNumber()==null?0L:(agent.getDirectCommonWarningsNumber()/10000));
        agent.setLineDirectWarningsNumber(agent.getLineDirectWarningsNumber()==null?0L:(agent.getLineDirectWarningsNumber()/10000));
        map.put("agentInfo",agent);
        
        StaticsAgentAccountData staticsAgentAccountData = accountMapper.stasticsDataOfAgentNew(agentId);
        map.put("emptyBalance", staticsAgentAccountData==null?0:BigDecimal.valueOf(staticsAgentAccountData.getTotalEmptyBalance()).divide(new BigDecimal(10000)));
        map.put("realtimeBalance",staticsAgentAccountData==null?0:BigDecimal.valueOf(staticsAgentAccountData.getTotalRealtimeBalance()).divide(new BigDecimal(10000)));
        map.put("internationalBalance",staticsAgentAccountData==null?0:BigDecimal.valueOf(staticsAgentAccountData.getTotalInternationalBalance()).divide(new BigDecimal(10000)));
        map.put("directCommonBalance",staticsAgentAccountData==null?0:BigDecimal.valueOf(staticsAgentAccountData.getTotalDirectCommonBalance()).divide(new BigDecimal(10000)));
        map.put("lineDirectBalance",staticsAgentAccountData==null?0:BigDecimal.valueOf(staticsAgentAccountData.getTotalLineDirectBalance()).divide(new BigDecimal(10000)));
        
        if(null == category || category == 0) {
        	map.put("custRechargeSum",staticsAgentAccountData==null?0:staticsAgentAccountData.getCustRechargeMoney());
            map.put("custRechargeNumberSum", staticsAgentAccountData==null?0:staticsAgentAccountData.getCustRechargeNum());
            
            List<DeskConsumeData> consumeList = custDailyInfoMapper.getDeskConsumeByEmpty(agentId);
            map.put("consumeTrend", consumeList);
            map.put("consume",CollectionUtils.isEmpty(consumeList) ? 0L : consumeList.stream().collect(Collectors.summingLong(DeskConsumeData::getTotalConsume)));
        }else if(category == 1) {
        	map.put("custRechargeSum",staticsAgentAccountData==null?0:staticsAgentAccountData.getCustRealtimeRechargeMoney());
            map.put("custRechargeNumberSum", staticsAgentAccountData==null?0:staticsAgentAccountData.getCustRealtimeRechargeNum());
            
            List<DeskConsumeData> consumeList = custDailyInfoMapper.getDeskConsumeByRealtime(agentId);
            map.put("consumeTrend", consumeList);
            map.put("consume",CollectionUtils.isEmpty(consumeList) ? 0L : consumeList.stream().collect(Collectors.summingLong(DeskConsumeData::getTotalConsume)));
        }else if(category == 2) {
        	map.put("custRechargeSum",staticsAgentAccountData==null?0:staticsAgentAccountData.getCustInternationalRechargeMoney());
            map.put("custRechargeNumberSum", staticsAgentAccountData==null?0:staticsAgentAccountData.getCustInternationalRechargeNum());
            
            List<DeskConsumeData> consumeList = custDailyInfoMapper.getDeskConsumeByInternational(agentId);
            map.put("consumeTrend", consumeList);
            map.put("consume",CollectionUtils.isEmpty(consumeList) ? 0L : consumeList.stream().collect(Collectors.summingLong(DeskConsumeData::getTotalConsume)));
        }else if(category == 4) {
        	map.put("custRechargeSum",staticsAgentAccountData==null?0:staticsAgentAccountData.getCustDirectCommonRechargeMoney());
            map.put("custRechargeNumberSum", staticsAgentAccountData==null?0:staticsAgentAccountData.getCustDirectCommonRechargeNum());
            
            List<DeskConsumeData> consumeList = custDailyInfoMapper.getDeskConsumeByDirectCommon(agentId);
            map.put("consumeTrend", consumeList);
            map.put("consume",CollectionUtils.isEmpty(consumeList) ? 0L : consumeList.stream().collect(Collectors.summingLong(DeskConsumeData::getTotalConsume)));
        }else if(category == 5) {
        	map.put("custRechargeSum",staticsAgentAccountData==null?0:staticsAgentAccountData.getCustLineDirectRechargeMoney());
            map.put("custRechargeNumberSum", staticsAgentAccountData==null?0:staticsAgentAccountData.getCustLineDirectRechargeNum());
            
            List<DeskConsumeData> consumeList = custDailyInfoMapper.getDeskConsumeByLineDirect(agentId);
            map.put("consumeTrend", consumeList);
            map.put("consume",CollectionUtils.isEmpty(consumeList) ? 0L : consumeList.stream().collect(Collectors.summingLong(DeskConsumeData::getTotalConsume)));
        }

        map.put("waitToDoNum",staticsAgentAccountData==null?0:computeToDoNum(agentId));
        return R.ok(map);

    }

    /**
     * 管理员统计逻辑为统计所有代理商的充值以及所有代理商客户的消耗都算作管理员的消耗
     * @return
     */
    @Override
    public R getAdminDeskInfo(Integer category) {
        Long sysUserId = sysUserService.getSysUserId();
        if (!sysUserService.judgeIfAdmin(sysUserId)) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "不是管理员角色");
        }
        
        SysUserEntity sysUserEntity = sysUserDao.selectById(sysUserId);
        Map map = Maps.newHashMap();
        map.put("mobile", sysUserEntity.getPhone());
        map.put("email", sysUserEntity.getEmail());
        StaticsAgentAccountData staticsAgentAccountData = accountMapper.stasticsDataNew();
        Map<String, Integer> custNums = agentMapper.countAgent();
        
        if(null == category || category == 0) {
        	map.put("agentNums",custNums.get("emptyAgentNums"));
        	map.put("rechargeSum", staticsAgentAccountData.getTotalEmptyRechargeMoney());
            map.put("rechargeNumberSum", staticsAgentAccountData.getTotalEmptyRechargeNumber());
            
            List<DeskConsumeData> consumeList = custDailyInfoMapper.getDeskConsumeByEmpty(null);
            map.put("consumeTrend", consumeList);
            map.put("consume",CollectionUtils.isEmpty(consumeList) ? 0L : consumeList.stream().collect(Collectors.summingLong(DeskConsumeData::getTotalConsume)));
        }else if(category == 1) {
        	map.put("agentNums",custNums.get("realtimeAgentNums"));
        	map.put("rechargeSum", staticsAgentAccountData.getTotalRealtimeRechargeMoney());
            map.put("rechargeNumberSum", staticsAgentAccountData.getTotalRealtimeRechargeNumber());
            
            List<DeskConsumeData> consumeList = custDailyInfoMapper.getDeskConsumeByRealtime(null);
            map.put("consumeTrend", consumeList);
            map.put("consume",CollectionUtils.isEmpty(consumeList) ? 0L : consumeList.stream().collect(Collectors.summingLong(DeskConsumeData::getTotalConsume)));
        }else if(category == 2) {
        	map.put("agentNums", custNums.get("internationalAgentNums"));
        	map.put("rechargeSum", staticsAgentAccountData.getTotalInternationalRechargeMoney());
            map.put("rechargeNumberSum", staticsAgentAccountData.getTotalInternationalRechargeNumber());
            
            List<DeskConsumeData> consumeList = custDailyInfoMapper.getDeskConsumeByInternational(null);
            map.put("consumeTrend", consumeList);
            map.put("consume",CollectionUtils.isEmpty(consumeList) ? 0L : consumeList.stream().collect(Collectors.summingLong(DeskConsumeData::getTotalConsume)));
        }else if(category == 4) {
        	map.put("agentNums", custNums.get("directCommonAgentNums"));
        	map.put("rechargeSum", staticsAgentAccountData.getTotalDirectCommonRechargeMoney());
            map.put("rechargeNumberSum", staticsAgentAccountData.getTotalDirectCommonRechargeNumber());
            
            List<DeskConsumeData> consumeList = custDailyInfoMapper.getDeskConsumeByDirectCommon(null);
            map.put("consumeTrend", consumeList);
            map.put("consume",CollectionUtils.isEmpty(consumeList) ? 0L : consumeList.stream().collect(Collectors.summingLong(DeskConsumeData::getTotalConsume)));
        }else if(category == 5) {
        	map.put("agentNums", custNums.get("lineDirectAgentNums"));
        	map.put("rechargeSum", staticsAgentAccountData.getTotalLineDirectRechargeMoney());
            map.put("rechargeNumberSum", staticsAgentAccountData.getTotalLineDirectRechargeNumber());
            
            List<DeskConsumeData> consumeList = custDailyInfoMapper.getDeskConsumeByLineDirect(null);
            map.put("consumeTrend", consumeList);
            map.put("consume",CollectionUtils.isEmpty(consumeList) ? 0L : consumeList.stream().collect(Collectors.summingLong(DeskConsumeData::getTotalConsume)));
        }
        
        //统计今日的实时消耗情况，与历史记录总数进行累加
        map.put("waitToDoNum",computeToDoNum(null));

        return R.ok(map);
    }

    /**
     * 计算待办数量
     *
     * @return
     */
    public Integer computeToDoNum(Long agentId) {
        List<WaitTodoData> waitTodoDataList = new ArrayList<>();
        if(agentId==null){
            waitTodoDataList = agentMapper.computAdminToDoNum();
        }else{
            waitTodoDataList = agentMapper.computAgentToDoNum(agentId);
        }
        AtomicReference<Integer> totalNum = new AtomicReference<>(0);
        waitTodoDataList.stream().forEach(k-> totalNum.updateAndGet(v -> v + k.getTotalNum()));
        return totalNum.get();
    }

    @Override
    public R updateWarnNumber(Long agentId, Long warnNumber,Integer category) {
        Agent agentAccount = agentMapper.selectByPrimaryKey(agentId);
        if (agentAccount == null) {
            return R.error();
        }
        
        Long agentWarningNumber = getWarnningNumber(category, agentAccount);
        if(warnNumber==agentWarningNumber){
            return R.ok();
        }
        
        Agent updateAgent = new Agent();
        updateAgent.setId(agentId);
        
        if(category == 0) {
        	updateAgent.setWarningsNumber(warnNumber);
        }else if(category == 1) {
        	updateAgent.setRealWarningsNumber(warnNumber);
        }else if(category == 2) {
        	updateAgent.setInternationalWarningsNumber(warnNumber);
        }else if(category == 4) {
        	updateAgent.setDirectCommonWarningsNumber(warnNumber);
        }else if(category == 5) {
        	updateAgent.setLineDirectWarningsNumber(warnNumber);
        }
        
        agentMapper.updateByPrimaryKeySelective(updateAgent);
        refreshCacheService.agentInfoRefresh(agentId);
        return R.ok();
    }

    @Override
    public R updateMobile(Long sysUserId, String mobile) {
        sysUserDao.updateMobileByUserId(sysUserId, mobile);
        return R.ok();
    }

    @Override
    public R findMobile(Long sysUserId) {
        if (sysUserId == null) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "未登录");
        }
        String mobile = sysUserDao.selectById(sysUserId).getPhone();
        Map map = Maps.newHashMap();
        map.put("mobile", mobile);
        return R.ok(map);
    }

    @Override
    public R updateMail(Long sysUserId, String mail) {
        sysUserDao.updateEmailByUserId(sysUserId, mail);
        return R.ok();
    }

    @Override
    public R updateAutoPresentCfg(Long agentId, Integer autoPresentCfg) {
        if (agentId == null || autoPresentCfg == null) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "参数有误!");
        }
        if (autoPresentCfg.intValue() != 0 && autoPresentCfg.intValue() != 1) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "参数有误!");
        }

        int count = agentMapper.updateRegisterGift(agentId,autoPresentCfg);
        if (count != 1) {
            throw new RuntimeException("更新出错，请重试");
        }
        refreshCacheService.agentInfoRefresh(agentId);
        if(autoPresentCfg.intValue()==0){
            return R.ok("自动赠送关闭成功");
        }
        if(autoPresentCfg.intValue()==1){
            return R.ok("自动赠送开启成功");
        }
        return R.ok();
    }

    @Override
    public R setAuthenLevel(Long agentId ,Integer authenLevel) {
        int i = agentMapper.updateAuthenLevel(agentId, authenLevel);
        if(i!=1){
            return R.error();
        }

        custInfoMapper.updateCustAuthenLevelOfAgent(agentId,authenLevel);
        List<CustInfo> list = custInfoMapper.selectCustInfoListByAgentId(agentId);
        refreashCustInfoByList(list,agentId);
        refreshCacheService.agentInfoRefresh(agentId);
        return R.ok();
    }

    @Async
    public void refreashCustInfoByList( List<CustInfo> list,Long agentId){
        for(CustInfo each:list){
            refreshCacheService.reflushCustExt(each.getId(),agentId,each.getPhone());
        }

    }
    
    private Long getWarnningNumber(Integer category,Agent agentAccount) {
    	switch (category) {
		case 0:return agentAccount.getWarningsNumber();
		case 1:return agentAccount.getRealWarningsNumber();
		case 2:return agentAccount.getInternationalWarningsNumber();
		case 4:return agentAccount.getDirectCommonWarningsNumber();
		case 5:return agentAccount.getLineDirectWarningsNumber();

		default:return 0L;
		}
    }
}
