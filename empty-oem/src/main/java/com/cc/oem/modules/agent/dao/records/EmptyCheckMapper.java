package com.cc.oem.modules.agent.dao.records;

import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.records.EmptyCheck;
import com.cc.oem.modules.agent.model.data.records.EmptyCheckQueryVo;
import com.cc.oem.modules.agent.model.param.records.EmptyCheckQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 空号检测记录 Mapper 接口
 * </pre>
 *
 */
@Mapper
public interface EmptyCheckMapper extends BaseOemMapper<EmptyCheck,Long> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    EmptyCheckQueryVo getEmptyCheckById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param emptyCheckQueryParam
     * @return
     */
    List<EmptyCheckQueryVo> getEmptyCheckPageList(@Param("param") EmptyCheckQueryParam emptyCheckQueryParam);

    /**
     * 获取Api的分页对象
     *
     * @param emptyCheckQueryParam
     * @return
     */
    List<EmptyCheckQueryVo> getEmptyCheckApiList(@Param("param") EmptyCheckQueryParam emptyCheckQueryParam);
    Long getEmptyCheckApiList_COUNT(@Param("param") EmptyCheckQueryParam emptyCheckQueryParam);

    EmptyCheckQueryVo getEmptyTotal(@Param("param") EmptyCheckQueryParam param);

    EmptyCheckQueryVo getEmptyApiTotal(@Param("param") EmptyCheckQueryParam param);
}
