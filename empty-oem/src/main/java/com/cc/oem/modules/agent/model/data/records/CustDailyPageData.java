package com.cc.oem.modules.agent.model.data.records;

import lombok.Data;

import java.util.Date;

@Data
public class CustDailyPageData {

    private Long agentId;

    private Long customerId;

    private Long dayInt;

    private Integer staticType;

    private Date createTime;

    private Date updateTime;

    /**
     * 空号检测总条数
     */
    private Long emptyTotal=0l;
    /**
     * 实时检测总条数
     */
    private Long realtimeTotal=0l;
    /**
     * 国际检测总条数
     */
    private Long internationalTotal = 0L;
    /**
     * 国际定向通用检测总条数
     */
    private Long directCommonTotal = 0L;
    /**
     * 国际line定向检测总条数
     */
    private Long lineDirectTotal = 0L;


    private String customerName;

    private String phone;

    private String companyName;

}
