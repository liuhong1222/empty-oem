package com.cc.oem.modules.agent.controller;

import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.ProductGroup;
import com.cc.oem.modules.agent.model.param.ProductLineListParam;
import com.cc.oem.modules.agent.service.ProductLineService;
import com.cc.oem.modules.sys.controller.AbstractController;
import com.cc.oem.modules.sys.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wade
 *  产品线管理
 *
 * 对应表 product_group
 */
@RestController
@RequestMapping("/open/agent/line")
@Validated
public class ProductLineController extends AbstractController {

    @Autowired
    ProductLineService productLineService;
    @Autowired
    SysUserService sysUserService;

    @SysLog("查询产品线列表")
    @PostMapping("/list")
    @ApiOperation("产品线列表")
    @RequiresPermissions("user:line:list")
    public R list(ProductLineListParam param) {
        param.setAgentId(super.getAgentId());
        return productLineService.list(param);
    }


    /**
     * @param id     产品Id
     * @param status 0上架，1下架 ,2删除  ,3通过，4驳回
     * @param remark 驳回备注
     * @return
     */
    @SysLog("更新产品线状态")
    @PostMapping("/updateStatus")
    @ApiOperation("产品线列表")
    @RequiresPermissions("user:line:updateStatus")
    R updateStatus(Long id, Integer status, String remark) {
        return productLineService.updateStatus(id, status, remark);
    }

    @SysLog("编辑产品线")
    @PostMapping("/saveOrUpdate")
    @ApiOperation("产品线列表")
    @RequiresPermissions("user:line:saveOrUpdate")
    R saveOrUpdate(@RequestBody @Validated ProductGroup productGroup) {//state 0上架，1下架
        productGroup.setAgentId(getAgentId());
        return productLineService.saveOrUpdate(productGroup);
    }

    @SysLog("查询产品线")
    @PostMapping("/findById")
    @ApiOperation("产品线列表")
    @RequiresPermissions("user:line:findById")
    R findById(String id) {
        if (id == null) {
            return R.error("参数有误");
        }
        return productLineService.findById(id);
    }

    @SysLog("产品线排序")
    @PostMapping("/reorder")
    @ApiOperation("产品线排序")
    @RequiresPermissions("user:line:reorder")
    R reorder() {
        Long agentId = getAgentId();
        if (agentId == null) {
            return R.error("没有权限");
        }
        return productLineService.reorder(agentId);
    }

    @SysLog("更新产品线排序")
    @PostMapping("/updateOrder")
    @ApiOperation("更新产品线排序")
    @RequiresPermissions("user:line:updateOrder")
    R updateOrder(Long id, Integer sort) {
        Long agentId = getAgentId();
        if (agentId == null) {
            return R.error("没有权限");
        }
        if (id == null || sort == null) {
            return R.error("缺少参数");
        }
        return productLineService.updateOrder(id, sort);
    }

    @SysLog("查询代理商产品线名称")
    @RequestMapping("/findNameList")
    @ApiOperation("查询代理商产品线名称")
    @RequiresPermissions("user:line:findNameList")
    R findNameList(String productLineName) {
        Long agentId = getAgentId();
        if (agentId == null) {
            return R.error("没有权限");
        }
        if (productLineName != null) {
            productLineName = "%" + productLineName + "%";
        }
        return productLineService.findNameListByName(productLineName, agentId);
    }

}
