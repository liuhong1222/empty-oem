/*
 *
 *  Copyright (c) wade_woke updateStatus based on pgx
 *
 */

package com.cc.oem.modules.job.dao;

import com.cc.oem.modules.job.entity.task.TaskRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 最终实际发送的短信模板
 *
 * @author wade_woke
 * @date 2021-06-01
 */
@Mapper
public interface TaskRecordDao {

    @Select("select invoke_data from task_record where invoke_day=#{targetInvokeDay} and status=1 and task_type=#{taskType} order by run_time desc limit 0,1")
    String findLatestSuccessRecord(@Param("targetInvokeDay") Long targetInvokeDay, @Param("taskType") Integer taskType);

    @Insert("insert into task_record(task_id,task_name,status,run_time,success_time,invoke_data,data_version,invoke_day,remark,task_type)" +
            "values(#{taskId},#{taskName},#{status},#{runTime},#{successTime},#{invokeData},#{dataVersion},#{invokeDay},#{remark},#{taskType})")
    int save(TaskRecord taskRecord);
}
