package com.cc.oem.modules.agent.constants;

/**
 * @author chenzj
 * @since 2018/8/9
 */
public class AgentConstant {
    public static final Integer MAX_PAGE_SIZE = 1048570;

    //代理商能否升级登记
    public static final String AGENT_LEVEL_CAN_UPGRADE_NAME="能";
    public static final String AGENT_LEVEL_CAN_NOT_UPGRADE_NAME="否";

    //代理商状态
    public static final String AGENT_STATUS_ENABLE="使用中";
    public static final String AGENT_STATUS_DISABLE="已禁用";

    //通用状态值
    public static final Integer COMMON_NORMAL_STATUS_VALUE = 1;

    //代理商禁用状态
    public static final Integer COMMON_DISABLED_STATUS_VALUE = 0;

    //代理商删除状态
    public static final Integer COMMON_DELETED_STATUS_VALUE = 2;

    //默认空号预警条数
    public static final Long DEFAULT_AGENT_EMPTY_WARN_NUMBER=2000L;

    //管理员角色
    public static final Long ADMIN_ROLE_ID=1L;
    //代理商角色
    public static final Long AGENT_ROLE_ID=2L;

    public static final String SYS_USER_PASSWORD="123456";


    //代理商主账号角色
    public static final Integer MASTER_SYS_USER_FLAG=0;
    //代理商副账号角色
    public static final Integer SLAVER_SYS_USER_FLAG=1;

    //代理商充值  充值
    public static final Integer agent_recharge_TYPE_RECHARGE=1;
    //代理商充值  退款
    public static final Integer agent_recharge_TYPE_REFUND=2;
    //代理商给用户充值
    public static final Integer agent_recharge_TYPE_RECHARGE_CUST=3;

    //代理商充值状态  待处理
    public static final Integer agent_recharge_STATUS_TODO=0;
    //代理商充值状态  成功
    public static final Integer agent_recharge_STATUS_SUCCESS=1;
    //代理商充值状态  失败
    public static final Integer agent_recharge_STATUS_FAILURE=2;

    //代理商充值操作的角色  代理商
    public static final Integer agent_recharge_ROLE_TYPE_AGENT=0;
    //代理商充值操作的角色  管理员
    public static final Integer agent_recharge_ROLE_TYPE_ADMIN=1;


    //空号检查服务成功码
    public static final String HTTP_UNN_RESULT_SUCCESS="000000";

    //常见问题审核状态  新增待审核
    public static final Integer AGENT_FAQ_STATUS_ADD_TODO=1;
    //常见问题审核状态  修改待审核
    public static final Integer AGENT_FAQ_STATUS_UPDATE_TODO=2;
    //常见问题审核状态  已审核
    public static final Integer AGENT_FAQ_STATUS_SUCCESS=3;
    //常见问题审核状态  新增驳回
    public static final Integer AGENT_FAQ_STATUS_ADD_REFUSED=4;
    //常见问题审核状态  修改驳回
    public static final Integer AGENT_FAQ_STATUS_UPDATE_REFUSED=4;
    //空号默认套餐包的条数
    public static final Integer DEFAULT_PACKAGE_COUNT=4;

    //产品类别，0：空号检测产品，1：实时检测产品 2-国际检测产品
    public static final Integer RECHARGE_CATEGORY_EMPTY=0;
    public static final Integer RECHARGE_CATEGORY_REALTIME=1;
    public static final Integer RECHARGE_CATEGORY_INTERNATIONAL=2;
    
    public static final Integer RECHARGE_CATEGORY_DIRECT_COMMON=4;
    
    public static final Integer RECHARGE_CATEGORY_LINE_DIRECT=5;

    public static final Integer AUTHLIME_LEVEL_1 = 0;
    public static final Integer AUTHLIME_LEVEL_2 = 1;
}
