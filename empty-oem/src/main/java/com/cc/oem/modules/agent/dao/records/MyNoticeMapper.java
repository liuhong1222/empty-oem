package com.cc.oem.modules.agent.dao.records;

import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.records.MyNotice;
import com.cc.oem.modules.agent.model.data.records.CommonDateTimeData;
import com.cc.oem.modules.agent.model.data.records.ConsumeData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <pre>
 * 我的信息 Mapper 接口
 * </pre>
 *
 */
@Mapper
public interface MyNoticeMapper extends BaseOemMapper {


    int batchSave(List<MyNotice> param);
}
