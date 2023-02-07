package com.cc.oem.modules.agent.service.impl;

import com.cc.oem.common.redis.RedisClient;
import com.cc.oem.common.utils.R;
import com.cc.oem.common.utils.ShiroUtils;
import com.cc.oem.modules.agent.constants.RedisKeyConstant;
import com.cc.oem.modules.agent.dao.*;
import com.cc.oem.modules.agent.dao.agentSettings.GoodsMapper;
import com.cc.oem.modules.agent.dao.finance.CustomerRefundMapper;
import com.cc.oem.modules.agent.entity.Agent;
import com.cc.oem.modules.agent.entity.AgentAccount;
import com.cc.oem.modules.agent.entity.CustomerBalance;
import com.cc.oem.modules.agent.entity.agentSettings.Goods;
import com.cc.oem.modules.agent.entity.finance.CustomerRecharge;
import com.cc.oem.modules.agent.entity.finance.CustomerRefund;
import com.cc.oem.modules.agent.model.data.customer.CustomerBalanceInfo;
import com.cc.oem.modules.agent.model.param.CustRechargeParam;
import com.cc.oem.modules.agent.model.param.CustRefundParam;
import com.cc.oem.modules.agent.service.CustOrderService;
import com.cc.oem.modules.agent.service.RefreshCacheService;
import com.cc.oem.modules.config.utils.Snowflake;
import com.cc.oem.modules.sys.service.SysSendMsgService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName OrderServiceImpl
 * author   zhangx
 * date     2018/8/30 13:24
 * description
 */
