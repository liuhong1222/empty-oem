package com.cc.oem.modules.agent.model.data;

import com.cc.oem.common.annotation.ExcelField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName CustExportData
 * description 导出表格时使用
 */
@Data
public class CustExportData {

    private Long customerId;

    @ExcelField(value = "手机号码", order = 0)
    private String phone;

    private String email;

    @ExcelField(value = "客户类型", order = 1)
    private String customerType;

    @ExcelField(value = "客户名称", order =2)
    private String name;

    @ExcelField(value = "官网类型", order = 3,needChange = 1)
    private String officialName;

    @ExcelField(value = "代理商名称", order = 4)
    private String companyName;
    
    @ExcelField(value = "注册时间", order = 5)
    private String createTime;

    private String ip;

    @ExcelField(value = "空号充值总计(元)", order = 6)
    private BigDecimal  emptyRechargeMoney;
    @ExcelField(value = "空号充值总条数", order = 7)
    private Long emptyRechargeNum;
    @ExcelField(value = "空号剩余条数", order = 8)
    private Long emptyCount;
    
    @ExcelField(value = "实时充值总计(元)", order = 9)
    private BigDecimal realtimeRechargeMoney;
    @ExcelField(value = "实时充值总条数", order = 10)
    private Long realtimeRechargeNum;
    @ExcelField(value = "实时剩余条数", order = 11)
    private Long realtimeCount;
    
    @ExcelField(value = "国际充值总计(元)", order = 12)
    private BigDecimal internationalRechargeMoney;
    @ExcelField(value = "国际充值总条数", order = 13)
    private Long internationalRechargeNum;
    @ExcelField(value = "国际剩余条数", order = 14)
    private Long internationalCount;
    
    @ExcelField(value = "国际定向通用充值总计(元)", order = 15)
    private BigDecimal directCommonRechargeMoney;
    @ExcelField(value = "国际定向通用充值总条数", order = 16)
    private Long directCommonRechargeNum;
    @ExcelField(value = "国际定向通用剩余条数", order = 17)
    private Long directCommonCount;
    
    @ExcelField(value = "国际line定向充值总计(元)", order = 18)
    private BigDecimal lineDirectRechargeMoney;
    @ExcelField(value = "国际line定向充值总条数", order = 19)
    private Long lineDirectRechargeNum;
    @ExcelField(value = "国际line定向剩余条数", order = 20)
    private Long lineDirectCount;

    /**
     * 0 false,1true
     */
    private boolean canRefundFlag =true;
    private Integer canPresent;
    private Integer apiState;

    private Integer authenticationLimitLevel;
    private Integer officialWeb;

}
