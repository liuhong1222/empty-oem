package com.cc.oem.modules.sys.controller;

import com.alibaba.druid.util.StringUtils;
import com.cc.oem.common.redis.RedisClient;
import com.cc.oem.common.utils.ConfigConstant;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.sys.entity.SysUserEntity;
import com.cc.oem.modules.sys.form.CheckLoginForm;
import com.cc.oem.modules.sys.form.SysLoginForm;
import com.cc.oem.modules.sys.service.SysCaptchaService;
import com.cc.oem.modules.sys.service.SysSendMsgService;
import com.cc.oem.modules.sys.service.SysUserService;
import com.cc.oem.modules.sys.service.SysUserTokenService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 登录相关
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
@RequestMapping("/open")
public class SysLoginController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private SysCaptchaService sysCaptchaService;
    @Autowired
    private SysSendMsgService sysSendMsgService;
//    @Autowired
//    private RedissonClient redissonClient;
    @Autowired
    private RedisClient redisClient;

    /**
     * 验证码
     */
    @GetMapping("/captcha2")
    public void captchaJpg(HttpServletResponse response, String uuid) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        sysCaptchaService.getCaptcha(uuid, response.getOutputStream());

        ServletOutputStream out = response.getOutputStream();
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     */
    @PostMapping("/sys/login")
    public Map<String, Object> login(@RequestBody SysLoginForm form) throws IOException {
/*		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
		if(!captcha){
			return R.error("验证码不正确");
		}*/

        //用户信息
        SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

        if(null == user){
            return R.error("账户信息不存在,请重新确认后再登录~");
        }
        System.out.println(new Sha256Hash(form.getPassword()+user.getSalt()).toHex());
        System.out.println(DigestUtils.sha256Hex(form.getPassword()+user.getSalt()));
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()+user.getSalt()))) {
            return R.error("账号或密码不正确");
        }
        //YevOBm0GwMASZKBADrCf
        //c698236d3c2e61f03367bfc2aab979cc5f499c5f810dd4e68dd18266a482e15f
        //账号锁定
        if (user.getState() == 0) {
            return R.error("账号已被锁定,请联系管理员");
        }

        if(!sysSendMsgService.SendMsg(user.getPhone())){
            logger.error("验证码发送失败，请联系管理员");
            return R.error("验证码发送失败");
        }


        logger.info("账号：{} , 验证码发送成功" , user.getUsername());
        return R.ok("验证码发送成功");
    }


    @PostMapping("/sys/checkVrifyCode")
    public Map<String, Object> checkVerifyCode(@RequestBody CheckLoginForm checkLoginForm){
        if(StringUtils.isEmpty(checkLoginForm.getVerifyCode()) || StringUtils.isEmpty(checkLoginForm.getUsername())){
            return R.error("验证码为空或者用户名为空");
        }

        //用户信息
        SysUserEntity user = sysUserService.queryByUserName(checkLoginForm.getUsername());

        if(!checkLoginForm.getVerifyCode().equals(redisClient.get("oemLogin_verifyCode"+user.getPhone()+"_"+checkLoginForm.getVerifyCode()))){
            logger.info("验证码填入错误，请重新输入");
            return R.error("验证码填入错误,请重新输入");
        }
        redisClient.remove("oemLogin_verifyCode"+user.getPhone()+"_"+checkLoginForm.getVerifyCode());
        //生成token，并保存到数据库
        R r = sysUserTokenService.createToken(user.getId());
        Boolean flag = false;
        //判断用户是否首次登录
        if (user.getPassword().equals(DigestUtils.sha256Hex(DigestUtils.sha256Hex(ConfigConstant.DEFAULT_PASSWORD).toLowerCase()+user.getSalt()))) {
            flag = true;
        }
        r.put("isFirstLogin", flag);
        return r;
    }


    /**
     * 退出
     */
    @PostMapping("/sys/logout")
    public R logout() {
        sysUserTokenService.logout(getUserId());
        return R.ok();
    }


}
