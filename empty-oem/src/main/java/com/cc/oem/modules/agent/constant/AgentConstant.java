package com.cc.oem.modules.agent.constant;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName AgentConstant
 * author   zhangx
 * date     2018/8/9 11:34
 * description
 */
public class AgentConstant {

    public static final Long Admin_Role_Id = 1L;
    public static final Long Agent_Role_Id = 2L;

    //      `is_auth` int(1) DEFAULT '0' COMMENT '是否认证  0-未认证 1-已提交审核 2- 审核驳回 3-已认证',
    public static final int AUTH_STATUS_REJECT = 2;
    public static final int AUTH_STATUS_PASS = 3;
    public final static String TRD_ORDER_STATUS_SUCCEED = "1";

    public final static int ALIPAY_STATUS_SUCCEED = 0;
    public final static int ALIPAY_STATUS_FAIL = 1;

    public final static int ALIPAY_LOG_TYPE_PRECREATE = 1;
    public final static int ALIPAY_LOG_TYPE_CALLBACK1 = 2;
    public final static int ALIPAY_LOG_TYPE_CHECK = 3;

    /**代理商给自己充值 true*/
    public static  final String AgentRecharge="agentRecharge";
    /**代理商给客户充值 true*/
    public static  final String AgentRechargeForCust="agentRechargeForCust";
    /**代理商给客户退款 true*/
    public static  final String AgentRefundForCust="agentRefundForCust";
    /**代理商给客户退款 true*/
    public static  final String AdminRechargeForAgent="adminRechargeForAgent";

}
