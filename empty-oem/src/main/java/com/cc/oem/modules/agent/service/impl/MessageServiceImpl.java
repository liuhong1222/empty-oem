package com.cc.oem.modules.agent.service.impl;


import com.cc.oem.common.exception.RRException;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.dao.AgentMessageMapper;
import com.cc.oem.modules.agent.dao.CreUserMapper;
import com.cc.oem.modules.agent.dao.CustInfoMapper;
import com.cc.oem.modules.agent.entity.AgentMessage;
import com.cc.oem.modules.agent.entity.records.MyNotice;
import com.cc.oem.modules.agent.enums.MessageAuditStateEnum;
import com.cc.oem.modules.agent.enums.MessageTypeEnum;
import com.cc.oem.modules.agent.model.data.MessageInfoData;
import com.cc.oem.modules.agent.model.data.MessageInfoDetailData;
import com.cc.oem.modules.agent.model.param.MessageAuditParam;
import com.cc.oem.modules.agent.model.param.MessageInfoParam;
import com.cc.oem.modules.agent.model.param.MessageSaveParam;
import com.cc.oem.modules.agent.service.MessageService;
import com.cc.oem.modules.agent.service.records.MyNoticeService;
import com.cc.oem.modules.config.utils.Snowflake;
import com.cc.oem.modules.sys.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author chenzj
 */

@Service
public class MessageServiceImpl implements MessageService {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CreUserMapper creUserMapper;
    @Autowired
    private CustInfoMapper custInfoMapper;

    @Autowired
    private AgentMessageMapper agentMessageMapper;

    @Autowired
    private MyNoticeService myNoticeService;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private Snowflake snowflake;

    private final String CreateByStart = "[OEM]";

    private final static String CREATE_BY_OEM = "[OEM]sysUserId:%s,agentId:%s";
    private final static String NON_AGENT_ROLE = "该用户不是代理商";


    /**
     * 发布我的消息
     */
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void messageMySave(MessageSaveParam param, Long sysUserId, Long agentId) {
        verifyMessageType(param.getType()+"");

        int count = custInfoMapper.countCustNumOfAgent(agentId);
        if (count == 0) {
            throw new RRException("你还没有用户，不能发布消息");
        }
        //userIdList:"[1,2,3]" 字符串转成数组
        String userIdListStr = param.getUserIdList();
        if (StringUtils.isNotBlank(userIdListStr)) {
            userIdListStr = userIdListStr.replace("\"", "")
                    .replace("[", "")
                    .replace("]", "");
        }
        if (StringUtils.isBlank(userIdListStr) && !MessageSaveParam.SELECT_TYPE_ALL.equals(param.getSelectType())) {
            throw new RRException("用户列表不能为空");
        }
        Assert.notNull(agentId, NON_AGENT_ROLE);
        //60秒内只能发送一条新闻
        long recordCount = agentMessageMapper.countByCreatorWithinSeconds(agentId, 120L);
        if (recordCount > 0) {
            throw new RRException("120s内只能发送一条消息");
        }
        //1.插入AgentMessage
        AgentMessage agentMessage = new AgentMessage();
        agentMessage.setTitle(param.getTitle());
        agentMessage.setContent(param.getMessage());
        agentMessage.setNoticeType(Integer.valueOf(param.getType()));
        agentMessage.setState(MessageAuditStateEnum.TO_AUDIT.getCode());
        agentMessage.setAgentId(agentId);
        agentMessage.setCustomerIds(userIdListStr);
        agentMessage.setId(snowflake.nextId());
        agentMessage.setSendTarget(Integer.valueOf(param.getSelectType()));
        int insertCount = agentMessageMapper.insert(agentMessage);
        if (insertCount == 0) {
            throw new RRException("插入失败，请重试");
        }
    }

    @Async
    @Transactional
    public void insertAllMessage(AgentMessage param) {
        logger.info("taskExecutor插入消息 agentMessageId{}"+param.getId()+"开始");
        long start=System.currentTimeMillis();
        int count = custInfoMapper.countCustNumOfAgent(param.getAgentId());
        int pageSize = 3000;
        int pages = count / pageSize + 1;
        for (int i = 0; i < pages; i++) {
            Map map = Maps.newHashMap();
            map.put("start", i * pageSize);
            map.put("pagesize", pageSize);
            map.put("agentId", param.getAgentId());
            List<Long> custIdList = custInfoMapper.selectCustIdListByAgentId(map);
            List<MyNotice> toInsertMessageList = new ArrayList<>(pageSize + 1);
            for (Long custId : custIdList) {
                toInsertMessageList.add(initMessage(param,custId));
            }
            myNoticeService.batchSave(toInsertMessageList);
        }
        long end=System.currentTimeMillis();
        logger.info("taskExecutor插入消息 agentMessageId{}"+param.getId()+"结束，cost"+(end-start));
    }

