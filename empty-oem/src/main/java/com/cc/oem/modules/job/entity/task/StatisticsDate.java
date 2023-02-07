package com.cc.oem.modules.job.entity.task;

import lombok.Data;

/**
 * 任务时间实体类
 * @author wade
 * @date 2021年6月21日
 */
@Data
public class StatisticsDate {
    private String startDate;
    private String endDate;
    private Long invokeDate;

    public StatisticsDate(String startTime, String endTime, Long invokeDay) {
        this.startDate = startTime;
        this.endDate = endTime;
        this.invokeDate = invokeDay;
    }

    public StatisticsDate(){

    }
}
