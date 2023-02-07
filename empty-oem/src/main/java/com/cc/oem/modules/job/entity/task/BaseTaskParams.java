package com.cc.oem.modules.job.entity.task;

import lombok.Data;

/**
 * @Description
 * @Author wade_woke
 * @Date 2021-06-01 10:07:08
 */

@Data
public class BaseTaskParams {
    /**
     * 任务目标日期
     */
    private Long invokeDay;
    /**
     * 0，执行失败，1，执行成功
     */
    private Integer status;

    /**
     * 1.日报统计 2.回调统计， 3.短链统计
     */
    private Integer taskType;

    /**
     * 任务名称
     */
    private String taskName;

}
