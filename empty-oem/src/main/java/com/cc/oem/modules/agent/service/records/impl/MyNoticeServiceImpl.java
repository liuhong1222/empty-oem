package com.cc.oem.modules.agent.service.records.impl;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.dao.CustInfoMapper;
import com.cc.oem.modules.agent.dao.records.MyNoticeMapper;
import com.cc.oem.modules.agent.entity.records.MyNotice;
import com.cc.oem.modules.agent.model.data.userManage.CustomerExtQueryData;
import com.cc.oem.modules.agent.model.param.userManage.CustextAuditParam;
import com.cc.oem.modules.agent.model.param.userManage.CustomerVerifyQueryParam;
import com.cc.oem.modules.agent.service.records.CustExtService;
import com.cc.oem.modules.agent.service.records.MyNoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenzj
 * @since 2018/5/23
 */

@Service
public class MyNoticeServiceImpl implements MyNoticeService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MyNoticeMapper myNoticeMapper;

    @Override
    public void batchSave(List<MyNotice> param){
        myNoticeMapper.batchSave(param);



    }




}
