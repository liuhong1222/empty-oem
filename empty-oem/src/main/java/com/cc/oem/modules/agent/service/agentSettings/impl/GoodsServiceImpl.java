package com.cc.oem.modules.agent.service.agentSettings.impl;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.dao.AgentMapper;
import com.cc.oem.modules.agent.dao.agentSettings.GoodsMapper;
import com.cc.oem.modules.agent.entity.Agent;
import com.cc.oem.modules.agent.entity.agentSettings.Goods;
import com.cc.oem.modules.agent.model.param.GoodsQueryParam;
import com.cc.oem.modules.agent.service.agentSettings.GoodsService;
import com.cc.oem.modules.config.utils.Snowflake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 充值套餐管理 服务实现类
 * </pre>
 *
 * @author rivers
 * @since 2020-02-13
 */
@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private AgentMapper agentMapper;

    @Autowired
    private Snowflake snowflake;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveGoods(Goods goods){
        Agent agent = agentMapper.selectByPrimaryKey(goods.getAgentId());
        if(goods.getCategory()==0){
            if(goods.getType()==1&&new BigDecimal(goods.getUnitPrice()).compareTo(agent.getPrice())<0){
                return R.error("空号自定义单价不能小于" + agent.getPrice());
            }else if (new BigDecimal(goods.getPrice()).divide(new BigDecimal(goods.getSpecifications()), 5, RoundingMode.DOWN)
                    .compareTo(agent.getPrice()) < 0) {
                return R.error("空号单条单价不能小于" + agent.getPrice());
            }
        }else if(goods.getCategory()==1){
            if(goods.getType()==1&&new BigDecimal(goods.getUnitPrice()).compareTo(agent.getRealPrice())<0){
                return R.error("实号自定义单价不能小于" + agent.getRealPrice());
            }else if (new BigDecimal(goods.getPrice()).divide(new BigDecimal(goods.getSpecifications()), 5, RoundingMode.DOWN)
                    .compareTo(agent.getRealPrice()) < 0) {
                return R.error("实号单条单价不能小于" + agent.getRealPrice());
            }
        }else if(goods.getCategory()==2){
            if(goods.getType()==1&&new BigDecimal(goods.getUnitPrice()).compareTo(agent.getInternationalPrice())<0){
                return R.error("国际自定义单价不能小于" + agent.getInternationalPrice());
            }else if (new BigDecimal(goods.getPrice()).divide(new BigDecimal(goods.getSpecifications()), 5, RoundingMode.DOWN)
                    .compareTo(agent.getInternationalPrice()) < 0) {
                return R.error("国际单条单价不能小于" + agent.getInternationalPrice());
            }
        }

        goods.setAgentName(agent.getCompanyName());
        goods.setId(snowflake.nextId());
        goods.setVersion(0);
        int flag = goodsMapper.insert(goods);
        if(flag!=1){
            log.error("插入套餐失败，插入数据{}",goods);
            return R.error("插入套餐表失败");
        }
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateGoods(Goods goods) throws Exception {
        Agent agent = agentMapper.selectByPrimaryKey(goods.getAgentId());
        if (new BigDecimal(goods.getPrice()).divide(new BigDecimal(goods.getSpecifications()), 5, RoundingMode.DOWN)
                .compareTo(agent.getPrice()) < 0) {
            return R.ok("单条单价不能小于" + agent.getPrice());
        }
        int i = goodsMapper.updateByPrimaryKey(goods);
        return R.ok(i==1?true:false);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteGoods(Long id) throws Exception {
        int i = goodsMapper.deleteByPrimaryKey(id);
        return i==1?false:true;
    }

    @Override
    public Goods getGoodsById(Serializable id) throws Exception {
        return goodsMapper.getGoodsById(id);
    }

    @Override
    public PageInfo getGoodsPageList(GoodsQueryParam goodsQueryParam) throws Exception {
        PageHelper.startPage(goodsQueryParam.getCurrentPage(), goodsQueryParam.getPageSize());
        List<Goods> list = goodsMapper.getGoodsPageList(goodsQueryParam);
        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public R listAgentGoods(Long agentId){
        List<Map> map = goodsMapper.listAgentGoods(agentId);
        return R.ok(map);
    }
}
