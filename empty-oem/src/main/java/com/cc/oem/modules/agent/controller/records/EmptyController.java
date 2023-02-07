package com.cc.oem.modules.agent.controller.records;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.records.EmptyCheck;
import com.cc.oem.modules.agent.model.param.records.EmptyCheckQueryParam;
import com.cc.oem.modules.agent.service.records.EmptyCheckService;
import com.cc.oem.modules.sys.controller.AbstractController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <pre>
 * 空号检测记录 前端控制器
 * </pre>
 *
 */
@Slf4j
@RestController
@RequestMapping("/open/agent/empty")
@Api("空号检测记录 API")
public class EmptyController extends AbstractController {

    @Autowired
    private EmptyCheckService emptyCheckService;

    /**
     * 获取一条空号检测记录详细情况
     */
    @GetMapping("/getEmptyCheckDetailById")
    @RequiresPermissions("empty:detail")
    @ApiOperation(value = "获取最新的Empty对象详情", notes = "查看最新的空号检测记录")
    public R getEmptyCheckDetailById(Long id) throws Exception {
        EmptyCheck emptyCheck = emptyCheckService.getEmptyCheckById(id);
        if (emptyCheck == null) {
            return R.ok();
        }
        return R.ok(emptyCheck);
    }

    /**
     * 空号检测记录分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("empty:list")
    @ApiOperation(value = "获取Empty分页列表", notes = "空号检测记录分页列表")
    public R getEmptyPageList(@Valid @RequestBody EmptyCheckQueryParam emptyQueryParam) throws Exception {
        if(!getIsAdmin()){
            emptyQueryParam.setAgentId(getAgentId());
        }
        return emptyCheckService.getEmptyCheckPageList(emptyQueryParam);
    }

    /**
     * 获取一条空号检测API记录详细情况
     */
    @GetMapping("/getEmptyCheckApiDetailById")
    @RequiresPermissions("empty:api:detail")
    @ApiOperation(value = "获取最新的Empty对象详情", notes = "查看最新的空号检测API记录")
    public R getEmptyCheckApiDetailById(Long id) throws Exception {
        EmptyCheck emptyCheck = emptyCheckService.getEmptyCheckApiDetailById(id);
        if (emptyCheck == null) {
            return R.ok();
        }
        return R.ok(emptyCheck);
    }

    /**
     * 空号检测记录分页列表
     */
    @PostMapping("/getEmptyApiList")
    @RequiresPermissions("empty:api:list")
    @ApiOperation(value = "获取Empty分页列表", notes = "空号检测API记录分页列表")
    public R getEmptyApiList(@Valid @RequestBody EmptyCheckQueryParam emptyQueryParam) throws Exception {
        if(!getIsAdmin()){
            emptyQueryParam.setAgentId(getAgentId());
        }
        return emptyCheckService.getEmptyApiList(emptyQueryParam);
    }

}

