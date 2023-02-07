package com.cc.oem.modules.job.service.impl;

import com.cc.oem.modules.job.dao.TaskRecordDao;
import com.cc.oem.modules.job.entity.task.TaskRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskRecordServiceImpl {

    @Autowired
    private TaskRecordDao taskRecordDao;

    public String findLatestSuccessRecord(Long targetInvokeDay, Integer taskType) {
        return taskRecordDao.findLatestSuccessRecord(targetInvokeDay, taskType);
    }

    public int save(TaskRecord taskRecord) {
        return taskRecordDao.save(taskRecord);
    }
}
