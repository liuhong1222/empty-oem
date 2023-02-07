package com.cc.oem.modules.agent.service.records;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.data.records.EmptyCheckQueryVo;
import com.cc.oem.modules.agent.model.param.records.EmptyCheckQueryParam;

import java.io.Serializable;

/**
 * <pre>
 * 空号检测记录 服务类
 * </pre>
 *
 * @author rivers
 * @since 2020-03-03
 */
public interface EmptyCheckService {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    EmptyCheckQueryVo getEmptyCheckById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param emptyCheckQueryParam
     * @return
     * @throws Exception
     */
    R getEmptyCheckPageList(EmptyCheckQueryParam emptyCheckQueryParam) throws Exception;

    R getEmptyApiList(EmptyCheckQueryParam emptyQueryParam);

    EmptyCheckQueryVo getEmptyCheckApiDetailById(Long id);
}
