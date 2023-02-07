package com.cc.oem.modules.agent.service.agentSettings;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.agentSettings.Goods;
import com.cc.oem.modules.agent.model.param.GoodsQueryParam;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 充值套餐管理 服务类
 * </pre>
 *
 */
public interface GoodsService{

    /**
     * 保存
     *
     * @param goods
     * @return
     * @throws Exception
     */
    R saveGoods(Goods goods) throws Exception;

    /**
     * 修改
     *
     * @param goods
     * @return
     * @throws Exception
     */
    R updateGoods(Goods goods) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteGoods(Long id) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    Goods getGoodsById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param goodsQueryParam
     * @return
     * @throws Exception
     */
    PageInfo getGoodsPageList(GoodsQueryParam goodsQueryParam) throws Exception;

    R listAgentGoods(Long agentId);
}
