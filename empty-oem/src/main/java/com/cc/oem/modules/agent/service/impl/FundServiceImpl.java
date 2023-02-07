package com.cc.oem.modules.agent.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.cc.oem.modules.agent.constants.AgentConstant;
import com.cc.oem.common.exception.RRException;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.dao.*;
import com.cc.oem.modules.agent.entity.*;
import com.cc.oem.modules.agent.enums.AgentRechargePayTypeEnum;
import com.cc.oem.modules.agent.model.param.FundRechargeParam;
import com.cc.oem.modules.agent.service.FundService;
import com.cc.oem.modules.config.utils.Snowflake;
import com.cc.oem.modules.sys.service.SysConfigService;
import com.cc.oem.modules.sys.service.SysUserService;
import com.google.common.collect.Maps;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName FundServiceImpl
 * author   zhangx
 * date     2018/8/17 14:10
 * description
 */
@Service
public class FundServiceImpl implements FundService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${alipay_appid}")
    private String alipayAppid;
    @Value("${alipay_payurl}")
    private String alipayPayurl;
    @Value("${alipay_publickey}")
    private String alipayPublickey;
    @Value("${alipay_privatekey}")
    private String alipayPrivatekey;
    @Value("${alipay_callbackurl}")
    private String alipayCallbackurl;

    @Autowired
    AgentRechargeMapper agentRechargeMapper;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    AgentMapper agentMapper;
    @Autowired
    AgentAccountMapper agentAccountMapper;
    @Autowired
    AlipayLogMapper alipayLogMapper;

    @Autowired
    SysConfigService sysConfigService;
    @Autowired
    private Snowflake snowflake;

    @Override
    public R recharge(FundRechargeParam param) {

        Long agentId = sysUserService.selectAgentIdBySysUserId(sysUserService.getSysUserId());
        if (agentId == null) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "只有代理商才可调用该接口!");
        }

        int numberScale = param.getNumber().scale();
        if (numberScale > 5) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "条数不正确，请重新输入!");
        }
        BigDecimal numberParam = param.getNumber().multiply(BigDecimal.valueOf(10000)).setScale(0, BigDecimal.ROUND_UP);

        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        try{
            if(param.getCategory()==1&&agent.getRealPrice().compareTo(BigDecimal.ZERO)<0){
                return R.error("您没有开通实时检测类账户，请联系管理员");
            }
            
            if(param.getCategory()==2&&agent.getInternationalPrice().compareTo(BigDecimal.ZERO)<0){
                return R.error("您没有开通国际检测类账户，请联系管理员");
            }
            
            if(param.getCategory()==4&&agent.getDirectCommonPrice().compareTo(BigDecimal.ZERO)<0){
                return R.error("您没有开通定向通用检测类账户，请联系管理员");
            }
            
            if(param.getCategory()==5&&agent.getLineDirectPrice().compareTo(BigDecimal.ZERO)<0){
                return R.error("您没有开通line定向检测类账户，请联系管理员");
            }
        }catch (Exception e){
            return R.error("您没有开通实时检测类账户，请联系管理员");
        }

        BigDecimal price = getPrice(param.getCategory(), agent);
        if (price.compareTo(param.getPrice()) != 0) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "单价参数不对，请确认!");
        }

        Long number = numberParam.longValue();
        BigDecimal money = param.getMoney().multiply(BigDecimal.valueOf(10000));

        long number2 = money.divide(price, 0, BigDecimal.ROUND_UP).longValue();
        if (number != number2) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "参数不对，请检查");
        }

        money = money.setScale(2, BigDecimal.ROUND_UP);

        String orderNo = "O_CLSH_" + UUID.randomUUID().toString().replaceAll("-", "");
        AgentRecharge agentRecharge = new AgentRecharge();
        agentRecharge.setOrderNo(orderNo);
        agentRecharge.setAgentId(agentId);
        agentRecharge.setPrice(price);
        agentRecharge.setRechargeNumber(number.intValue());
        agentRecharge.setPaymentAmount(money.intValue());
        agentRecharge.setPayType(AgentRechargePayTypeEnum.ALIPAY.getCode());
        agentRecharge.setRemark(param.getRemark());
        agentRecharge.setStatus(AgentConstant.agent_recharge_STATUS_TODO);
        agentRecharge.setAgentLevel(agent.getAgentLevel());
        agentRecharge.setName(agent.getCompanyName());
        agentRecharge.setPhone(agent.getLinkmanPhone());
        agentRecharge.setId(snowflake.nextId());
        agentRechargeMapper.insertSelective(agentRecharge);

        // 向支付宝发送请求 生成二维码
        AlipayClient alipayClient = new DefaultAlipayClient(alipayPayurl, alipayAppid, alipayPrivatekey, "json", "utf-8", alipayPublickey, "RSA2");
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest(); // 创建API对应的request类
        String subject = "空号检测产品";
        String storeId = "数据";
        request.setBizContent("{" + "    \"out_trade_no\":\"" + orderNo + "\"," + "    \"total_amount\":\"" + money + "\","
                + "    \"subject\":\"" + subject + "\"," + "    \"store_id\":\"" + storeId + "\","
                + "    \"timeout_express\":\"90m\"}");// 设置业务参数
        request.setNotifyUrl(alipayCallbackurl);

        String requestData = JSON.toJSONString(request);
