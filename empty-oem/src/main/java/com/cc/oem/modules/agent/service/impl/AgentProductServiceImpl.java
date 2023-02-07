package com.cc.oem.modules.agent.service.impl;

import com.cc.oem.modules.agent.constant.ProductConstant;
import com.cc.oem.common.exception.RRException;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.dao.AgentProductMapper;
import com.cc.oem.modules.agent.dao.ProductLineMapper;
import com.cc.oem.modules.agent.entity.AgentProduct;
import com.cc.oem.modules.agent.entity.AgentProductWeb;
import com.cc.oem.modules.agent.model.param.ProductLineListParam;
import com.cc.oem.modules.agent.service.AgentProductService;
import com.cc.oem.modules.config.utils.Snowflake;
import com.cc.oem.modules.sys.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * author zhangx
 * date  2019/3/25 14:41
 */
@Service
public class AgentProductServiceImpl implements AgentProductService {

    @Autowired
    AgentProductMapper agentProductMapper;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    ProductLineMapper productLineMapper;
    @Autowired
    private Snowflake snowflake;

    //    s 0上架，1下架 ,5删除  ,3通过，4驳回
    private int ShelfStatus_ON = 0;
    private int ShelfStatus_OFF = 1;
    private int DELETE = 5;
    private int PASS = 3;
    private int DENY = 4;
    private int DELETE_STATUS = 5;

    /**
     * 查询产品列表
     */
    @Override
    public R list(ProductLineListParam param) {
        param.appendTimeString();
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        List<Map> list = agentProductMapper.selectListByParam(param);
        PageInfo<Map> pageInfo = new PageInfo<>(list);
        return R.ok(pageInfo);
    }

    /**
     * 0上架，1下架 ,2删除,3通过，4驳回
     */
    @Override
    public R updateStatus(Long id, Integer status, String remark) {
        AgentProduct agentProduct = agentProductMapper.selectByPrimaryKey(id);
        if (agentProduct == null) {
            return R.error("参数不正确");
        }

        if (status.intValue() == ShelfStatus_ON || status.intValue() == ShelfStatus_OFF || status.intValue() == DELETE) {//检查权限
            Long agentId = sysUserService.selectAgentIdBySysUserId(sysUserService.getSysUserId());
            if (agentId == null || agentId.longValue() != agentProduct.getAgentId().longValue()) {
                return R.error("没有权限");
            }
        } else if (status.intValue() == PASS || status.intValue() == DENY) {
            Long sysUserId = sysUserService.getSysUserId();
            if (!sysUserService.judgeIfAdmin(sysUserId)) {//不是管理员
                return R.error("没有权限");
            }
        } else {
            return R.error("参数不正确");
        }

        if (status.intValue() == ShelfStatus_ON || status.intValue() == ShelfStatus_OFF) {//上架状态
            return updateShelfStatus(id, status, remark);
        }

        if (status.intValue() == DELETE) {//删除
            return updateDelStatus(id, status, remark);
        }

        if ((agentProduct.getApplyState().intValue() == ProductConstant.CREATE_WAIT_AUDIT || agentProduct.getApplyState().intValue() == ProductConstant.MODIFY_WAIT_AUDIT) && status.intValue() == PASS) {//审核通过
            return updateAuditPassStatus(id, status, remark);
        }

        if ((agentProduct.getApplyState().intValue() == ProductConstant.CREATE_WAIT_AUDIT || agentProduct.getApplyState().intValue() == ProductConstant.MODIFY_WAIT_AUDIT) && status.intValue() == DENY) {//审核驳回
            return updateAuditDenyStatus(id, status, remark);
        }
        return R.error("请检查参数或重试");
    }

    /**
     * 0上架，1下架
     */
    public R saveOrUpdate(AgentProduct agentProduct) {
        if (agentProduct.getId() == null) {//新增
            return insert(agentProduct);
        }
        return update(agentProduct);
    }

    public R findById(Long id) {
        Map result = agentProductMapper.findById(id);
        R r = R.ok();
        r.put("data", result);
        return r;
    }

    @Transactional(rollbackFor = {Throwable.class})
    public R reorder(Long agentId) {
        List<Map> list = productLineMapper.selectOrderList(agentId);
        for (Map map : list) {
            Long productLineId = (Long) map.get("id");
            List<Map> mapList = agentProductMapper.selectOrderList(productLineId);
            if (mapList.size() > 0) {
                int count = agentProductMapper.updateOrderBatch(mapList);
                if (count != mapList.size()) {
                    throw new RRException("排序异常，请重试");
                } else {
//                    agentProductMapper.updateWebOrderBatch(mapList);
                }
            }
        }
        return R.ok();
    }

