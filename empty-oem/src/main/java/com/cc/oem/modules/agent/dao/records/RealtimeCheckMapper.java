package com.cc.oem.modules.agent.dao.records;

import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.records.RealtimeCheck;
import com.cc.oem.modules.agent.model.data.records.RealtimeCheckQueryVo;
import com.cc.oem.modules.agent.model.param.records.RealtimeCheckQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
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
public interface RealtimeCheckMapper extends BaseOemMapper<RealtimeCheck,Long> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    RealtimeCheckQueryVo getRealtimeCheckById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param realtimeCheckQueryParam
     * @return
     */
    List<RealtimeCheckQueryVo> getRealtimeCheckPageList(@Param("param") RealtimeCheckQueryParam realtimeCheckQueryParam);



    /**
     * 获取分页对象
     *
     * @param realtimeCheckQueryParam
     * @return
     */
    List<RealtimeCheckQueryVo> getRealtimeCheckApiList(@Param("param") RealtimeCheckQueryParam realtimeCheckQueryParam);

    RealtimeCheckQueryVo getRealtimeTotal(@Param("param")RealtimeCheckQueryParam param);
    RealtimeCheckQueryVo getRealtimeApiTotal(@Param("param")RealtimeCheckQueryParam param);
}