@Service
public class CustOrderServiceImpl implements CustOrderService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CustomerRechargeMapper customerRechargeMapper;
    @Autowired
    CustomerBalanceMapper userAccountMapper;
    @Autowired
    CustomerBalanceMapper customerBalanceMapper;
    @Autowired
    AgentRechargeMapper agentRechargeMapper;
    @Autowired
    AgentCreUserMapper agentCreUserMapper;
    @Autowired
    AgentAccountMapper agentAccountMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    private CustomerRefundMapper customerRefundMapper;

    @Autowired
    RefreshCacheService refreshCacheService;
    @Autowired
    private Snowflake snowflake;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private AgentMapper agentMapper;

    @Autowired
    private SysSendMsgService sysSendMsgService;



    /**
     * 获取通过的套餐
     * @param agentId
     * @return
     */
    @Override
    public R getPackageInfo(Long agentId,Integer category,Long custId) {
        List<Goods> list = goodsMapper.getGoodsPageByAgentId(agentId,category);
        Map map = new HashMap();
        map.put("goodsList",list);
        CustomerBalanceInfo custBa = customerBalanceMapper.selectAccountInfoByCustId(custId);
        map.put("emptyBalance",custBa.getEmptyCount());
        map.put("realtimeBalance",custBa.getRealtimeCount());
        map.put("internationalBalance",custBa.getInternationalCount());
        map.put("directCommonBalance",custBa.getDirectCommonCount());
        map.put("lineDirectBalance",custBa.getLineDirectCount());
        return R.ok(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R recharge(CustRechargeParam param,Long agentId) {
        if (agentId == null) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "参数异常,非代理商不能操作!");
        }
        if (param.getCustId() == null || param.getGoodsId() == null || param.getNumber() == null || param.getAmount() == null) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "参数异常");
        }
        Goods goods = goodsMapper.getGoodsById(param.getGoodsId());
        //当套餐为自定义套餐，需要校验最小充值条数
        if (goods.getType()==1) {
            if (param.getNumber()<0) {
                return R.error(HttpStatus.SC_BAD_REQUEST, "自定义套餐最少充值一条");
            }
        }
        AgentAccount agentAccount = agentAccountMapper.queryOneByAgentId(agentId);
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        //获取代理商余额
        if (param.getCategory()==0) {
            if(param.getNumber() > agentAccount.getEmptyBalance()){
                return R.error(HttpStatus.SC_BAD_REQUEST, "充值空号条数超过代理商所有的权限，请联系管理员");
            }
            long closingBalance = agentAccount.getEmptyBalance() - param.getNumber();
            if(closingBalance<=agent.getWarningsNumber()){
                String msg = "您的客户正在进行一次充值总条数为"+param.getNumber()+"条的空号充值操作，充值后您的余额将低于空号预警值";
                sysSendMsgService.SendNotifyMsg(agent.getLinkmanPhone(),msg);
            }
        }
        if (param.getCategory()==1) {
            if(param.getNumber()>agentAccount.getRealtimeBalance()){
                return R.error(HttpStatus.SC_BAD_REQUEST, "充值实时条数超过代理商所有的权限，请联系管理员");
            }
            long closingBalance = agentAccount.getRealtimeBalance() - param.getNumber();
            if(closingBalance<=agent.getRealWarningsNumber()){
                String msg = "您的客户正在进行一次充值总条数为"+param.getNumber()+"条的实号充值操作，充值后您的余额将低于实号预警值";
                sysSendMsgService.SendNotifyMsg(agent.getLinkmanPhone(),msg);
            }
        }
        if (param.getCategory()==2) {
            if(param.getNumber()>agentAccount.getInternationalBalance()){
                return R.error(HttpStatus.SC_BAD_REQUEST, "充值国际条数超过代理商所有的权限，请联系管理员");
            }
            long closingBalance = agentAccount.getInternationalBalance() - param.getNumber();
            if(closingBalance<=agent.getInternationalWarningsNumber()){
                String msg = "您的客户正在进行一次充值总条数为"+param.getNumber()+"条的国际充值操作，充值后您的余额将低于国际预警值";
                sysSendMsgService.SendNotifyMsg(agent.getLinkmanPhone(),msg);
            }
        }
        
        if (param.getCategory()==4) {
            if(param.getNumber()>agentAccount.getDirectCommonBalance()){
                return R.error(HttpStatus.SC_BAD_REQUEST, "充值国际定向通用条数超过代理商所有的权限，请联系管理员");
            }
            long closingBalance = agentAccount.getDirectCommonBalance() - param.getNumber();
            if(closingBalance<=agent.getDirectCommonWarningsNumber()){
                String msg = "您的客户正在进行一次充值总条数为"+param.getNumber()+"条的国际定向通用充值操作，充值后您的余额将低于国际定向通用预警值";
                sysSendMsgService.SendNotifyMsg(agent.getLinkmanPhone(),msg);
            }
        }
        
        if (param.getCategory()==5) {
            if(param.getNumber()>agentAccount.getLineDirectBalance()){
                return R.error(HttpStatus.SC_BAD_REQUEST, "充值国际line定向条数超过代理商所有的权限，请联系管理员");
            }
            long closingBalance = agentAccount.getLineDirectBalance() - param.getNumber();
            if(closingBalance<=agent.getLineDirectWarningsNumber()){
                String msg = "您的客户正在进行一次充值总条数为"+param.getNumber()+"条的国际line定向充值操作，充值后您的余额将低于国际line定向预警值";
                sysSendMsgService.SendNotifyMsg(agent.getLinkmanPhone(),msg);
            }
        }

        return commonRechargeOperation(goods,param,agentId);
    }

    public R commonRechargeOperation(Goods goods,CustRechargeParam param,Long agentId){
        CustomerBalanceInfo customerBalanceInfo = customerBalanceMapper.selectAccountInfoByCustId(param.getCustId());
        CustomerRecharge order = new CustomerRecharge();
        
        //获取产品支付套餐信息
        order.setGoodsName(goods.getName());
        order.setName(customerBalanceInfo.getCustName());
        order.setPhone(customerBalanceInfo.getCustPhone());
        Long openBalance = getOpenBalance(param.getCategory(), customerBalanceInfo);
        order.setOpeningBalance(openBalance);
        order.setClosingBalance(openBalance+param.getNumber());
        order.setAgentId(agentId);
        order.setCategory(param.getCategory());
        order.setOrderNo("OEMCZ_" + System.currentTimeMillis());
        order.setCustomerId(param.getCustId());
        order.setPrice(param.getPrice().toString());
        order.setRechargeNumber(param.getNumber());
        order.setPaymentAmount(param.getAmount().toString());
        order.setPayType(param.getPayType());
        order.setRemark(param.getRemark());
        order.setVersion(0);
        order.setId(snowflake.nextId());
        order.setCreatorName(ShiroUtils.getUserEntity().getUsername());
        int count = customerRechargeMapper.save(order);// useGeneratedKeys="true" keyProperty="logId"
        if (count != 1) {
            throw new RuntimeException("insertCount1!=1");
        }
        //成功需要1：更改客户账户余额 2：更改代理商账户余额，
        CustomerBalance modifyEntity = new CustomerBalance();
        modifyEntity.setCustomerId(param.getCustId());
        AgentAccount ac = new AgentAccount();
        ac.setAgentId(agentId);
        if(param.getCategory()==0){
            modifyEntity.setEmptyCount(1L*param.getNumber());
            modifyEntity.setEmptyRechargeNum(1L*param.getNumber());
            modifyEntity.setEmptyRechargeMoney(param.getAmount());
            ac.setEmptyBalance(-1L*param.getNumber());
            ac.setCustRechargeNum(1L*param.getNumber());
            ac.setCustRechargeMoney(param.getAmount());
        }else if(param.getCategory()==1){
            modifyEntity.setRealtimeCount(1L*param.getNumber());
            ac.setRealtimeBalance(-1L*param.getNumber());
            modifyEntity.setRealtimeRechargeNum(1L*param.getNumber());
            modifyEntity.setRealtimeRechargeMoney(param.getAmount());
            ac.setCustRealtimeRechargeNum(1L*param.getNumber());
            ac.setCustRealtimeRechargeMoney(param.getAmount());
        }else if(param.getCategory()==2){
            modifyEntity.setInternationalCount(1L*param.getNumber());
            ac.setInternationalBalance(-1L*param.getNumber());
            modifyEntity.setInternationalRechargeNum(1L*param.getNumber());
            modifyEntity.setInternationalRechargeMoney(param.getAmount());
            ac.setCustInternationalRechargeNum(1L*param.getNumber());
            ac.setCustInternationalRechargeMoney(param.getAmount());
        }else if(param.getCategory()==4){
            modifyEntity.setDirectCommonCount(1L*param.getNumber());
            ac.setDirectCommonBalance(-1L*param.getNumber());
            modifyEntity.setDirectCommonRechargeNum(1L*param.getNumber());
            modifyEntity.setDirectCommonRechargeMoney(param.getAmount());
            ac.setCustDirectCommonRechargeNum(1L*param.getNumber());
            ac.setCustDirectCommonRechargeMoney(param.getAmount());
        }else if(param.getCategory()==5){
            modifyEntity.setLineDirectCount(1L*param.getNumber());
            ac.setLineDirectBalance(-1L*param.getNumber());
            modifyEntity.setLineDirectRechargeNum(1L*param.getNumber());
            modifyEntity.setLineDirectRechargeMoney(param.getAmount());
            ac.setCustLineDirectRechargeNum(1L*param.getNumber());
            ac.setCustLineDirectRechargeMoney(param.getAmount());
        }
        int i = customerBalanceMapper.updateCustAccountByCustId(modifyEntity);
        if (count != 1) {
            throw new RuntimeException("客户充值-更改客户账户余额表示出错，错误信息");
        }
        addRedisBalace(agentId,order.getRechargeNumber(),order.getCustomerId(),order.getCategory());
        int j = agentAccountMapper.updateAgentAccountByAgentId(ac);
        if (count != 1) {
            throw new RuntimeException("客户充值-更改代理商账户余额表示出错，错误信息");
        }
        refreshCacheService.customerInfoRefresh(param.getCustId());
        refreshCacheService.agentInfoRefresh(agentId);

        return R.ok();
    }


    public R commonPresentRechargeOperation(Goods goods,CustRechargeParam param,Long agentId){
        CustomerBalanceInfo customerBalanceInfo = customerBalanceMapper.selectAccountInfoByCustId(param.getCustId());
        CustomerRecharge order = new CustomerRecharge();
//        order.setCustId(param.getCustId());
        //获取产品支付套餐信息
        order.setGoodsName(goods.getName());
        order.setName(customerBalanceInfo.getCustName());
        order.setPhone(customerBalanceInfo.getCustPhone());
        Long openBalance = param.getCategory() == 0 ? customerBalanceInfo.getEmptyCount() : customerBalanceInfo.getRealtimeCount();
        order.setOpeningBalance(openBalance);
        order.setClosingBalance(openBalance+param.getNumber());
        order.setAgentId(agentId);
        order.setCategory(param.getCategory());
        order.setOrderNo("OEMCZ_" + System.currentTimeMillis());
        order.setCustomerId(param.getCustId());
        order.setPrice(param.getPrice().toString());
        order.setRechargeNumber(param.getNumber());
        order.setPaymentAmount(param.getAmount().toString());
        order.setPayType(param.getPayType());
        order.setRemark(param.getRemark());
        order.setVersion(0);
        order.setId(snowflake.nextId());
        order.setCreatorName(ShiroUtils.getUserEntity().getUsername());
        int count = customerRechargeMapper.save(order);// useGeneratedKeys="true" keyProperty="logId"
        if (count != 1) {
            throw new RuntimeException("insertCount1!=1");
        }
        //成功需要1：更改客户账户余额 2：更改代理商账户余额，
        CustomerBalance modifyEntity = new CustomerBalance();
        modifyEntity.setCustomerId(param.getCustId());
        if(param.getCategory()==0){
            modifyEntity.setEmptyCount(1L*param.getNumber());
            modifyEntity.setEmptyRechargeNum(1L*param.getNumber());
            modifyEntity.setEmptyRechargeMoney(param.getAmount());
        }else{
            modifyEntity.setRealtimeCount(1L*param.getNumber());
            modifyEntity.setRealtimeRechargeNum(1L*param.getNumber());
            modifyEntity.setRealtimeRechargeMoney(param.getAmount());
        }
        int i = customerBalanceMapper.updateCustAccountByCustId(modifyEntity);
        if (count != 1) {
            throw new RuntimeException("客户充值-更改客户账户余额表示出错，错误信息");
        }
        addRedisBalace(agentId,order.getRechargeNumber(),order.getCustomerId(),order.getCategory());
        refreshCacheService.customerInfoRefresh(param.getCustId());
        refreshCacheService.agentInfoRefresh(agentId);
        return R.ok();
    }


    /**
     * 退款，减少客户增加供应商
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public R refunds(CustRefundParam param,Long agentId) {
        if (agentId == null) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "参数异常,非代理商不能操作!");
        }
        if (param.getCustId() == null || param.getNumber() == null || param.getAmount() == null) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "参数异常");
        }
        if (param.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "退款金额不能小于0");
        }
        CustomerBalanceInfo customerBalanceInfo = customerBalanceMapper.selectAccountInfoByCustId(param.getCustId());
        if (customerBalanceInfo == null) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "参数异常");
        }
        CustomerRefund order = new CustomerRefund();
//        order.setCustId(param.getCustId());
        //获取产品支付套餐信息
        order.setAgentId(agentId);
        order.setCategory(param.getCategory());
        order.setOrderNo("OEMTK_" + System.currentTimeMillis());
        order.setCustomerId(param.getCustId());
        order.setPrice(param.getPrice().toString());
        order.setRefundNumber(param.getNumber());
        order.setRefundAmount(param.getAmount().toString());
        order.setRefundType(param.getRefundType());
        order.setVersion(0);
        order.setRemark(param.getRemark());
        order.setOpeningBalance(param.getOpeningBalance());
        order.setClosingBalance(param.getOpeningBalance()-param.getNumber());
        order.setId(snowflake.nextId());
        order.setCreatorName(ShiroUtils.getUserEntity().getUsername());
        int count = customerRefundMapper.save(order);// useGeneratedKeys="true" keyProperty="logId"
        if (count != 1) {
            throw new RuntimeException("insertCount1!=1");
        }
        //成功需要1：更改客户账户余额 2：更改代理商账户余额，
        CustomerBalance modifyEntity = new CustomerBalance();
        modifyEntity.setCustomerId(param.getCustId());
        AgentAccount ac = new AgentAccount();
        ac.setAgentId(agentId);
        if(param.getCategory()==0){
            //代理商余额增加，客户余额减少
            modifyEntity.setEmptyCount(-1L*param.getNumber());
            ac.setEmptyBalance(1L*param.getNumber());
        }else if(param.getCategory()==1){
            modifyEntity.setRealtimeCount(-1L*param.getNumber());
            ac.setRealtimeBalance(1L*param.getNumber());
        }else if(param.getCategory()==2){
            modifyEntity.setInternationalCount(-1L*param.getNumber());
            ac.setInternationalBalance(1L*param.getNumber());
        }else if(param.getCategory()==4){
            modifyEntity.setDirectCommonCount(-1L*param.getNumber());
            ac.setDirectCommonBalance(1L*param.getNumber());
        }else if(param.getCategory()==5){
            modifyEntity.setLineDirectCount(-1L*param.getNumber());
            ac.setLineDirectBalance(1L*param.getNumber());
        }
        int i = customerBalanceMapper.updateCustAccountByCustId(modifyEntity);
        decrRedisBalace(agentId,order.getRefundNumber(),order.getCustomerId(),order.getCategory());
        if (count != 1) {
            throw new RuntimeException("客户退款-更改客户账户余额表示出错，错误信息");
        }
        int j = agentAccountMapper.updateAgentAccountByAgentId(ac);
        if (count != 1) {
            throw new RuntimeException("客户退款-更改代理商账户余额表示出错，错误信息");
        }
        refreshCacheService.customerInfoRefresh(param.getCustId());
        refreshCacheService.agentInfoRefresh(agentId);
        return R.ok();
    }

    /**
     * 如果redis余额存在，就更新。
     * @date 2021/11/15
     * @return void
     */
    private void addRedisBalace(Long agentId,Integer number,Long custId,Integer category) {
        Integer count = number;
        if (category.equals(0)) {
            // 空号检测
            String emptyKey = RedisKeyConstant.EMPTY_BALANCE_KEY + custId;
            String emptyStr = redisClient.get(emptyKey);
            if (!StringUtils.isBlank(emptyStr)) {
                Long nowBalance = redisClient.incrBy(emptyKey, count);
                logger.info("代理商给客户{}充值空号产品.redis余额-空号余额-修改成功。充值前余额：{}，充值后余额：{}",agentId, emptyStr, nowBalance);
            }

        } else if (category.equals(1)){
            // 实时检测
            String realtimeKey = RedisKeyConstant.REALTIME_BALANCE_KEY + custId;
            String realtimeStr = redisClient.get(realtimeKey);
            if (!StringUtils.isBlank(realtimeStr)) {
                Long nowBalance = redisClient.incrBy(realtimeKey, count);
                logger.info("代理商给客户{}充值实时产品，5.redis余额-实时检测余额-修改成功。充值前余额：{}，充值后余额：{}",agentId, realtimeStr, nowBalance);
            }

        }else if (category.equals(2)){
            // 国际检测
            String internationalKey = RedisKeyConstant.INTERNATIONAL_BALANCE_KEY + custId;
            String internationalStr = redisClient.get(internationalKey);
            if (!StringUtils.isBlank(internationalStr)) {
                Long nowBalance = redisClient.incrBy(internationalKey, count);
                logger.info("代理商给客户{}充值国际产品，5.redis余额-国际检测余额-修改成功。充值前余额：{}，充值后余额：{}",agentId, internationalStr, nowBalance);
            }

        }else if (category.equals(4)){
            // 国际定向通用检测
            String directCommonKey = RedisKeyConstant.DIRECT_COMMON_BALANCE_KEY + custId;
            String directCommonStr = redisClient.get(directCommonKey);
            if (!StringUtils.isBlank(directCommonStr)) {
                Long nowBalance = redisClient.incrBy(directCommonKey, count);
                logger.info("代理商给客户{}充值国际定向通用产品，5.redis余额-国际定向通用检测余额-修改成功。充值前余额：{}，充值后余额：{}",agentId, directCommonStr, nowBalance);
            }

        }else if (category.equals(5)){
            // 国际line定向检测
            String lineDirectKey = RedisKeyConstant.LINE_DIRECT_BALANCE_KEY + custId;
            String lineDirectStr = redisClient.get(lineDirectKey);
            if (!StringUtils.isBlank(lineDirectStr)) {
                Long nowBalance = redisClient.incrBy(lineDirectKey, count);
                logger.info("代理商给客户{}充值国际line定向产品，5.redis余额-国际line定向检测余额-修改成功。充值前余额：{}，充值后余额：{}",agentId, lineDirectStr, nowBalance);
            }

        }
    }

    /**
     * 如果redis余额存在，就更新。
     * @date 2021/11/15
     * @return void
     */
    private void decrRedisBalace(Long agentId,Integer number,Long custId,Integer category) {
        Integer count = number;
        if (category.equals(0)) {
            // 空号检测
            String emptyKey = RedisKeyConstant.EMPTY_BALANCE_KEY + custId;
            String emptyStr = redisClient.get(emptyKey);
            if (!StringUtils.isBlank(emptyStr)) {
                Long nowBalance = redisClient.decrBy(emptyKey, count);
                logger.info("代理商给客户{}退款空号产品.redis余额-空号余额-修改成功。充值前余额：{}，充值后余额：{}",agentId, emptyStr, nowBalance);
            }

        } else if (category.equals(1)){
            // 实时检测
            String realtimeKey = RedisKeyConstant.REALTIME_BALANCE_KEY + custId;
            String realtimeStr = redisClient.get(realtimeKey);
            if (!StringUtils.isBlank(realtimeStr)) {
                Long nowBalance = redisClient.decrBy(realtimeKey, count);
                logger.info("代理商给客户{}退款实时产品，5.redis余额-实时检测余额-修改成功。充值前余额：{}，充值后余额：{}",agentId, realtimeStr, nowBalance);
            }

        }else if (category.equals(2)){
            // 国际检测
            String internationalKey = RedisKeyConstant.INTERNATIONAL_BALANCE_KEY + custId;
            String internationalStr = redisClient.get(internationalKey);
            if (!StringUtils.isBlank(internationalStr)) {
                Long nowBalance = redisClient.decrBy(internationalKey, count);
                logger.info("代理商给客户{}退款国际产品，5.redis余额-国际检测余额-修改成功。充值前余额：{}，充值后余额：{}",agentId, internationalStr, nowBalance);
            }

        }else if (category.equals(4)){
            // 国际定向通用检测
            String directCommonKey = RedisKeyConstant.DIRECT_COMMON_BALANCE_KEY + custId;
            String directCommonStr = redisClient.get(directCommonKey);
            if (!StringUtils.isBlank(directCommonStr)) {
                Long nowBalance = redisClient.decrBy(directCommonKey, count);
                logger.info("代理商给客户{}退款国际定向通用产品，5.redis余额-国际定向通用检测余额-修改成功。充值前余额：{}，充值后余额：{}",agentId, directCommonStr, nowBalance);
            }

        }else if (category.equals(5)){
            // 国际line定向检测
            String lineDirectKey = RedisKeyConstant.LINE_DIRECT_BALANCE_KEY + custId;
            String lineDirectStr = redisClient.get(lineDirectKey);
            if (!StringUtils.isBlank(lineDirectStr)) {
                Long nowBalance = redisClient.decrBy(lineDirectKey, count);
                logger.info("代理商给客户{}退款国际line定向产品，5.redis余额-国际line定向检测余额-修改成功。充值前余额：{}，充值后余额：{}",agentId, lineDirectStr, nowBalance);
            }

        }
    }
    
    private Long getOpenBalance(Integer category,CustomerBalanceInfo customerBalanceInfo) {
    	switch (category) {
		case 0: return customerBalanceInfo.getEmptyCount();
		case 1: return customerBalanceInfo.getRealtimeCount();
		case 2: return customerBalanceInfo.getInternationalCount();
		case 4: return customerBalanceInfo.getDirectCommonCount();
		case 5: return customerBalanceInfo.getLineDirectCount();

		default:
			return 0L;
		}
    }
}