    @Async
    @Transactional
    public void insertPartMessage(AgentMessage param) {
        logger.info("taskExecutor插入消息 agentMessageId{}"+ param.getId()+"开始");
        long start=System.currentTimeMillis();
        int count = custInfoMapper.countCustNumOfAgent(param.getId());
        String[] custIdList = param.getCustomerIds().split(",");
        List<MyNotice> toInsertMessageList = new ArrayList<>();
        for (String custId:custIdList) {
            toInsertMessageList.add(initMessage(param,Long.valueOf(custId)));
        }
        myNoticeService.batchSave(toInsertMessageList);
        long end=System.currentTimeMillis();
        logger.info("taskExecutor插入消息 agentMessageId{}"+param.getId()+"结束，cost"+(end-start));
    }


    /**
     * 审核代理商消息
     */
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void messageAllAudit(MessageAuditParam param, Long agentId) {
        //判断审核状态
        if (!param.getAuditState().equals(MessageAuditStateEnum.AUDITED.getCode()) &&
                !param.getAuditState().equals(MessageAuditStateEnum.REJECTED.getCode())) {
            throw new RRException("非法操作");
        }
        //1.更新代理商消息状态
        int auditAgentMessageCount = agentMessageMapper.auditAgentMessage(Long.valueOf(param.getAgentMessageId()),param.getAuditState(), param.getAuditRemark());
        if (auditAgentMessageCount == 0) {
            throw new RRException("代理商消息不存在或不可审核");
        }
        //假如审核通过，则需要插入我的消息列表，绑定和客户的关系
        if(MessageAuditStateEnum.AUDITED.getCode().equals(param.getAuditState())){
            AgentMessage agentMessage = agentMessageMapper.selectByPrimaryKey(Long.valueOf(param.getAgentMessageId()));
            if (MessageSaveParam.SELECT_TYPE_ALL.equals(agentMessage.getSendTarget()+"")) {
                insertAllMessage(agentMessage);
            } else {
                insertPartMessage(agentMessage);
            }
        }
        //2.根据手机号列表，查询用户，批量插入 Message

    }

    private MyNotice initMessage(AgentMessage param,Long custId) {
        MyNotice message = new MyNotice();
        message.setId(snowflake.nextId());
        message.setCustomerId(custId);
        message.setTitle(param.getTitle());
        message.setContent(param.getContent());
        message.setNoticeId(param.getId());
        message.setNoticeType(param.getNoticeType());
        message.setHaveRead(0);
        return message;
    }

    @Override
    public R findUserIdMobileByMobile(String userPhone) {

        Long sysUserId = sysUserService.getSysUserId();
        Long agentId = sysUserService.selectAgentIdBySysUserId(sysUserId);

        if (userPhone == null || userPhone.trim().length() < 7) {
            PageHelper.startPage(1, 100);
        }

        if (StringUtils.isNotBlank(userPhone)) {
            userPhone = "%" + userPhone + "%";
        } else {
            userPhone = "%%";
        }

        List<Map> mapList = custInfoMapper.findUserIdMobileByMobileAndAgentId(userPhone, agentId);
        return R.ok(mapList);
    }

    @Override
    public R test(String userPhone) {
        return R.ok(new PageInfo<>(null));
    }

    /**
     * 代理商消息列表
     */
    private PageInfo messageList(MessageInfoParam param) {
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        List<MessageInfoData> list = agentMessageMapper.queryAgentMessageList(param);
        PageInfo<MessageInfoData> pageInfo = new PageInfo<>(list);
        pageInfo.getList().forEach(e -> {
            e.setStateName(MessageAuditStateEnum.getDescriByCode(e.getState()));
            e.setTypeName(MessageTypeEnum.getDescriByCode(e.getNoticeType()+""));
        });
        return pageInfo;
    }


    /**
     * 查询代理商消息列表
     */
    @Override
    public PageInfo messageAllList(MessageInfoParam param) {
        return messageList(param);
    }



