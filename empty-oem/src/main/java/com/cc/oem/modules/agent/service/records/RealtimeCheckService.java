package com.cc.oem.modules.agent.service.records;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.records.RealtimeCheck;
import com.cc.oem.modules.agent.model.data.records.RealtimeCheckQueryVo;
import com.cc.oem.modules.agent.model.param.records.RealtimeCheckQueryParam;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 实时检测记录 服务类
 * </pre>
 *
 * @author rivers
 * @since 2021-01-18
 */
public interface RealtimeCheckService {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    RealtimeCheckQueryVo getRealtimeCheckById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param realtimeCheckQueryParam
     * @return
     * @throws Exception
     */
    R getRealtimeCheckPageList(RealtimeCheckQueryParam realtimeCheckQueryParam) throws Exception;


    RealtimeCheckQueryVo getRealtimeApiCheck(Long id);

    R getRealtimeCheckApiList(RealtimeCheckQueryParam realtimeCheckQueryParam);
}
