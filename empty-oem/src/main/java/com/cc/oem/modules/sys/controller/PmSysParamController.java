package com.cc.oem.modules.sys.controller;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.sys.service.PmSysParamService;
import com.cc.oem.modules.sys.vo.data.PmSysParam;
import com.cc.oem.modules.sys.vo.data.PmSysParamQueryParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 参数管理
 * @author xiaoybb
 * @date 2021-03-28
 */
@RestController
@RequestMapping("/open/sys/param")
public class PmSysParamController {
    @Autowired
    private PmSysParamService pmSysParamService;

    /**
     * 分页条件查询参数管理列表
     * @date 2021/3/28
     * @param param
     * @return com.chuanglan.pm.base.response.Result
     */
    @PostMapping("/list")
    @RequiresPermissions("sys:param:list")
    public R list(PmSysParamQueryParam param) {
        return pmSysParamService.ParamPageByCondition(param);
    }

    /**
     * 修改单个参数
     * @date 2021/3/28
     * @param pmSysParam
     * @return com.chuanglan.pm.base.response.Result
     */
    @PutMapping("/update")
    @RequiresPermissions("sys:param:update")
    public R updateOne(@Validated @RequestBody PmSysParam pmSysParam) {
        return pmSysParamService.updateOne(pmSysParam);
    }

    /**
     * 删除单个参数
     * @date 2021/3/28
     * @param id
     * @return com.chuanglan.pm.base.response.Result
     */
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:param:delete")
    public R deleteOne(Integer id) {
        return pmSysParamService.deleteOne(id);
    }

    /**
     * 新增参数
     * @date 2021/4/23
     * @param param
     * @return com.chuanglan.pm.base.response.Result
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:param:save")
    public R save(@Validated @RequestBody PmSysParam param) {
        return pmSysParamService.save(param);
    }

    /**
     * 通过id查询参数详情
     * @date 2021/4/23
     * @param id
     * @return com.chuanglan.pm.base.response.Result
     */
    @PostMapping("/queryById")
    @RequiresPermissions("sys:param:detail")
    public R queryById(Integer id) {
        return pmSysParamService.queryById(id);
    }

    /**
     * 通过参数名查询参数
     * @date 2021/8/6
     * @param paramName
     * @return com.chuanglan.pm.base.response.Result
     */
    @PostMapping("/getByName")
    public R getByName(String paramName) {
        PmSysParam pmSysParam = pmSysParamService.findOneByCache(paramName);
        return R.ok(pmSysParam);
    }

}
