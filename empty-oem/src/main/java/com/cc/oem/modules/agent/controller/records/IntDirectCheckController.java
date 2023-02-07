package com.cc.oem.modules.agent.controller.records;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.records.IntDirectQueryParam;
import com.cc.oem.modules.agent.service.records.IntDirectCheckService;
import com.cc.oem.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 国际定向检测记录
 * @author liuh
 * @date 2022年10月21日
 */
@Slf4j
@RestController
@RequestMapping("/open/agent/intDirectCheck")
@Api("国际定向检测记录")
public class IntDirectCheckController extends AbstractController {

    @Autowired
    private IntDirectCheckService intDirectCheckService;

    /**
     * 国际定向检测记录分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("intDirect:list")
    @ApiOperation(value = "获取intDirectCheck分页列表", notes = "国际定向检测记录分页列表")
    public R getInternationalPageList(@Valid @RequestBody IntDirectQueryParam param) throws Exception {
        if(!getIsAdmin()){
        	param.setAgentId(getAgentId());
        }
        return intDirectCheckService.getIntDirectPageList(param);
    }
}

