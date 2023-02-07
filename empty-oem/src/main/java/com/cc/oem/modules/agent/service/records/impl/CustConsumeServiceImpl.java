package com.cc.oem.modules.agent.service.records.impl;

import com.cc.oem.common.utils.DateUtils;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.dao.records.CustConsumeMapper;
import com.cc.oem.modules.agent.dao.records.EmptyCheckMapper;
import com.cc.oem.modules.agent.model.data.records.CommonDateTimeData;
import com.cc.oem.modules.agent.model.data.records.ConsumeData;
import com.cc.oem.modules.agent.model.data.records.EmptyCheckQueryVo;
import com.cc.oem.modules.agent.model.param.records.EmptyCheckQueryParam;
import com.cc.oem.modules.agent.service.records.CustConsumeService;
import com.cc.oem.modules.agent.service.records.EmptyCheckService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 客户消耗操作类
 * </pre>
 *
 */
@Slf4j
@Service
public class CustConsumeServiceImpl implements CustConsumeService {

    @Autowired
    private CustConsumeMapper consumeMapper;


    public List<ConsumeData> queryTodayConsumeData(){
        CommonDateTimeData timeData = DateUtils.getCommonDateTime();
        return consumeMapper.queryTodayConsumeData(timeData);
    }

    public List<ConsumeData> queryTodayConsumeDataOfAgent(Long agentId){
        CommonDateTimeData timeData = DateUtils.getCommonDateTime();
        return consumeMapper.queryTodayConsumeDataOfAgent(agentId,timeData);
    }

}
