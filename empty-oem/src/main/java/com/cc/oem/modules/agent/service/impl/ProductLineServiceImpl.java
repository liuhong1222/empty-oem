package com.cc.oem.modules.agent.service.impl;

import com.cc.oem.modules.agent.constant.ProductConstant;
import com.cc.oem.common.exception.RRException;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.dao.ProductLineMapper;
import com.cc.oem.modules.agent.entity.ProductGroup;
import com.cc.oem.modules.agent.entity.ProductLineWeb;
import com.cc.oem.modules.agent.model.param.ProductLineListParam;
import com.cc.oem.modules.agent.service.ProductLineService;
import com.cc.oem.modules.config.utils.Snowflake;
import com.cc.oem.modules.sys.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * author zhangx
 */
@Service
public class ProductLineServiceImpl implements ProductLineService {

    @Autowired
    ProductLineMapper productLineMapper;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    private Snowflake snowflake;


    private int ShelfStatus_ON = 0;
    private int ShelfStatus_OFF = 1;
    private int DELETE = 5;
    private int PASS = 3;
    private int DENY = 4;

    /**
     * 查询代理商级别列表
     */
    @Override
    public R list(ProductLineListParam param) {
        param.appendTimeString();
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        List<Map> list = productLineMapper.selectListByParam(param);
        PageInfo<Map> pageInfo = new PageInfo<>(list);
        return R.ok(pageInfo);
    }


    public R doUpdateStatus(Long id, Integer status, String remark) {
        ProductGroup productGroup = productLineMapper.selectByPrimaryKey(id);
        int statusInt = status.intValue();
        if (statusInt == ShelfStatus_ON || statusInt == ShelfStatus_OFF) {//上架状态
            return updateShelfStatus(id, status);
        }
        if (statusInt == DELETE) {//删除
            return updateDelStatus(id, status);
        }

        if ((productGroup.getApplyState().intValue() == ProductConstant.CREATE_WAIT_AUDIT || productGroup.getApplyState().intValue() == ProductConstant.MODIFY_WAIT_AUDIT) && statusInt == PASS) {//审核通过
            return updateAuditPassStatus(id, remark);
        }

        if ((productGroup.getApplyState().intValue() == ProductConstant.CREATE_WAIT_AUDIT || productGroup.getApplyState().intValue() == ProductConstant.MODIFY_WAIT_AUDIT) && statusInt == DENY) {//审核驳回
            return updateAuditDenyStatus(id,remark);
        }
        return R.error();
    }

    public ProductGroup getProductLineById(Long id) {
        return productLineMapper.selectByPrimaryKey(id);
    }

    /**
     * 0上架，1下架 ,2删除  ,3通过，4驳回
     */
    @Override
    public R updateStatus(Long id, Integer status, String remark) {
        ProductGroup productGroup = productLineMapper.selectByPrimaryKey(id);

        if (status.intValue() == ShelfStatus_ON || status.intValue() == ShelfStatus_OFF) {//上架状态
            return updateShelfStatus(id, status);
        }

        if (status.intValue() == DELETE) {//删除
            return updateDelStatus(id, status);
        }

        if ((productGroup.getApplyState().intValue() == ProductConstant.CREATE_WAIT_AUDIT || productGroup.getApplyState().intValue() == ProductConstant.MODIFY_WAIT_AUDIT) && status.intValue() == PASS) {//审核通过
            return updateAuditPassStatus(id,remark);
        }

        if ((productGroup.getApplyState().intValue() == ProductConstant.CREATE_WAIT_AUDIT || productGroup.getApplyState().intValue() == ProductConstant.MODIFY_WAIT_AUDIT) && status.intValue() == DENY) {//审核驳回
            return updateAuditDenyStatus(id,remark);
        }
        return R.error("请检查参数或重试");
    }

    public R updateShelfStatus(Long id, Integer status) {
        ProductGroup updateObject = new ProductGroup();
        updateObject.setId(id);
        updateObject.setState(status);
        updateObject.setUpdateTime(new Date());
        int count = productLineMapper.updateByPrimaryKeySelective(updateObject);
        if (count != 1) {
            return R.error("请重试");
        }
        return R.ok();
    }

    public R updateDelStatus(Long id, Integer status) {
        ProductGroup updateObject = new ProductGroup();
        updateObject.setId(id);
        updateObject.setApplyState(status);
        int count = productLineMapper.updateByPrimaryKeySelective(updateObject);
        if (count != 1) {
            return R.error("请重试");
        }
        return R.ok();
    }


    public R updateAuditPassStatus(Long id,String remark) {
        ProductGroup updateObject = new ProductGroup();
        updateObject.setId(id);
        updateObject.setRemark(remark);
        updateObject.setUpdateTime(new Date());
        updateObject.setApplyState(ProductConstant.AUDITED);

        int count = productLineMapper.updateByPrimaryKeySelective(updateObject);
        if (count != 1) {
            return R.error("请重试");
        }
        ProductGroup oldLine = productLineMapper.selectByPrimaryKey(id);
        ProductLineWeb lineWeb = new ProductLineWeb();
        BeanUtils.copyProperties(oldLine, lineWeb);
        return R.ok();
    }