//        logger.info("AlipayClient request param:" +requestData );

        AlipayLog alipayLog = new AlipayLog();
        alipayLog.setOrderNo(orderNo);
        alipayLog.setRequestData(requestData);
        alipayLog.setType(com.cc.oem.modules.agent.constant.AgentConstant.ALIPAY_LOG_TYPE_PRECREATE);

        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);

            alipayLog.setResponseData(JSON.toJSONString(response));

            if (response.isSuccess()) {
                alipayLog.setSuccessFlag(com.cc.oem.modules.agent.constant.AgentConstant.ALIPAY_STATUS_SUCCEED);

                JSONObject responseBody = JSONObject.parseObject(response.getBody());
                JSONObject json = JSONObject.parseObject(responseBody.get("alipay_trade_precreate_response").toString());
                if (json.get("code").equals("10000")) {
                    //返回结果
                    Map map = Maps.newHashMap();
                    map.put("payUrl", json.get("qr_code").toString());
                    map.put("orderNo", orderNo);

                    R result = new R();
                    result.put("data", map);
                    return result;
                }
            } else {
                alipayLog.setSuccessFlag(com.cc.oem.modules.agent.constant.AgentConstant.ALIPAY_STATUS_FAIL);

                logger.error("申请支付异常：【" + response.getBody() + "】");
                return R.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getSubMsg());
            }

        } catch (Exception e) {
            alipayLog.setSuccessFlag(com.cc.oem.modules.agent.constant.AgentConstant.ALIPAY_STATUS_FAIL);

            logger.error("申请支付系统异常", e);
            logger.error("申请支付系统异常：【" + e.getMessage() + "】");
            return R.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "申请支付异常,请重试");
        } finally {
            alipayLogMapper.insertSelective(alipayLog);
        }
        return R.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "申请支付异常,请重试");
    }

    @Override
    public String rechargeCallBack(Map<String, String> param) throws AlipayApiException {
        logger.info("收到相关的支付宝回调参数");
        String orderNo = param.get("out_trade_no");
        String tradNo = param.get("trade_no");
        String status = param.get("trade_status");
//        String sign = param.get("sign");
        String tradeStatus = param.get("trade_status");

        boolean signCheck = AlipaySignature.rsaCheckV1(param, alipayPublickey, "utf-8", "RSA2");
        if (!signCheck) {
            logger.error("支付宝验签失败,param:" + param);
            return "false";
        }
        AlipayLog alipayLog = new AlipayLog();
        alipayLog.setResponseData(JSON.toJSONString(param));
        alipayLog.setOrderNo(orderNo);
        alipayLog.setType(com.cc.oem.modules.agent.constant.AgentConstant.ALIPAY_LOG_TYPE_CALLBACK1);
        alipayLogMapper.insertSelective(alipayLog);

        boolean dealFlag = dealRecharge(orderNo, tradNo, tradeStatus, param);
        AlipayLog alipayLog2 = new AlipayLog();
        alipayLog2.setId(alipayLog.getId());
        if (dealFlag) {
            alipayLog2.setSuccessFlag(com.cc.oem.modules.agent.constant.AgentConstant.ALIPAY_STATUS_SUCCEED);
            return "success";
        } else {
            alipayLog2.setSuccessFlag(com.cc.oem.modules.agent.constant.AgentConstant.ALIPAY_STATUS_FAIL);
        }
        alipayLogMapper.updateByPrimaryKeySelective(alipayLog2);

        return "success1";
    }

    /**
     * 根据回调处理充值操作
     * @param orderNo
     * @param tradNo
     * @param tradeStatus
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dealRecharge(String orderNo, String tradNo, String tradeStatus, Map<String, String> param) {
        AgentRecharge findAgentRecharge = agentRechargeMapper.findOrderByOrderNo(orderNo);
        if (findAgentRecharge == null) {
            logger.error("充值回调异常,未找到orderNo" + orderNo);
            return false;
        }

        BigDecimal money = new BigDecimal(findAgentRecharge.getPaymentAmount());
        BigDecimal totalAmount = new BigDecimal(param.get("total_amount").toString());
        if (totalAmount == null || money.compareTo(totalAmount) != 0) {
            logger.error("充值回调异常,充值金额与数据库不一致 " + orderNo);
            return false;
        }

        if (findAgentRecharge.getStatus().intValue() == AgentConstant.agent_recharge_STATUS_SUCCESS.intValue()) {
            logger.error("充值回调异常,该orderNo可能已被正常处理" + orderNo);
            return true;
        }

        if (findAgentRecharge.getStatus().intValue() != AgentConstant.agent_recharge_STATUS_TODO.intValue()) {
            logger.error("充值回调异常,状态不对orderNo" + orderNo);
            return false;
        }

        String appId = param.get("app_id");
        if (!alipayAppid.equals(appId)) {
            logger.error("充值回调异常,appid与现有服务不一致 " + orderNo);
            return false;
        }

        AgentRecharge agentRecharge = findAgentRecharge;
        AgentRecharge updateOrder = new AgentRecharge();
        updateOrder.setId(agentRecharge.getId());

        updateOrder.setOrderNo(tradNo);
        updateOrder.setVersion(agentRecharge.getVersion());
        if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
            updateOrder.setStatus(AgentConstant.agent_recharge_STATUS_SUCCESS);
            int count1 = agentRechargeMapper.updateByIdAndVersionSelective(updateOrder);
            int count2 = agentAccountMapper.addEmptyBalanceByAgentId(agentRecharge);

            if ((count1 + count2) != 2) {
                logger.error("充值回调异常:" + orderNo + "," + tradeStatus + "," + tradNo);
                throw new RRException("充值回调异常更新数据异常");
            }
        } else {
            updateOrder.setStatus(AgentConstant.agent_recharge_STATUS_FAILURE);
            int count1 = agentRechargeMapper.updateByIdAndVersionSelective(updateOrder);
            if (count1 != 1) {
                throw new RRException("充值回调异常更新数据异常");
            }
        }
        return true;
    }

    @Override
    public R findOrderStatus(String orderNo) {
        Map map = Maps.newHashMap();
        map.put("orderStatus", "Unkown");
        if (!StringUtils.isEmpty(orderNo)) {
            AgentRecharge agentRecharge = agentRechargeMapper.findOrderByOrderNo(orderNo);
            if (agentRecharge == null || agentRecharge.getId() == null) {
                map.put("orderStatus", "NotExist");
            } else {
                int status = agentRecharge.getStatus();
                if (status == AgentConstant.agent_recharge_STATUS_TODO.intValue()) {
                    map.put("orderStatus", "WaitForDeal");
                } else if (status == AgentConstant.agent_recharge_STATUS_SUCCESS.intValue()) {
                    map.put("orderStatus", "Success");
                } else if (status == AgentConstant.agent_recharge_STATUS_FAILURE.intValue()) {
                    map.put("orderStatus", "Fail");
                } else {
                    map.put("orderStatus", "Unkown");
                }
            }
        }

        return R.ok((Object) map);
    }

    private BigDecimal getPrice(Integer category,Agent agent) {
    	switch (category) {
		case 0:return agent.getPrice();
		case 1:return agent.getRealPrice();
		case 2:return agent.getInternationalPrice();
		case 4:return agent.getDirectCommonPrice();
		case 5:return agent.getLineDirectPrice();

		default:return BigDecimal.ZERO;
		}
    }
}

