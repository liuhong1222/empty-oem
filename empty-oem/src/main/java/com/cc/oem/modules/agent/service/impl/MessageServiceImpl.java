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
    private final static String NON_AGENT_ROLE = "????????????????????????";


    /**
     * ??????????????????
     */
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void messageMySave(MessageSaveParam param, Long sysUserId, Long agentId) {
        verifyMessageType(param.getType()+"");

        int count = custInfoMapper.countCustNumOfAgent(agentId);
        if (count == 0) {
            throw new RRException("???????????????????????????????????????");
        }
        //userIdList:"[1,2,3]" ?????????????????????
        String userIdListStr = param.getUserIdList();
        if (StringUtils.isNotBlank(userIdListStr)) {
            userIdListStr = userIdListStr.replace("\"", "")
                    .replace("[", "")
                    .replace("]", "");
        }
        if (StringUtils.isBlank(userIdListStr) && !MessageSaveParam.SELECT_TYPE_ALL.equals(param.getSelectType())) {
            throw new RRException("????????????????????????");
        }
        Assert.notNull(agentId, NON_AGENT_ROLE);
        //60??????????????????????????????
        long recordCount = agentMessageMapper.countByCreatorWithinSeconds(agentId, 120L);
        if (recordCount > 0) {
            throw new RRException("120s???????????????????????????");
        }
        //1.??????AgentMessage
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
            throw new RRException("????????????????????????");
        }
    }

    @Async
    @Transactional
    public void insertAllMessage(AgentMessage param) {
        logger.info("taskExecutor???????????? agentMessageId{}"+param.getId()+"??????");
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
        logger.info("taskExecutor???????????? agentMessageId{}"+param.getId()+"?????????cost"+(end-start));
    }

    @Async
    @Transactional
    public void insertPartMessage(AgentMessage param) {
        logger.info("taskExecutor???????????? agentMessageId{}"+ param.getId()+"??????");
        long start=System.currentTimeMillis();
        int count = custInfoMapper.countCustNumOfAgent(param.getId());
        String[] custIdList = param.getCustomerIds().split(",");
        List<MyNotice> toInsertMessageList = new ArrayList<>();
        for (String custId:custIdList) {
            toInsertMessageList.add(initMessage(param,Long.valueOf(custId)));
        }
        myNoticeService.batchSave(toInsertMessageList);
        long end=System.currentTimeMillis();
        logger.info("taskExecutor???????????? agentMessageId{}"+param.getId()+"?????????cost"+(end-start));
    }


    /**
     * ?????????????????????
     */
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void messageAllAudit(MessageAuditParam param, Long agentId) {
        //??????????????????
        if (!param.getAuditState().equals(MessageAuditStateEnum.AUDITED.getCode()) &&
                !param.getAuditState().equals(MessageAuditStateEnum.REJECTED.getCode())) {
            throw new RRException("????????????");
        }
        //1.???????????????????????????
        int auditAgentMessageCount = agentMessageMapper.auditAgentMessage(Long.valueOf(param.getAgentMessageId()),param.getAuditState(), param.getAuditRemark());
        if (auditAgentMessageCount == 0) {
            throw new RRException("???????????????????????????????????????");
        }
        //?????????????????????????????????????????????????????????????????????????????????
        if(MessageAuditStateEnum.AUDITED.getCode().equals(param.getAuditState())){
            AgentMessage agentMessage = agentMessageMapper.selectByPrimaryKey(Long.valueOf(param.getAgentMessageId()));
            if (MessageSaveParam.SELECT_TYPE_ALL.equals(agentMessage.getSendTarget()+"")) {
                insertAllMessage(agentMessage);
            } else {
                insertPartMessage(agentMessage);
            }
        }
        //2.??????????????????????????????????????????????????? Message

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
     * ?????????????????????
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
     * ???????????????????????????
     */
    @Override
    public PageInfo messageAllList(MessageInfoParam param) {
        return messageList(param);
    }



    /**
     * ??????????????????
     */
    private MessageInfoDetailData messageDetail(String agentMessageId, Long agentId) {
        MessageInfoDetailData messageInfoDetailData = new MessageInfoDetailData();
        AgentMessage agentMessage = agentMessageMapper.selectByPrimaryKey(Long.valueOf(agentMessageId));
        if (agentMessage != null) {
            //????????????????????????id????????????
            if (agentId != null && !agentId.equals(agentMessage.getAgentId())) {
                throw new RRException("?????????????????????");
            }
            BeanUtils.copyProperties(agentMessage, messageInfoDetailData);
            messageInfoDetailData.setNoticeTypeName(MessageTypeEnum.getDescriByCode(messageInfoDetailData.getNoticeType()+""));
            //?????????????????????
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
     * ???????????????????????????
     */
    @Override
    public MessageInfoDetailData messageAllDetail(String agentMessageId) {
        return messageDetail(agentMessageId, null);
    }

    /**
     * ?????????????????????
     */
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void messageAllDelete(Long agentMessageId, Long sysUserId) {
        //1.?????????????????????
        int deleteCount = agentMessageMapper.deleteAgentMessage(agentMessageId, null);
        if (deleteCount == 0) {
            throw new RRException("??????????????????????????????");
        }
    }

    /**
     * ????????????????????????
     */
    @Override
    public PageInfo messageMyList(MessageInfoParam param, Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        //???????????????????????????????????????
        param.setAgentId(agentId);
        param.setAgentMobile(null);
        param.setAgentName(null);
        return messageList(param);
    }

    /**
     * ??????????????????
     */
    private void verifyMessageType(String messageType) {
        if (MessageTypeEnum.getEnumByCode(messageType) == null) {
            throw new RRException("?????????????????????");
        }
    }

    /**
     * ??????????????????
     */
    @Override
    public void messageMyUpdate(MessageSaveParam param, Long sysUserId, Long agentId) {
        Long agentMessageId = param.getAgentMessageId();
        Assert.notNull(agentMessageId, "???????????????????????????id????????????");
        verifyMessageType(param.getType());
        Assert.notNull(agentId, NON_AGENT_ROLE);
        //???????????? ????????????????????? ??? ??????????????? ??????
        //1.??????agent_message
        AgentMessage agentMessage = agentMessageMapper.selectByPrimaryKey(Long.valueOf(agentMessageId.toString()));
        Assert.notNull(agentMessage, "???????????????");
        if (!agentId.equals(agentMessage.getAgentId())) {
            throw new RRException("???????????????????????????id??????");
        }
        String updater = String.format(CREATE_BY_OEM, sysUserId, agentId);
        //??????????????????
        Integer auditState;
        if (MessageAuditStateEnum.TO_AUDIT.getCode().equals(agentMessage.getState())) {
            auditState = MessageAuditStateEnum.TO_AUDIT.getCode();
        } else if (MessageAuditStateEnum.MODIFIED.getCode().equals(agentMessage.getState()) || MessageAuditStateEnum.REJECTED.getCode().equals(agentMessage.getState())) {
            auditState = MessageAuditStateEnum.MODIFIED.getCode();
        } else {
            throw new RRException("???????????????????????????");
        }
        int modifyCount = agentMessageMapper.modifyAgentMessage(param.getTitle(), param.getMessage(),
                param.getAgentMessageId(), auditState,
                param.getType());
        if (modifyCount == 0) {
            throw new RRException("?????????????????????????????????");
        }
    }

    /**
     * ????????????????????????
     */
    @Override
    public MessageInfoDetailData messageMyDetail(String agentMessageId, Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        return messageDetail(agentMessageId, agentId);
    }

    /**
     * ??????????????????
     */
    @Override
    public void messageMyDelete(Long agentMessageId, Long sysUserId, Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        //1.?????????????????????
        int deleteCount = agentMessageMapper.deleteAgentMessage(agentMessageId, agentId);
        if (deleteCount == 0) {
            throw new RRException("??????????????????????????????");
        }
    }


}
