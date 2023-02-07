package com.cc.oem.modules.agent.controller.records;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.model.param.records.InternationalQueryParam;
import com.cc.oem.modules.agent.service.records.InternationalCheckService;
import com.cc.oem.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 国际检测记录
 * @author liuh
 * @date 2022年6月13日
 */
@Slf4j
@RestController
@RequestMapping("/open/agent/internationalCheck")
@Api("国际检测记录")
public class InternationalCheckController extends AbstractController {

    @Autowired
    private InternationalCheckService internationalCheckService;

    /**
     * 国际检测记录分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("international:list")
    @ApiOperation(value = "获取InternationalCheck分页列表", notes = "国际检测记录分页列表")
    public R getInternationalPageList(@Valid @RequestBody InternationalQueryParam param) throws Exception {
        if(!getIsAdmin()){
        	param.setAgentId(getAgentId());
        }
        return internationalCheckService.getInternationalPageList(param);
    }
}

