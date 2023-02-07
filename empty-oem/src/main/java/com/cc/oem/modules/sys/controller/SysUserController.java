package com.cc.oem.modules.sys.controller;

import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.common.validator.Assert;
import com.cc.oem.common.validator.ValidatorUtils;
import com.cc.oem.common.validator.group.AddGroup;
import com.cc.oem.common.validator.group.UpdateGroup;
import com.cc.oem.modules.sys.entity.SysUserEntity;
import com.cc.oem.modules.sys.form.PasswordForm;
import com.cc.oem.modules.sys.service.SysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统用户
 *
 * @author wade_woke
 * @update 2021年10月28日 上午10:40:10
 */
@RestController
@RequestMapping("/open/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据传入参数展示管理员列表与用户列表
     * role_id 1管理员，2代理商
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(String phone,String roleId,Integer currentPage,Integer pageSize,Long agentId) {
        return sysUserService.queryPage(phone,roleId,currentPage,pageSize,agentId);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info() {
        return R.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    public R password(@RequestBody PasswordForm form) {
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");

        //sha256加密
        String password = DigestUtils.sha256Hex(form.getPassword()+getUser().getSalt());
        //sha256加密
        String newPassword = DigestUtils.sha256Hex(form.getNewPassword()+getUser().getSalt());

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return R.error("原密码不正确");
        }

        return R.ok();
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.selectById(userId);
        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        sysUserService.save(user);

        return R.ok();
    }


    /**
     * 保存代理商使用用户
     */
    @SysLog("保存代理商使用用户")
    @PostMapping("/addAgentUser")
    @RequiresPermissions("sys:user:addAgentUser")
    public R addAgentUser(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        sysUserService.save(user);
        return R.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        return sysUserService.update(user);

    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error("当前用户不能删除");
        }

        sysUserService.deleteBatch(userIds);

        return R.ok();
    }
}