    /**
     * 查看新闻详情
     */
    private MessageInfoDetailData messageDetail(String agentMessageId, Long agentId) {
        MessageInfoDetailData messageInfoDetailData = new MessageInfoDetailData();
        AgentMessage agentMessage = agentMessageMapper.selectByPrimaryKey(Long.valueOf(agentMessageId));
        if (agentMessage != null) {
            //判断是否为代理商id是否相同
            if (agentId != null && !agentId.equals(agentMessage.getAgentId())) {
                throw new RRException("无权查看此消息");
            }
            BeanUtils.copyProperties(agentMessage, messageInfoDetailData);
            messageInfoDetailData.setNoticeTypeName(MessageTypeEnum.getDescriByCode(messageInfoDetailData.getNoticeType()+""));
            //添加手机号列表
            List<String> mobileList = new ArrayList<>();
            if(agentMessage.getSendTarget()==0){
                mobileList = custInfoMapper.selectMobileListByCustIds(null);
            }else{
                mobileList = custInfoMapper.selectMobileListByCustIds(Arrays.asList(agentMessage.getCustomerIds().split(",")));
            }
            if (mobileList != null && mobileList.size() == 10) {
                mobileList.set(9,"......");
            }
            messageInfoDetailData.setCustomerMobileList(mobileList);
        }
        return messageInfoDetailData;
    }

    /**
     * 查看代理商消息详情
     */
    @Override
    public MessageInfoDetailData messageAllDetail(String agentMessageId) {
        return messageDetail(agentMessageId, null);
    }

    /**
     * 删除代理商消息
     */
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void messageAllDelete(Long agentMessageId, Long sysUserId) {
        //1.删除代理商消息
        int deleteCount = agentMessageMapper.deleteAgentMessage(agentMessageId, null);
        if (deleteCount == 0) {
            throw new RRException("消息不存在或不可删除");
        }
    }

    /**
     * 查询我的消息列表
     */
    @Override
    public PageInfo messageMyList(MessageInfoParam param, Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        //查找并验证代理商主账号信息
        param.setAgentId(agentId);
        param.setAgentMobile(null);
        param.setAgentName(null);
        return messageList(param);
    }

    /**
     * 验证消息类型
     */
    private void verifyMessageType(String messageType) {
        if (MessageTypeEnum.getEnumByCode(messageType) == null) {
            throw new RRException("消息类型不正确");
        }
    }

    /**
     * 修改我的消息
     */
    @Override
    public void messageMyUpdate(MessageSaveParam param, Long sysUserId, Long agentId) {
        Long agentMessageId = param.getAgentMessageId();
        Assert.notNull(agentMessageId, "参数校验失败，消息id不能为空");
        verifyMessageType(param.getType());
        Assert.notNull(agentId, NON_AGENT_ROLE);
        //只能修改 发布待审核状态 和 驳回状态的 消息
        //1.修改agent_message
        AgentMessage agentMessage = agentMessageMapper.selectByPrimaryKey(Long.valueOf(agentMessageId.toString()));
        Assert.notNull(agentMessage, "消息不存在");
        if (!agentId.equals(agentMessage.getAgentId())) {
            throw new RRException("参数校验失败，消息id非法");
        }
        String updater = String.format(CREATE_BY_OEM, sysUserId, agentId);
        //审核状态赋值
        Integer auditState;
        if (MessageAuditStateEnum.TO_AUDIT.getCode().equals(agentMessage.getState())) {
            auditState = MessageAuditStateEnum.TO_AUDIT.getCode();
        } else if (MessageAuditStateEnum.MODIFIED.getCode().equals(agentMessage.getState()) || MessageAuditStateEnum.REJECTED.getCode().equals(agentMessage.getState())) {
            auditState = MessageAuditStateEnum.MODIFIED.getCode();
        } else {
            throw new RRException("消息为不可编辑状态");
        }
        int modifyCount = agentMessageMapper.modifyAgentMessage(param.getTitle(), param.getMessage(),
                param.getAgentMessageId(), auditState,
                param.getType());
        if (modifyCount == 0) {
            throw new RRException("修改失败，不可编辑状态");
        }
    }

    /**
     * 查看我的消息详情
     */
    @Override
    public MessageInfoDetailData messageMyDetail(String agentMessageId, Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        return messageDetail(agentMessageId, agentId);
    }

    /**
     * 删除我的消息
     */
    @Override
    public void messageMyDelete(Long agentMessageId, Long sysUserId, Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        //1.删除代理商消息
        int deleteCount = agentMessageMapper.deleteAgentMessage(agentMessageId, agentId);
        if (deleteCount == 0) {
            throw new RRException("消息不存在或不可删除");
        }
    }


}
