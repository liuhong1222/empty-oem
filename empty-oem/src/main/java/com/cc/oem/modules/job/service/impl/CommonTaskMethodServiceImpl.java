package com.cc.oem.modules.job.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cc.oem.common.redis.DistributedLockWrapper;
import com.cc.oem.common.redis.RedisClient;
import com.cc.oem.common.utils.JacksonUtil;
import com.cc.oem.modules.job.entity.task.BaseTaskParams;
import com.cc.oem.modules.job.entity.task.StatisticsDate;
import com.cc.oem.modules.job.utils.MyDateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CommonTaskMethodServiceImpl {
    private static Logger logger = LoggerFactory.getLogger(CommonTaskMethodServiceImpl.class);

    @Autowired
    private TaskRecordServiceImpl taskRecordService;
    //锁名称前缀
    public static final String LOCK_PREFIX = "redis_lock:";

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private JedisPool jedisPool;


    /**
     * 获取统计时间范围
     * 开始时间：如果没有上次调用记录，则默认开始时间为当天的0点0分0秒，
     * 开始时间：如果有上次调用记录，但是结束时间为null，则默认开始时间为当天的0点0分0秒，否则为上次结束时间
     * 结束时间：默认为当前时间减掉2分钟
     * 结束时间：如果当前时间与开始时间不是同一天，那么结束时间为第二天的0点0分0秒
     *
     * @param
     * @param baseTaskParams
     * @return 统计时间范围
     */
    public StatisticsDate getInvokeData(BaseTaskParams baseTaskParams) {
        if(baseTaskParams.getTaskType()==1 || baseTaskParams.getTaskType()==3){
            return getYesterdayDate();
        }
        Date startDate, endDate;
        String startTime = "";
        String endTime = "";
        //先根据当前时间确定结束时间
        Date currentDate = new Date();
        //默认为当前时间减2分钟
        endDate = DateUtils.addMinutes(currentDate, -2);
        endTime =
                MyDateUtils.formatDate(endDate, "yyyy-MM-dd HH:mm:ss");
        //理论上来说开始时间为减去一个周期的时间
        startDate = DateUtils.addMinutes(currentDate, -12);
        //依据这两个时间判断是不是同一天
        if (!DateUtils.isSameDay(startDate, endDate)) {
            endDate = DateUtils.ceiling(startDate, Calendar.HOUR_OF_DAY);
        }
        // 强制去除毫秒的影响
        endDate = DateUtils.truncate(endDate, Calendar.SECOND);
        Long invokeDay = Long.valueOf(
                MyDateUtils.formatDate(endDate, "yyyyMMdd"));

        String lastInvokeData = taskRecordService.findLatestSuccessRecord(invokeDay, baseTaskParams.getTaskType());
        if (null == lastInvokeData) {
            startDate = DateUtils.truncate(currentDate, Calendar.DATE);
            startTime =
                    MyDateUtils.formatDate(DateUtils.addMinutes(startDate, -2), "yyyy-MM-dd HH:mm:ss");
        } else {
            try {
                StatisticsDate lastStatisticsDate = JSONObject.parseObject(lastInvokeData, StatisticsDate.class);
                if (null == lastStatisticsDate.getEndDate()) {
                    startDate = DateUtils.truncate(currentDate, Calendar.DATE);
                    startTime =
                            MyDateUtils.formatDate(startDate, "yyyy-MM-dd HH:mm:ss");
                } else {
                    startTime = lastStatisticsDate.getEndDate();
                }
            } catch (Exception e) {
                // todo 出问题赶紧钉钉
                logger.error("getInvokeData - 获取上次InvokeData失败");
                return null;
            }
        }
        return new StatisticsDate(startTime, endTime,invokeDay);
    }

    private StatisticsDate getYesterdayDate(){
        Date currentDate = new Date();
        //默认为当前时间减2分钟

        String startTime = "";
        String endTime = "";
        Date yesterday = DateUtils.addDays(currentDate, -1);
        String yesterDay = MyDateUtils.formatDate(yesterday, "yyyy-MM-dd");
        startTime =yesterDay + " 00:00:00";
        endTime =yesterDay + " 23:59:59";
        Long invokeDay = Long.valueOf(yesterDay.replaceAll("-",""));
         return new StatisticsDate(startTime,endTime,invokeDay);

    }


    /**
     * 开始任务
     * 如果任务记录已开始，则跳过
     *
     * @param baseTaskParams
     * @return 任务记录
     * <p>
     * 多台服务器去获取之前的任务记录的时候，竞争同一个资源，
     */
    public StatisticsDate startRecord(BaseTaskParams baseTaskParams) {
        return getInvokeData(baseTaskParams);
    }


    public List<StatisticsDate> getStatisticsList(String startTime,int dayCount) {
        List<StatisticsDate> list= new ArrayList<>();
        if(dayCount==0) {
            String startStr = startTime + " 00:00:00";
            String endStr = startTime + " 23:59:59";
            Long invokeDay = Long.valueOf(startTime.replaceAll("-", ""));
            list.add(new StatisticsDate(startStr, endStr, invokeDay));
            return list;
        }
        Date startDate = com.cc.oem.common.utils.DateUtils.stringToDate(startTime, "yyyy-MM-dd");
        String startStr ="";
        String endStr ="";
        Long invokeDay = 0l;
        for(int index=0;index<=dayCount;index++){
            String currentDay = com.cc.oem.common.utils.DateUtils.format(startDate,"yyyy-MM-dd");
            invokeDay = Long.valueOf(currentDay.replaceAll("-",""));
            startStr = currentDay+" 00:00:00";
            endStr = currentDay+" 23:59:59";
            list.add(new StatisticsDate(startStr,endStr,invokeDay));
            startDate = DateUtils.addDays(startDate,1);
        }
        return list;
    }
}
