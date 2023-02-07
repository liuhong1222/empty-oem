package com.cc.oem.modules.agent.model.param;

import org.apache.commons.lang.StringUtils;

/**
 * @author chenzj
 * @since 2018/8/13
 */
public class TimesParam extends PageParam {
    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 拼接开始时间、结束时间
     */
    public void appendTimeString(){
        if (StringUtils.isNotBlank(this.startTime)){
            this.startTime = this.startTime+" 00:00:00";
        }
        if (StringUtils.isNotBlank(this.endTime)){
            this.endTime = this.endTime+" 23:59:59";
        }
    }

}
