package com.cc.oem.modules.agent.controller.agent;

import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.AgentProduct;
import com.cc.oem.modules.agent.model.param.ProductLineListParam;
import com.cc.oem.modules.agent.service.AgentProductService;
import com.cc.oem.modules.sys.controller.AbstractController;
import com.cc.oem.modules.sys.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author zhangx
 * date  2019/3/25 13:38
 */
@RestController
@RequestMapping("/open/agent/product")
@Validated
public class AgentProductController extends AbstractController {

    @Autowired
    AgentProductService agentProductService;
    @Autowired
    SysUserService sysUserService;

    @SysLog("查询产品列表")
    @PostMapping("/list")
    @ApiOperation("产品列表")
    @RequiresPermissions("user:product:list")
    public R list(ProductLineListParam param) {
        Long sysUserId = sysUserService.getSysUserId();
        if (!sysUserService.judgeIfAdmin(sysUserId)) {
            param.setAgentId(getAgentId());
        }
        return agentProductService.list(param);
    }

    @SysLog("查询产品列表")
    @PostMapping("/listProductsOfAgent")
    @ApiOperation("产品列表")
    public R listProductsOfAgent(Long agentId,Integer category) {

        return agentProductService.listProductsOfAgent(agentId,category);
    }

    /**
     * state 0上架，1下架 ,2删除  ,3通过，4驳回
     */
    @SysLog("更新产品状态")
    @PostMapping("/updateStatus")
    @ApiOperation("产品列表")
    @RequiresPermissions("user:product:updateStatus")
    R updateStatus(Long id, Integer status, String remark) {
        if (id == null || status == null) {
            return R.error("缺少必要参数");
        }
        return agentProductService.updateStatus(id, status, remark);
    }

    @SysLog("编辑产品")
    @PostMapping("/saveOrUpdate")
    @ApiOperation("产品列表")
    @RequiresPermissions("user:product:saveOrUpdate")
    R saveOrUpdate(AgentProduct agentProduct) {
        if (StringUtils.isBlank(agentProduct.getName()) || agentProduct.getProductGroupId() == null) {
            return R.error("缺少必要参数");
        }
        if (StringUtils.isBlank(agentProduct.getIcon())) {
            agentProduct.setIcon(null);
        }
        if (agentProduct.getSort() == null) {
            agentProduct.setSort(0);
        }
        Long agentId = getAgentId();
        if (agentId == null) {
            return R.error("没有权限");
        }
        agentProduct.setAgentId(agentId);

        return agentProductService.saveOrUpdate(agentProduct);
    }

    @SysLog("查询产品")
    @PostMapping("/findById")
    @ApiOperation("产品列表")
    @RequiresPermissions("user:product:findById")
    R findById(Long id) {
        if (id == null) {
            return R.ok("参数有误");
        }
        return agentProductService.findById(id);
    }

    @SysLog("产品排序")
    @PostMapping("/reorder")
    @ApiOperation("产品排序")
    @RequiresPermissions("user:product:reorder")
    R reorder() {
        Long agentId = getAgentId();
        if (agentId == null) {
            return R.error("没有权限");
        }
        return agentProductService.reorder(agentId);
    }

    @SysLog("更新产品排序")
    @PostMapping("/updateOrder")
    @ApiOperation("更新产品排序")
    @RequiresPermissions("user:product:updateOrder")
    R updateOrder(Long id, Integer orderNum) {
        Long agentId = getAgentId();
        if (agentId == null) {
            return R.error("没有权限");
        }
        if (id == null || orderNum == null) {
            return R.error("缺少参数");
        }
        return agentProductService.updateOrder(id, orderNum);
    }


}