    public R updateAuditDenyStatus(Long id,String remark) {
        ProductGroup productGroup = productLineMapper.selectByPrimaryKey(id);
        if (StringUtils.isBlank(remark)) {
            return R.error("请填写驳回原因");
        }
        ProductGroup updateObject = new ProductGroup();
        updateObject.setId(id);

        updateObject.setRemark(remark);
        updateObject.setUpdateTime(new Date());
        if (productGroup.getApplyState() != null) {
            if (productGroup.getApplyState().intValue() == ProductConstant.CREATE_WAIT_AUDIT) {
                updateObject.setApplyState(ProductConstant.CREATE_REJECT);
            } else if (productGroup.getApplyState().intValue() == ProductConstant.MODIFY_WAIT_AUDIT) {
                updateObject.setApplyState(ProductConstant.MODIFY_WAIT_REJECT);
            } else {
                return R.error("状态不对，刷新重试");
            }
        }
        int count = productLineMapper.updateByPrimaryKeySelective(updateObject);
        if (count != 1) {
            return R.error("请重试");
        }
        return R.ok();
    }

    /**
     * 0上架，1下架
     */
    public R saveOrUpdate(ProductGroup productGroup) {

        if (productGroup.getId() == null) {//新增
            return insert(productGroup);
        }

        return update(productGroup);
    }

    public R update(ProductGroup productGroup) {
        Date now = new Date();
        productGroup.setUpdateTime(now);
        //修改
        ProductGroup oldLine = productLineMapper.selectByPrimaryKey(productGroup.getId());
        if (oldLine == null) {
            return R.error("参数有误!");
        }
        Map map = Maps.newHashMap();
        map.put("agentId", productGroup.getAgentId());
        map.put("name", productGroup.getName());
        List<Map> list = productLineMapper.selectOneByMap(map);
        if (list.size() > 1) {
            return R.error("产品线名称重复!");
        }
        if (list.size() == 1) {
            Map resultMap = list.get(0);
            Long resultId = (Long) resultMap.get("logId");
            if (productGroup.getId() != null && resultId.longValue() != productGroup.getId().longValue()) {
                return R.error("产品线名称重复!");
            }
        }

        if (oldLine.getApplyState() != null) {
            if (oldLine.getApplyState().intValue() == ProductConstant.CREATE_WAIT_AUDIT) {
                productGroup.setApplyState(ProductConstant.CREATE_WAIT_AUDIT);
            }
            if (oldLine.getApplyState().intValue() == ProductConstant.CREATE_REJECT) {
                productGroup.setApplyState(ProductConstant.CREATE_WAIT_AUDIT);
            } else {
                productGroup.setApplyState(ProductConstant.MODIFY_WAIT_AUDIT);
            }
        }

        int count = productLineMapper.updateByPrimaryKeySelective(productGroup);
        if (count == 1) {
            return R.ok();
        }
        return R.error("请重试");
    }

    public R insert(ProductGroup productGroup) {
        Date now = new Date();
        productGroup.setUpdateTime(now);
        /**agentId ,productLineName*/
        productGroup.setCreateTime(now);
        productGroup.setUpdateTime(now);
        productGroup.setApplyState(ProductConstant.CREATE_WAIT_AUDIT);
        Map map = Maps.newHashMap();
        map.put("agentId", productGroup.getAgentId());
        map.put("name", productGroup.getName());
        List<Map> list = productLineMapper.selectOneByMap(map);
        if (list.size() > 0) {
            return R.error("产品线名称重复!");
        }
        if (productGroup.getSort() == null) {
            productGroup.setSort(0);
        }
        productGroup.setId(snowflake.nextId());
        int count = productLineMapper.insert(productGroup);
        if (count == 1) {
            return R.ok();
        }
        return R.error("请重试");
    }

    public R findById(String id) {
        Map result = productLineMapper.findById(Long.valueOf(id));
        R r = R.ok(result);
        return r;
    }

    public R findNameListByName(String productLineName, Long agentId) {

        return R.ok(productLineMapper.findNameListByAgentId(agentId, productLineName));
    }

    @Transactional(rollbackFor = {Throwable.class})
    public R reorder(Long agentId) {
        List<Map> mapList = productLineMapper.selectOrderList(agentId);
        if (mapList.size() > 0) {
            int count = productLineMapper.updateOrderBatch(mapList);
            if (count != mapList.size()) {
                throw new RRException("排序异常，请重试");
            }
        }
        return R.ok();
    }

    @Transactional(rollbackFor = {Throwable.class})
    public R updateOrder(Long id, Integer orderNum) {
        ProductGroup productGroup = new ProductGroup();
        productGroup.setId(id);
        productGroup.setSort(orderNum);
        int count = productLineMapper.updateByPrimaryKeySelective(productGroup);
        if (count != 1) {
            return R.error("更新失败，请重试");
        }
        return R.ok();
    }


}
