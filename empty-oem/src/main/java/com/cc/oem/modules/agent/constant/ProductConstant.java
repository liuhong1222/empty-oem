package com.cc.oem.modules.agent.constant;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName AgentConstant
 * author   zhangx
 * date     2018/8/9 11:34
 * description
 */
public class ProductConstant {

    /**
     * -审批状态，0：初始化，1：创建待审核，2：修改待审核，3：已审核，4：已驳回，5：已删除
     * */
    public static  int CREATE_WAIT_AUDIT=1;
    public static  int AUDITED=3;
    public static  int CREATE_REJECT=4;
    public static  int MODIFY_WAIT_AUDIT=2;
    public static  int MODIFY_WAIT_REJECT=4;


}
