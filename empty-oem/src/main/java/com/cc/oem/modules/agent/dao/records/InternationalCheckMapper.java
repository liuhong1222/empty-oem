package com.cc.oem.modules.agent.dao.records;

import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.records.InternationalCheck;
import com.cc.oem.modules.agent.model.data.records.InternationalCheckQueryVo;
import com.cc.oem.modules.agent.model.param.records.InternationalQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * 实时检测记录 Mapper 接口
 * </pre>
 *
 * @author rivers
 * @since 2021-01-18
 */
@Repository
public interface InternationalCheckMapper extends BaseOemMapper<InternationalCheck,Long> {

    List<InternationalCheckQueryVo> getInternationalCheckPageList(@Param("param") InternationalQueryParam param);
    
    InternationalCheckQueryVo getInternationalTotal(@Param("param")InternationalQueryParam param);
}
