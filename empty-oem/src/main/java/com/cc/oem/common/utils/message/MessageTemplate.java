package com.cc.oem.common.utils.message;

/**
 * Description: User: liutao Date: 2018-06-22 Time: 16:06
 */
public class MessageTemplate {

    public static final String BALANCE_EXCEPTION_MSG           = "警告！账户：%s余额%s异常，异常记录编号：%s,异常原因：%s";

    public static final String LOCK_MESSAGE                    = "严重警告！账户：%s余额异常，已锁定。";

    public static final String TAG_MESSAGE                     = "注意！账户：%s余额异常，已标记。";

    public static final String UNLOCK_MESSAGE                  = "账户：%s余额异常已恢复，解除锁定。";

    public static final String RM_TAG_MESSAGE                  = "账户：%s余额异常已恢复，解除标记。";

    public final static String WARN_MESSAGE_CONTENT            = "警告！在线检测回调保存数据失败。data:%s,message:%s";

    public static final String BALANCE_EXCEPTION_REPAIR        = "账户：%s余额异常记录修复%s，记录编号：%s";

    public static final String REDIS_DISCONNECT_WARN           = "警告：API调用Redis连接超时";

    public static final String REDIS_CHANGE_BALANCE_WARN       = "警告：API调用返还预扣金额异常，accNo:%s,preFee:%s,fee:%s,bizType:%s";

    public static final String REDIS_CHANGE_STACK_BALANCE_WARN = "警告：API调用变更堆积金额异常，accNo:%s,fee:%s,bizType:%s";

    public static final String REPAIR_CHANGE_BALANCE_WARN      = "手工修复缓存余额，accNo:%s,amount:%s,balance:%s";

    public static final String DATACENTER_TEAM_EMAIL           = "datacenter@253.com";

    public final static String START_DETECT_ERROR              = "警告！%s开始检测调用下游失败。fileCode:%s";

    public final static String DETECT_CALLBACK_ERROR           = "注意！在线检测回调，意外的回调状态。fileCode:%s,state:%s";

    public static final String SKU_PURCHASE_SAVE_WARN          = "注意：套餐包购买记录生成异常,违反数据约束，accNo:%s,sku:%s,orderNo:%s";

    public static final String SKU_PURCHASE_REPAIR_WARN        = "注意：手工修复购买记录生成计费模型完成，orderNo:%s";

    public static final String SKU_PURCHASE_SAVE_ERROR         = "警告：套餐包购买记录生成失败，accNo:%s,sku:%s";

    public static final String SKU_PURCHASE_MODIFY_ERROR       = "警告：套餐包订单回调失败，orderNo:%s,state:%s";

    public static final String SKU_ORDER_CREATE_ERROR          = "警告：套餐包订单生成失败，accNo:%s,skuPurchaseId:%s,skuCode:%s";

    public static final String SKU_ORDER_PAY_ERROR             = "警告：套餐包订单回调，修改ERP订单失败，accNo:%s,orderNo:%s,state:%s";

    public static final String SKU_ORDER_PAY_FAIL              = "警告：套餐包订单支付失败，accNo:%s,orderNo:%s,state:%s";

    public static final String SKU_PURCHASE_MODIFY_WARN        = "警告：套餐包订单回调处理失败，重复的回调记录，orderNo:%s,state:%s";

    public static final String ONLINE_ORDER_CREATE_ERROR       = "警告：在线充值订单生成失败，accNo:%s,money:%s";

    public static final String ONLINE_ORDER_MODIFY_ERROR       = "警告：在线充值订单回调处理失败，orderNo:%s,state:%s";

    public static final String FREE_ORDER_CREATE_ERROR         = "警告：注册赠送订单生成失败，accNo:%s,money:%s";

    public static final String MON_SETTLE_ENABLE_ERROR         = "警告：账户启用月结失败, accNo:%s";

    public static final String MON_SETTLE_DISABLE_ERROR        = "警告：账户停用月结失败, accNo:%s";

    public static final String MON_SETTLE_RECHARGE_ERROR       = "警告：月结还款失败, accNo:%s,xid:%s,money:%s";

    public static final String ACCOUNT_REMIND_VALUE_MESSAGE    = "账户%s从未登录，账户配置不存在,无法更新余额提醒阈值";

    public static final String ACTIVITY_ORDER_CREATE_ERROR          = "警告：活动赠送订单生成失败，accNo:%s,activityNo:%s";

    public static final String ACTIVITY_PURCHASE_SAVE_ERROR         = "警告：活动赠送记录生成失败，accNo:%s,activityNo:%s";

    public static final String ACTIVITY_PURCHASE_UPDATE_ERROR         = "警告：活动赠送记录更新失败，accNo:%s,activityNo:%s";

    public static final String ACTIVITY_CHARGEMODEL_UPDATE_ERROR         = "警告：活动赠送记录更新计费模型失败，accNo:%s,activityNo:%s";

    public static final String ACTIVE_CHARGEMODEL_UPDATE_ERROR         = "警告：激活与反激活更新计费模型失败，accNo:%s,prodCode:%s,bizTypeCode:%s";
}
