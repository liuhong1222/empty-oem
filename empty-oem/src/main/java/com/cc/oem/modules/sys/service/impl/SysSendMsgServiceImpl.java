package com.cc.oem.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cc.oem.common.redis.RedisClient;
import com.cc.oem.common.utils.message.MessageComponent;
import com.cc.oem.modules.sys.dao.SysUserDao;
import com.cc.oem.modules.sys.entity.SysUserEntity;
import com.cc.oem.modules.sys.service.SysSendMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SysSendMsgServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysSendMsgService {

    private Logger logger = LoggerFactory.getLogger(SysSendMsgServiceImpl.class);

    @Autowired
    private MessageComponent messageComponent;

    @Autowired
    private RedisClient redisClient;
    @Value("${spring.profiles.active}")
    private String envir;

    //异步发送
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * 发送验证码方法
     * @param mobile
     * @return
     */
    @Override
    public boolean SendMsg(String mobile) {
        String verifyCode = String.valueOf(new Random().nextInt(89999)+100000);
        if(envir.equals("dev")){
            verifyCode = "111111";
        }
        String msg = verifyCode+"是您的登录验证码，请在2分钟内按页面提示填写验证码，切勿泄露给他人，非本人请忽略";
//        if(envir.equals("dev")){
//            redisClient.set("oemLogin_verifyCode"+mobile+"_"+verifyCode , verifyCode , 5*60);
//            return true;
//        }
        if(!messageComponent.sendMsg(mobile,msg)){
            return false;
        }
        redisClient.set("oemLogin_verifyCode"+mobile+"_"+verifyCode , verifyCode , 5*60);
//        redissonClient.getBucket("oemLogin_verifyCode"+phone+"_"+verifyCode).expire(5*60 , TimeUnit.SECONDS);
        logger.info("oem系统登录验证码,手机号:{},缓存验证码:{}" , mobile , verifyCode);
        return true;
    }

    @Override
    public void SendNotifyMsg(String mobile,String msg) {
        //异步发送
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                boolean b = messageComponent.sendMsg(mobile, msg);
                logger.info("异步发送通知消息，内容：{}，返回：{}",msg,b);
            }
        });
    }
}
