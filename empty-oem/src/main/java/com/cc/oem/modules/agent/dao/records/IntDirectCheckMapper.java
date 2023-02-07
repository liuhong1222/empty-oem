package com.cc.oem.modules.agent.dao.records;

import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.records.IntDirectCheck;
import com.cc.oem.modules.agent.model.data.records.IntDirectCheckQueryVo;
import com.cc.oem.modules.agent.model.param.records.IntDirectQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntDirectCheckMapper extends BaseOemMapper<IntDirectCheck,Long> {

    List<IntDirectCheckQueryVo> getIntDirectCheckPageList(@Param("param") IntDirectQueryParam param);
    
    IntDirectCheckQueryVo getIntDirectCheckTotal(@Param("param")IntDirectQueryParam param);
}
