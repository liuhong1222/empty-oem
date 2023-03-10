package com.cc.oem.modules.job.entity.task;

import lombok.Data;

import java.util.Date;

@Data
public class TaskRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task_record.task_id
     *
     * @mbggenerated Thu Mar 22 09:26:10 CST 2018
     */
    private String taskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task_record.task_name
     *
     * @mbggenerated Thu Mar 22 09:26:10 CST 2018
     */
    private String taskName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task_record.state
     *
     * @mbggenerated Thu Mar 22 09:26:10 CST 2018
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task_record.run_time
     *
     * @mbggenerated Thu Mar 22 09:26:10 CST 2018
     */
    private Date runTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task_record.success_time
     *
     * @mbggenerated Thu Mar 22 09:26:10 CST 2018
     */
    private Date successTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task_record.invoke_data
     *
     * @mbggenerated Thu Mar 22 09:26:10 CST 2018
     */
    private String invokeData;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task_record.data_version
     *
     * @mbggenerated Thu Mar 22 09:26:10 CST 2018
     */
    private String dataVersion;

    /**
     * 任务反馈
     */
    private String remark;

    /**
     * 任务类型，1.日报统计
     * 2.回调统计，
     * 3.短链统计
     */
    private Integer taskType;

    /**
     * 目标时间
     */
    private Integer invokeDay;

    @Override
    public String toString() {
        return "TaskRecord{" + "taskId='" + taskId + '\'' + ", taskName='" + taskName + '\'' + ", state=" + status + ", runTime=" + runTime + ", successTime=" + successTime + ", invokeData='" + invokeData + '\'' + ", dataVersion='" + dataVersion + '}';
    }
}