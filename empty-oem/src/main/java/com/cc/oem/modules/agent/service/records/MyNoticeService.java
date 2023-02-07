package com.cc.oem.modules.agent.service.records;


import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.records.MyNotice;
import com.cc.oem.modules.agent.model.data.MessageInfoDetailData;
import com.cc.oem.modules.agent.model.param.MessageAuditParam;
import com.cc.oem.modules.agent.model.param.MessageInfoParam;
import com.cc.oem.modules.agent.model.param.MessageSaveParam;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author zhangx
 * date  2018/8/2 11:45
 */
@Service
public interface MyNoticeService {

    void batchSave(List<MyNotice> param);
}