    @Transactional(rollbackFor = {Throwable.class})
    public R updateOrder(Long id, Integer orderNum) {
        AgentProduct agentProduct = new AgentProduct();
        agentProduct.setId(id);
        agentProduct.setSort(orderNum);
        int count = agentProductMapper.updateByPrimaryKeySelective(agentProduct);
        if (count != 1) {
            return R.error("更新失败，请重试");
        }
        AgentProductWeb object = new AgentProductWeb();
        object.setId(id);
        object.setOrderNum(orderNum);
        return R.ok();
    }

    public R update(AgentProduct agentProduct) {
        AgentProduct oldProduct = agentProductMapper.selectByPrimaryKey(agentProduct.getId());
        if (oldProduct == null) {
            return R.error("参数有误!");
        }
        Map map = Maps.newHashMap();
        map.put("agentId", agentProduct.getAgentId());
        map.put("productName", agentProduct.getName());
        List<Map> list = agentProductMapper.selectOneByMap(map);
        if (list.size() > 1) {
            return R.error("产品线名称重复!");
        }
        if (list.size() == 1) {
            Map resultMap = list.get(0);
            Long resultId = (Long) resultMap.get("id");
            if (resultId.longValue() != agentProduct.getId().longValue()) {
                return R.error("产品线名称重复!");
            }
        }
        Date now = new Date();
        agentProduct.setUpdateTime(now);

        if (oldProduct.getApplyState() != null) {
            if (oldProduct.getApplyState().intValue() == ProductConstant.CREATE_WAIT_AUDIT) {
                oldProduct.setApplyState(ProductConstant.CREATE_WAIT_AUDIT);
            } else if (oldProduct.getApplyState().intValue() == ProductConstant.CREATE_REJECT) {
                agentProduct.setApplyState(ProductConstant.CREATE_WAIT_AUDIT);
            } else {
                agentProduct.setApplyState(ProductConstant.MODIFY_WAIT_AUDIT);
            }
        }

        int count = agentProductMapper.updateByPrimaryKeySelective(agentProduct);
        if (count == 1) {
            return R.ok();
        } else {
            return R.error("请重试");
        }
    }

        public R insert (AgentProduct agentProduct){
            /**agentId ,productName*/
            Map map = Maps.newHashMap();
            map.put("agentId", agentProduct.getAgentId());
            map.put("productName", agentProduct.getName());
            List<Map> list = agentProductMapper.selectOneByMap(map);
            if (list.size() > 0) {
                return R.error("产品名称重复!");
            }
            agentProduct.setId(snowflake.nextId());
            agentProduct.setApplyState(ProductConstant.CREATE_WAIT_AUDIT);
            int count = agentProductMapper.insert(agentProduct);
            if (count == 1) {
                return R.ok();
            }
            return R.error("请重试");
        }

        public R updateAuditDenyStatus (Long id, Integer status, String remark){
            AgentProduct updateObject = new AgentProduct();
            updateObject.setId(id);
            if (StringUtils.isBlank(remark)) {
                return R.error("请填写驳回原因");
            }
            updateObject.setApplyState(ProductConstant.MODIFY_WAIT_REJECT);
            updateObject.setRemark(remark);
            int count = agentProductMapper.updateByPrimaryKeySelective(updateObject);
            if (count != 1) {
                return R.error("请重试");
            }
            return R.ok();
        }

        public R updateAuditPassStatus (Long id, Integer status, String remark){
            AgentProduct updateObject = new AgentProduct();
            updateObject.setId(id);
            updateObject.setUpdateTime(new Date());
            updateObject.setApplyState(status);
            updateObject.setRemark(remark);
            int count = agentProductMapper.updateByPrimaryKeySelective(updateObject);
            if (count != 1) {
                return R.error("请重试");
            }
            return R.ok();
        }

        public R updateShelfStatus (Long id, Integer status, String remark){
            AgentProduct updateObject = new AgentProduct();
            updateObject.setId(id);
            updateObject.setUpdateTime(new Date());
            updateObject.setState(status);
            int count = agentProductMapper.updateByPrimaryKeySelective(updateObject);
            if (count != 1) {
                return R.error("请重试");
            }
            return R.ok();
        }

        public R updateDelStatus (Long id, Integer status, String remark){
            AgentProduct updateObject = new AgentProduct();
            updateObject.setId(id);
            updateObject.setUpdateTime(new Date());
            updateObject.setApplyState(DELETE_STATUS);
            int count = agentProductMapper.updateByPrimaryKeySelective(updateObject);
            if (count != 1) {
                return R.error("请重试");
            }
            return R.ok();
        }

        public R listProductsOfAgent (Long agentId, Integer category){
            List<Map> list = agentProductMapper.listProductsOfAgent(agentId, category);
            return R.ok(list);
        }

}
