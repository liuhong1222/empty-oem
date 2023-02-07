package com.cc.oem.modules.agent.controller;



import com.cc.oem.common.annotation.RepeatCommitToken;
import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.FaqAuditParam;
import com.cc.oem.modules.agent.model.param.ProductFaqParam;
import com.cc.oem.modules.agent.model.param.ProductFaqSaveParam;
import com.cc.oem.modules.agent.model.param.UpdateFaqOrderParam;
import com.cc.oem.modules.agent.service.ProductFaqService;
import com.cc.oem.modules.sys.controller.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

/**
 *     常见问题
 * @author liuh
 * @fileName ProductFaqController
 * @date 2019/03/25 17:30
 */
@RestController
@RequestMapping("/open/agent/productFaq")
public class ProductFaqController extends AbstractController {

    @Autowired
    ProductFaqService productFaqService;

    @SysLog("查询常见问题审核列表")
    @PostMapping("/all/list")
    @ApiOperation("查询常见问题审核列表")
    @RequiresPermissions("productFaq:all:list")
    public R productFaqAllList(ProductFaqParam param) {
        return productFaqService.productFaqAllList(param);
    }

    @SysLog("审核常见问题")
    @PostMapping("/all/audit")
    @ApiOperation("审核常见问题")
    @RepeatCommitToken
    @RequiresPermissions("productFaq:all:audit")
    public R productFaqAllAudit(@Valid FaqAuditParam param) {
        return productFaqService.productFaqAllAudit(param, getUserId());
    }

    @SysLog("上下架")
    @PostMapping("/all/updateStatus")
    @ApiOperation("上下架")
    @RepeatCommitToken
    @RequiresPermissions("productFaq:state:update")
    public R updateStatus(Integer state,Long faqId) {
        return productFaqService.updateStatus(state,faqId);
    }

    @SysLog("查看常见问题详情")
    @PostMapping("/all/detail")
    @ApiOperation("查看常见问题详情")
    @RequiresPermissions("productFaq:all:detail")
    public R productFaqAllDetail(@Valid String productFaqId) {
        return productFaqService.productFaqAllDetail(productFaqId);
    }

    @SysLog("查询我的常见问题列表")
    @PostMapping("/my/list")
    @ApiOperation("查询我的常见问题列表")
    @RequiresPermissions("productFaq:my:list")
    public R productFaqMyList(ProductFaqParam param) {
        return productFaqService.productFaqMyList(param, getAgentId());
    }


    @SysLog("发布我的常见问题")
    @PostMapping({"/my/save"})
    @ApiOperation("发布我的常见问题")
    @RepeatCommitToken
    @RequiresPermissions(value = {"productFaq:my:save"})
    public synchronized R productFaqMySave(@Valid ProductFaqSaveParam param) {
        return productFaqService.productFaqMySave(param,getAgentId());
    }

    @SysLog("修改我的常见问题")
    @PostMapping("/my/update")
    @ApiOperation("修改我的常见问题")
    @RequiresPermissions("productFaq:my:update")
    public R productFaqMyUpdate(@Valid ProductFaqSaveParam param) {
        return productFaqService.productFaqMyUpdate(param, getUserId(), getAgentId());
    }


    @SysLog("查看我的常见问题详情")
    @GetMapping("/my/detail")
    @ApiOperation("查看我的常见问题详情")
    @RequiresPermissions("productFaq:my:detail")
    public R productFaqMyDetail(@Valid String productFaqId) {
        return productFaqService.productFaqMyDetail(productFaqId, getAgentId());
    }


    @SysLog("删除我的常见问题")
    @PostMapping("/my/delete")
    @ApiOperation("删除我的常见问题")
    @RepeatCommitToken
    @RequiresPermissions("productFaq:my:delete")
    public R productFaqMyDelete(@Validated String productFaqId) {
        return productFaqService.productFaqMyDelete(productFaqId,getAgentId());
    }

    @SysLog("根据产品名称获取产品相关信息")
    @PostMapping("/my/getProductInfo")
    @ApiOperation("根据产品名称获取产品相关信息")
    @RepeatCommitToken
    public R getProductInfo(@Validated String productName) {
        return productFaqService.getProductInfo(productName, getAgentId());
    }

    @SysLog("修改常见问题排序")
    @PostMapping("/my/updateFaqOrder")
    @ApiOperation("修改常见问题排序")
    @RepeatCommitToken
    @RequiresPermissions("productFaq:my:updateFaqOrder")
    public R updateFaqOrder(@Valid UpdateFaqOrderParam param, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return R.error(bindingResult.getFieldError().getDefaultMessage());
        }
        return productFaqService.updateFaqOrder(param ,getUserId(), getAgentId());
    }

    @SysLog("对常见问题重新排序")
    @PostMapping("/my/againOrder")
    @ApiOperation("对常见问题重新排序")
    @RepeatCommitToken
    @RequiresPermissions("productFaq:my:againOrder")
    public R againOrder() {
        return productFaqService.againOrder(getAgentId());
    }
}
