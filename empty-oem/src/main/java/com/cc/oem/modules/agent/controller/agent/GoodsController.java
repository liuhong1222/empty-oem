package com.cc.oem.modules.agent.controller.agent;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.agentSettings.Goods;
import com.cc.oem.modules.agent.model.param.GoodsQueryParam;
import com.cc.oem.modules.agent.service.agentSettings.GoodsService;
import com.cc.oem.modules.sys.controller.AbstractController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * <pre>
 * 充值套餐管理 前端控制器
 * </pre>
 *
 * @author rivers
 * @since 2020-02-13
 */
@Slf4j
@RestController
@RequestMapping("/open/agent/goods")
@Api("充值套餐管理 API")
public class GoodsController extends AbstractController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 添加充值套餐管理
     */
    @PostMapping("/add")
    @RequiresPermissions("goods:add")
    @ApiOperation("添加Goods对象")
    public R addGoods(@Valid @RequestBody Goods goods) throws Exception {
        goods.setId(null)
                .setCreateTime(new Date())
                .setUpdateTime(new Date())
                .setVersion(0)
                .setAgentName(super.getAgentInfoByAgentId().getCompanyName());

        if (goods.getType() == 0) {
            goods.setMinPayAmount(goods.getPrice());
        } else {
            goods.setPrice(goods.getUnitPrice());
            goods.setSpecifications("1");
        }
        return goodsService.saveGoods(goods);
    }



    /**
     * 修改充值套餐管理
     */
    @PostMapping("/update")
    @RequiresPermissions("goods:update")
    @ApiOperation(value = "修改Goods对象", notes = "修改充值套餐管理")
    public R updateGoods(@Valid @RequestBody Goods goods) throws Exception {
        if (goods.getId() == null) {
            return R.error("非法请求");
        }
        goods.setAgentName(super.getAgentInfoByAgentId().getCompanyName())
                .setUpdateTime(new Date());

        if (goods.getType() == 0) {
            goods.setMinPayAmount(goods.getPrice());
        } else {
            goods.setPrice(goods.getUnitPrice());
            goods.setSpecifications("1");
        }

        return goodsService.updateGoods(goods);
    }

    /**
     * 删除充值套餐管理
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("goods:delete")
    @ApiOperation(value = "删除Goods对象", notes = "删除充值套餐管理")
    public R deleteGoods(@PathVariable("id") Long id) throws Exception {
        boolean flag = goodsService.deleteGoods(id);
        return R.ok(flag);
    }

    /**
     * 获取充值套餐管理
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("goods:detail")
    @ApiOperation(value = "获取Goods对象详情", notes = "查看充值套餐管理")
    public R getGoods(@PathVariable("id") Long id) throws Exception {
        Goods goodsQueryVo = goodsService.getGoodsById(id);
        return R.ok(goodsQueryVo);
    }

    /**
     * 充值套餐管理分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("goods:list")
    @ApiOperation(value = "获取Goods分页列表", notes = "充值套餐管理分页列表")
    public R getGoodsPageList(@Valid @RequestBody GoodsQueryParam goodsQueryParam) throws Exception {
        goodsQueryParam.setAgentId(super.getAgentId());
        PageInfo goodsPageList = goodsService.getGoodsPageList(goodsQueryParam);
        return R.ok(goodsPageList);
    }

}

