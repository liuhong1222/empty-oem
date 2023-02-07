package com.cc.oem.modules.agent.controller.records;

import cn.hutool.core.date.DateUtil;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.entity.records.RealtimeCheck;
import com.cc.oem.modules.agent.model.data.records.RealtimeCheckQueryVo;
import com.cc.oem.modules.agent.model.param.records.RealtimeCheckQueryParam;
import com.cc.oem.modules.agent.service.records.RealtimeCheckService;
import com.cc.oem.modules.sys.controller.AbstractController;
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
 * 实时检测记录 前端控制器
 * </pre>
 * cvs_file_path
 */
@Slf4j
@RestController
@RequestMapping("/open/agent/realtimeCheck")
@Api("实时检测记录 API")
public class RealtimeCheckController extends AbstractController {

    @Autowired
    private RealtimeCheckService realtimeCheckService;

    /**
     * 获取实时检测记录
     */
    @GetMapping("/info")
    @RequiresPermissions("realtime:detail")
    @ApiOperation(value = "获取RealtimeCheck对象详情", notes = "查看实时检测记录")
    public R getRealtimeCheck(Long id) throws Exception {
        RealtimeCheckQueryVo realtimeCheckQueryVo = realtimeCheckService.getRealtimeCheckById(id);
        return R.ok(realtimeCheckQueryVo);
    }

    /**
     * 实时检测记录分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("realtime:list")
    @ApiOperation(value = "获取RealtimeCheck分页列表", notes = "实时检测记录分页列表")
    public R getRealtimeCheckPageList(@Valid @RequestBody RealtimeCheckQueryParam realtimeCheckQueryParam) throws Exception {
        if(!getIsAdmin()){
            realtimeCheckQueryParam.setAgentId(getAgentId());
        }
        return realtimeCheckService.getRealtimeCheckPageList(realtimeCheckQueryParam);
    }

    /**
     * 获取实时检测API记录
     */
    @GetMapping("/info/api")
    @RequiresPermissions("realtime:api:detail")
    @ApiOperation(value = "获取RealtimeCheck对象详情", notes = "查看实时检测API记录")
    public R getRealtimeApiCheck(Long id) throws Exception {
        RealtimeCheckQueryVo realtimeCheckQueryVo = realtimeCheckService.getRealtimeApiCheck(id);
        return R.ok(realtimeCheckQueryVo);
    }

    /**
     * 实时检测API记录分页列表
     */
    @PostMapping("/getApiList")
    @RequiresPermissions("realtime:api:list")
    @ApiOperation(value = "获取RealtimeCheck分页列表", notes = "实时检测API记录分页列表")
    public R getRealtimeCheckApiList(@Valid @RequestBody RealtimeCheckQueryParam realtimeCheckQueryParam) throws Exception {
        if(!getIsAdmin()){
            realtimeCheckQueryParam.setAgentId(getAgentId());
        }
        return realtimeCheckService.getRealtimeCheckApiList(realtimeCheckQueryParam);
    }
}

