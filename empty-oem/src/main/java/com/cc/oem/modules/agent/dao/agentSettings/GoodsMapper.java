package com.cc.oem.modules.agent.dao.agentSettings;

import com.cc.oem.modules.agent.BaseOemMapper;
import com.cc.oem.modules.agent.entity.agentSettings.Goods;
import com.cc.oem.modules.agent.model.param.GoodsQueryParam;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 充值套餐管理 Mapper 接口
 * </pre>
 *
 * @author rivers
 * @since 2020-02-13
 */
@Repository
public interface GoodsMapper extends BaseOemMapper<Goods,Long> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    Goods getGoodsById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param goodsQueryParam
     * @return
     */
    List<Goods> getGoodsPageList(@Param("param") GoodsQueryParam goodsQueryParam);

    List<Goods> getGoodsPageByAgentId(@Param("agentId") Long agentId, @Param("category")Integer category);

    List<Map> listAgentGoods(Long agentId);
}
