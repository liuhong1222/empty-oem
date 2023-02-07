package com.cc.oem.modules.job.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.oem.modules.agent.service.records.AgentDailyStasticService;
import com.cc.oem.modules.job.constant.TaskJobConstant;
import com.cc.oem.modules.job.dao.TaskRecordDao;
import com.cc.oem.modules.job.entity.task.BaseTaskParams;
import com.cc.oem.modules.job.entity.task.StatisticsDate;
import com.cc.oem.modules.job.entity.task.TaskRecord;
import com.cc.oem.modules.job.service.impl.CommonTaskMethodServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务
 * 汇总统计客户每天的消耗情况，日充值情况
 * 根据结果统计得到相关的代理商情况
 */
@Component("agentDailyInfoTask")
public class AgentDailyInfoTask {
	private final static Logger logger = LoggerFactory.getLogger(AgentDailyInfoTask.class);


	@Autowired
	private CommonTaskMethodServiceImpl taskMethodService;

	@Autowired
	private AgentDailyStasticService agentDailyStasticService;

	@Autowired
	private TaskRecordDao taskRecordDao;
	/**
	 * 每天隔两分钟执行一次
	 *
	 */

	//	@Scheduled(cron = "2 2 2 * * ? ")
	public void dailyReportHandle() {
		logger.info("QuartzEntity：{}，开始执行日报跑批操作",new Date());
		//构造生成日报条件参数
		BaseTaskParams baseTaskParams = new BaseTaskParams();
		baseTaskParams.setTaskType(TaskJobConstant.TASK_TYPE_DAILYAGENTCONSUME);
		baseTaskParams.setTaskName(TaskJobConstant.TASK_NAME_DAILYAGENTCONSUME);
		StatisticsDate statisticsDate = taskMethodService.startRecord(baseTaskParams);
		if (statisticsDate == null) {
			logger.info("当前服务未获取到锁，该任务或已被执行");
			return;
		}
		TaskRecord taskRecord = new TaskRecord();
		taskRecord.setTaskName(TaskJobConstant.TASK_NAME_DAILYAGENTCONSUME);
		taskRecord.setTaskType(TaskJobConstant.TASK_TYPE_DAILYAGENTCONSUME);
		taskRecord.setStatus(TaskJobConstant.TASKRECORD_SUCCESS);
		taskRecord.setInvokeDay(statisticsDate.getInvokeDate().intValue());
		taskRecord.setRunTime(new Date());
		taskRecord.setInvokeData(JSONObject.toJSONString(statisticsDate));
		try {
			agentDailyStasticService.generateDailyAgentStatistic(statisticsDate);
			taskRecord.setSuccessTime(new Date());
		} catch (Exception e) {
			taskRecord.setRemark(e.getMessage());
			taskRecord.setStatus(TaskJobConstant.TASKRECORD_FAILED);
			logger.error("------客户日消耗明细日统计任务执行异常，统计时间：{},info:------", JSON.toJSONString(statisticsDate), e);
		}finally {
			save(taskRecord);
		}
	}


	public int save(TaskRecord taskRecord) {
		return taskRecordDao.save(taskRecord);
	}
}
