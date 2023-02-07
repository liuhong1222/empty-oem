package com.cc.oem.modules.agent.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.cc.oem.common.exception.RRException;
import com.cc.oem.common.utils.MD5Util;
import com.cc.oem.common.utils.R;
import com.cc.oem.common.utils.UploadUtils;
import com.cc.oem.modules.agent.constants.AgentConstant;
import com.cc.oem.modules.agent.dao.*;
import com.cc.oem.modules.agent.dao.records.AgentRefundMapper;
import com.cc.oem.modules.agent.entity.Agent;
import com.cc.oem.modules.agent.entity.AgentAccount;
import com.cc.oem.modules.agent.entity.AgentRecharge;
import com.cc.oem.modules.agent.entity.records.AgentRefund;
import com.cc.oem.modules.agent.enums.AgentAuditStateEnum;
import com.cc.oem.modules.agent.enums.AgentPictureTypeEnum;
import com.cc.oem.modules.agent.model.data.AgentInfoData;
import com.cc.oem.modules.agent.model.data.AgentRechargeTotalData;
import com.cc.oem.modules.agent.model.param.AgentInfoParam;
import com.cc.oem.modules.agent.model.param.AgentInfoSaveParam;
import com.cc.oem.modules.agent.model.param.AgentInfoUpdateParam;
import com.cc.oem.modules.agent.model.param.AgentRechargeParam;
import com.cc.oem.modules.agent.model.param.records.AgentRefundParam;
import com.cc.oem.modules.agent.service.AgentInfoService;
import com.cc.oem.modules.agent.service.OcrService;
import com.cc.oem.modules.agent.service.RefreshCacheService;
import com.cc.oem.modules.agent.service.UploadPictureService;
import com.cc.oem.modules.agent.util.SpringBeanUtil;
import com.cc.oem.modules.config.utils.Snowflake;
import com.cc.oem.modules.sys.entity.SysUserEntity;
import com.cc.oem.modules.sys.service.SysSendMsgService;
import com.cc.oem.modules.sys.service.SysUserService;
import com.cc.oem.modules.sys.service.SysUserTokenService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenzj
 * @since 2018/8/8
 */
@Service
public class AgentInfoServiceImpl implements AgentInfoService {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AgentMapper agentMapper;

    @Autowired
    UploadPictureService uploadPictureService;

    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysSendMsgService sysSendMsgService;

    @Autowired
    AgentLevelMapper agentLevelMapper;

    @Autowired
    AgentAccountMapper agentAccountMapper;

    @Autowired
    ProductPackageMapper productPackageMapper;

    @Autowired
    AgentRechargeMapper agentRechargeMapper;

    @Autowired
    AgentSetMapper agentSetMapper;

    @Autowired
    SysUserTokenService sysUserTokenService;

    @Autowired
    OcrService ocrService;

    @Autowired
    private Snowflake snowflake;
    @Autowired
    UploadUtils uploadUtils;
    @Value("${fileUploadPath.mobileCube}")
    private String mobileCubePath;
    @Autowired
    private AgentRefundMapper agentRefundMapper;
    @Autowired
    private RefreshCacheService refreshCacheService;
    
    @Override
    public List<Long> findAgentIdList(){
    	return agentMapper.findAgentIdList();
    }

    /**
     * 查询代理商列表
     *
     * @param param
     * @return 逻辑关系梳理，实时统计当日的客户消耗以及退款，总结成剩余条数
     */
    @Override
    public PageInfo list(AgentInfoParam param) {
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        //获取代理商列表
        if(param.getOfficialWeb()!=null){
            List<Long> agentIdList = agentSetMapper.querAgentIdListByOfficialWeb(param.getOfficialWeb());
            if(agentIdList.size()<1){
                return new PageInfo<>();
            }
            param.setAgentIdList(agentIdList);
        }
        List<AgentInfoData> list = agentMapper.queryAgentInfoList(param);
        PageInfo<AgentInfoData> pageInfo = new PageInfo<>(list);
        List<AgentInfoData> pageInfoList = pageInfo.getList();
        if (CollectionUtils.isEmpty(pageInfoList)) {
            //没有相关数据则直接返回
            return pageInfo;
        }
        //获取代理商id列表
        List<Long> agentIdList = pageInfoList.stream().map(AgentInfoData::getAgentId).collect(Collectors.toList());
        //根据代理id列表，获取充值总金额总条数
        //得到账户表相关情况
        List<AgentRechargeTotalData> agentRechargeTotalDataList = agentRechargeMapper.queryAgentRechargeTotalDataList(agentIdList);
        //充值汇总情况统计map
        Map<Long, AgentRechargeTotalData> RechargeTotalDataMap = new HashMap<>();
        for (AgentRechargeTotalData e : agentRechargeTotalDataList) {
            RechargeTotalDataMap.put(e.getAgentId(), e);
        }
        //组装相关信息
        for (AgentInfoData eachData : pageInfoList) {
            Long agentId = eachData.getAgentId();
            AgentRechargeTotalData agentInfo = RechargeTotalDataMap.get(agentId);
            eachData.setEmptyRechargeMoney(agentInfo == null?0L:agentInfo.getEmptyRechargeMoney());
            eachData.setEmptyBalance(agentInfo == null?0L:agentInfo.getEmptyBalance());
            eachData.setEmptyRechargeNumber(agentInfo == null?0L:agentInfo.getEmptyRechargeNumber());
            eachData.setRealTimeRechargeMoney(agentInfo == null?0L:agentInfo.getRealtimeRechargeMoney());
            eachData.setRealTimeRechargeNumber(agentInfo == null?0L:agentInfo.getRealtimeRechargeNumber());
            eachData.setRealTimeBalance(agentInfo == null?0L:agentInfo.getRealtimeBalance());
            eachData.setInternationalRechargeMoney(agentInfo == null?0L:agentInfo.getInternationalRechargeMoney());
            eachData.setInternationalRechargeNumber(agentInfo == null?0L:agentInfo.getInternationalRechargeNumber());
            eachData.setInternationalBalance(agentInfo == null?0L:agentInfo.getInternationalBalance());
            
            eachData.setDirectCommonRechargeMoney(agentInfo == null?0L:agentInfo.getDirectCommonRechargeMoney());
            eachData.setDirectCommonRechargeNumber(agentInfo == null?0L:agentInfo.getDirectCommonRechargeNumber());
            eachData.setDirectCommonBalance(agentInfo == null?0L:agentInfo.getDirectCommonBalance());
            
            eachData.setLineDirectRechargeMoney(agentInfo == null?0L:agentInfo.getLineDirectRechargeMoney());
            eachData.setLineDirectRechargeNumber(agentInfo == null?0L:agentInfo.getLineDirectRechargeNumber());
            eachData.setLineDirectBalance(agentInfo == null?0L:agentInfo.getLineDirectBalance());
        }
        
        return pageInfo;
    }

    /**
     * 上传营业执照图片
     *
     * @param sysUserId
     * @param file
     * @return
     */

    @Override
    public Map<String, String> uploadLicense(Long sysUserId, MultipartFile file) throws Exception {
        String picPath = uploadPictureService.uploadAgentPictureService(sysUserId, file, AgentPictureTypeEnum.AGENT_LICENSE.getCode());
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotBlank(picPath)) {
            Map<String, String> ocrResultMap = ocrService.agentBizLicenseByFilePath(picPath, String.valueOf(System.currentTimeMillis()));
            if (!CollectionUtils.isEmpty(ocrResultMap)) {
                map.put("companyName", ocrResultMap.get("name"));
                map.put("address", ocrResultMap.get("address"));
                map.put("legalPerson", ocrResultMap.get("legalPerson"));
                map.put("licenseNo", ocrResultMap.get("regNum"));
                map.put("businessLicensePath", picPath);
                String establishDate = ocrResultMap.get("establishDate");
                if (StringUtils.isNotBlank(establishDate)) {
                    map.put("effectDate", establishDate);
                }
                String validPeriod = ocrResultMap.get("validPeriod");
                if (StringUtils.isNotBlank(validPeriod)) {
                    map.put("expireDate", validPeriod);
                }
            }
        }
        return map;
    }

    /**
     * 新增代理商
     * 1.保存代理商信息
     * 2.添加用户信息
     * 3.添加代理商账户信息
     */
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void saveAgent(AgentInfoSaveParam param, Long sysUserId) {
        //验证代理商信息是否重复
        agentInfoVerify(param);
        //1.保存agent
        Agent agent = new Agent();
        BeanUtils.copyProperties(param, agent);
        agent.setBusinessLicenseAddress(param.getBusinessLicenseAddress());
        agent.setAuthenticationLimitLevel(1);
        agent.setId(snowflake.nextId());
        agentMapper.insertSelective(agent);
        if (agent.getId() == null) {
            throw new RRException("保存代理商信息失败，数据库异常");
        }
        Long agentId = agent.getId();
        //2.建立系统账户信息并保存
        SysUserEntity sysUser = new SysUserEntity();
        sysUser.setUsername(param.getLinkmanPhone());
        //设置为默认密码
        sysUser.setPassword(DigestUtils.sha256Hex(AgentConstant.SYS_USER_PASSWORD));
        sysUser.setAgentId(agentId);
        sysUser.setEmail(param.getLinkmanEmail());
        sysUser.setPhone(param.getLinkmanPhone());
        sysUser.setRoleId(AgentConstant.AGENT_ROLE_ID);
        sysUser.setState(AgentConstant.COMMON_NORMAL_STATUS_VALUE);
        sysUser.setId(snowflake.nextId());
        sysUserService.save(sysUser);
        //2.1.给用户发送短信，告知账户密码
        String notifyMsg = String.format("您的oem系统登录账号为%s,初始密码是%s,登录后请尽快修改初始密码", param.getLinkmanPhone(), AgentConstant.SYS_USER_PASSWORD);
        sysSendMsgService.SendNotifyMsg(param.getLinkmanPhone(), notifyMsg);
        //3.保存代理商账户信息
        AgentAccount agentAccount = new AgentAccount();
        agentAccount.setAgentId(agentId);
        agentAccount.setId(snowflake.nextId());
        int count1 = agentAccountMapper.insert(agentAccount);
        if (count1 != 1) {
            throw new RRException("保存代理商信息失败，数据库异常");
        }
        logger.info("新增代理商完成");
    }


    /**
     * 查看代理商详情
     *
     * @param agentId
     * @return
     */
    @Override
    public Agent detail(Long agentId) {
        //1.agent信息
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        if (agent == null) {
            throw new RRException("代理商信息不存在");
        }
        return agent;
    }

    /**
     * 修改代理商信息
     *
     * @param param
     */
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void updateAgent(AgentInfoUpdateParam param) {
        //验证单价
        if (param.getPrice().compareTo(BigDecimal.ZERO) < 1) {
            throw new RRException("单价必须大于0");
        }
        //验证预警条数
        if (param.getWarningsNumber().compareTo(0L) < 0) {
            throw new RRException("预警条数必须大于0");
        }
        //验证代理商名称是否重复
        Agent agentVerifyCompanyName = agentMapper.queryOneByCompanyName(param.getCompanyName());
        if (agentVerifyCompanyName!=null&&!agentVerifyCompanyName.getId().equals(param.getAgentId())) {
            throw new RRException("代理商已存在");
        }
        //验证并代理商手机号码
        List<Agent> agentContactVerifyMobile = agentMapper.queryListByMobile(param.getLinkmanPhone());
        if (!CollectionUtils.isEmpty(agentContactVerifyMobile)) {
            for (Agent agentContact : agentContactVerifyMobile) {
                //如果已存在该手机号的别的代理商，则不允许
                if (!agentContact.getId().equals(param.getAgentId())) {
                    throw new RRException("此手机号代理商已经存在");
                }
            }
        }
        //验证并更新代管理员账号
        //根据代理商id 获取管理员主账号
        List<SysUserEntity> sysUserList = sysUserService.queryByAgentId(param.getAgentId());
        Assert.notNull(sysUserList, "该代理商主账号不存在，请联系管理员");
        //更新代理商用户名和手机号 userName 为唯一索引
        Agent source = agentMapper.selectByPrimaryKey(param.getAgentId());
        //得到agent创建时建立的主账号
        sysUserList.stream().filter(k -> k.getUsername().equals(source.getLinkmanPhone())).map(v -> {
            if (!param.getLinkmanPhone().equals(v.getPhone())) {
                sysUserService.updateUserNameAndMobileByUserId(v.getId(), param.getLinkmanPhone(), param.getLinkmanPhone());
                return true;
            }
            return false;
        });
        //验证营业执照编号是否重复
        Agent agentVerifyLicenseNo = agentMapper.queryOneByLicenseNo(param.getBusinessLicenseNumber());
        if (agentVerifyLicenseNo != null && !agentVerifyLicenseNo.getId().equals(param.getAgentId())) {
            throw new RRException("营业执照编号已存在");
        }

        //1.保存agent
        Agent agent = new Agent();
        BeanUtils.copyProperties(param, agent);
        Integer integer = SpringBeanUtil.helpUpdateColumn(Agent.class, source, agent);
        if(integer<1){
            return ;
        }
        agent.setId(param.getAgentId());
        agentMapper.updateByPrimaryKeySelective(agent);
        refreshCacheService.agentInfoRefresh(param.getAgentId());
    }

    /**
     * 禁用代理商
     */
    @Override
    public void pauseAgent(Long agentId) {
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        Assert.notNull(agent, "记录不存在");
        if (!agent.getState().equals(AgentConstant.COMMON_NORMAL_STATUS_VALUE)) {
            throw new RRException("该代理商目前已是禁用状态");
        }
        agentMapper.updateStatusById(agentId, AgentConstant.COMMON_DISABLED_STATUS_VALUE);
        //登出并禁用该代理商的所有账号
        List<SysUserEntity> agentSysUserList = sysUserService.queryByAgentId(agentId);
        List<Long> sysUserIdList = agentSysUserList.stream().map(SysUserEntity::getId).collect(Collectors.toList());
        for (Long sysUserId : sysUserIdList) {
            //禁用账号
            sysUserService.disableByUserId(sysUserId);
            //登出
            sysUserTokenService.logout(sysUserId);
        }
        refreshCacheService.agentInfoRefresh(agentId);
    }

    /**
     * 启用代理商
     */
    @Override
    public void resumeAgent(Long agentId) {
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        Assert.notNull(agent, "记录不存在");
        if (!agent.getState().equals(AgentConstant.COMMON_DISABLED_STATUS_VALUE)) {
            throw new RRException("该代理商目前已是启用状态");
        }
        agentMapper.updateStatusById(agentId, AgentConstant.COMMON_NORMAL_STATUS_VALUE);
        //启用该代理商的所有账号
        List<SysUserEntity> agentSysUserList = sysUserService.queryByAgentId(agentId);
        List<Long> sysUserIdList = agentSysUserList.stream().map(SysUserEntity::getId).collect(Collectors.toList());
        for (Long sysUserId : sysUserIdList) {
            //启用账号
            sysUserService.enableByUserId(sysUserId);
        }
        refreshCacheService.agentInfoRefresh(agentId);
    }

    /**
     * 代理商充值
     */
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public R recharge(AgentRechargeParam param) {
        long agentId = param.getAgentId();
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        if (agent == null) {
            logger.error("通过主键未找到代理商{}", agentId);
            return R.error("操作失败；经销商不存在");
        }

        if (!AgentConstant.COMMON_NORMAL_STATUS_VALUE.equals(agent.getState())) {
            return R.error("操作失败；经销商未启用");
        }
        AgentRecharge agentRecharge = new AgentRecharge();
        //空号充值
        BeanUtils.copyProperties(param, agentRecharge);
        Integer rechargeNumber = param.getRechargeNumber();
        if (AgentConstant.RECHARGE_CATEGORY_EMPTY.equals(param.getCategory())) {
            if (!agent.getPrice().equals(param.getPrice())) {
                return R.error("操作失败；代理商价格已过期，请刷新");
            }

            BigDecimal payAmount = new BigDecimal(param.getPaymentAmount());
            if (rechargeNumber.compareTo(Integer.MAX_VALUE) > 0) {
                return R.error("操作失败；充值金额超过最大值");
            }

            if (payAmount.compareTo(new BigDecimal(agent.getMinPaymentAmount())) < 0) {
                return R.error("操作失败；最小充值金额" + agent.getMinPaymentAmount() + "元");
            }

            if (rechargeNumber.compareTo(agent.getMinRechargeNumber().intValue())< 0) {
                return R.error("操作失败；最小充值条数" + agent.getMinRechargeNumber() + "条");
            }

            Date now = new Date();
            agentRecharge.setAgentLevel(agent.getAgentLevel());
            agentRecharge.setName(agent.getCompanyName());
            agentRecharge.setPhone(agent.getLinkmanPhone());
            agentRecharge.setOrderNo("EC" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT)
                    + RandomUtil.randomNumbers(8));
            agentRecharge.setRechargeNumber(rechargeNumber);
            agentRecharge.setCreateTime(now);
            agentRecharge.setUpdateTime(now);
        } else if (AgentConstant.RECHARGE_CATEGORY_REALTIME.equals(param.getCategory())) {
            //实时检测充值
            if (agent.getRealPrice() == null || org.springframework.util.StringUtils.isEmpty(agent.getRealLevel())
                    || agent.getRealMinPaymentAmount() == null || agent.getRealMinRechargeNumber() == null) {
                return R.error("操作失败；请先设置代理商实时检测产品等级信息");
            }
            if (!agent.getRealPrice().equals(param.getPrice())) {
                return R.error("操作失败；代理商实时检测产品价格已过期，请刷新");
            }

            BigDecimal payAmount = new BigDecimal(param.getPaymentAmount());
            if (rechargeNumber.compareTo(Integer.MAX_VALUE) > 0) {
                return R.error("操作失败；实时检测充值金额超过最大值");
            }

            if (payAmount.compareTo(new BigDecimal(agent.getRealMinPaymentAmount())) < 0) {
                return R.error("操作失败；实时检测最小充值金额" + agent.getRealMinPaymentAmount() + "元");
            }

            if (rechargeNumber.compareTo(agent.getRealMinRechargeNumber().intValue()) < 0) {
                return R.error("操作失败；实时检测最小充值条数" + agent.getRealMinRechargeNumber() + "条");
            }

            Date now = new Date();
            agentRecharge.setAgentLevel(agent.getRealLevel());
            agentRecharge.setName(agent.getCompanyName());
            agentRecharge.setPhone(agent.getLinkmanPhone());
            agentRecharge.setOrderNo("RC" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT)
                    + RandomUtil.randomNumbers(8));
            agentRecharge.setRechargeNumber(rechargeNumber.intValue());
            agentRecharge.setCreateTime(now);
            agentRecharge.setUpdateTime(now);

        } else if (AgentConstant.RECHARGE_CATEGORY_INTERNATIONAL.equals(param.getCategory())) {
            //国际检测充值
            if (agent.getInternationalPrice() == null || StringUtils.isBlank(agent.getInternationalLevel())
                   || agent.getInternationalMinPaymentAmount() == null || agent.getInternationalMinRechargeNumber() == null) {
                return R.error("操作失败；请先设置代理商国际检测产品等级信息");
            }
            if (!agent.getInternationalPrice().equals(param.getPrice())) {
                return R.error("操作失败；代理商国际检测产品价格已过期，请刷新");
            }

            BigDecimal payAmount = new BigDecimal(param.getPaymentAmount());
            if (rechargeNumber.compareTo(Integer.MAX_VALUE) > 0) {
                return R.error("操作失败；国际检测充值金额超过最大值");
            }

            if (payAmount.compareTo(new BigDecimal(agent.getInternationalMinPaymentAmount())) < 0) {
                return R.error("操作失败；国际检测最小充值金额" + agent.getInternationalMinPaymentAmount() + "元");
            }

            if (rechargeNumber.compareTo(agent.getInternationalMinRechargeNumber().intValue()) < 0) {
                return R.error("操作失败；国际检测最小充值条数" + agent.getInternationalMinRechargeNumber() + "条");
            }

            Date now = new Date();
            agentRecharge.setAgentLevel(agent.getInternationalLevel());
            agentRecharge.setName(agent.getCompanyName());
            agentRecharge.setPhone(agent.getLinkmanPhone());
            agentRecharge.setOrderNo("IC" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT)
                    + RandomUtil.randomNumbers(8));
            agentRecharge.setRechargeNumber(rechargeNumber.intValue());
            agentRecharge.setCreateTime(now);
            agentRecharge.setUpdateTime(now);

        }else if (AgentConstant.RECHARGE_CATEGORY_DIRECT_COMMON.equals(param.getCategory())) {
            //国际定向通用检测充值
            if (agent.getDirectCommonPrice() == null || StringUtils.isBlank(agent.getDirectCommonLevel())
                   || agent.getDirectCommonMinPaymentAmount() == null || agent.getDirectCommonMinRechargeNumber() == null) {
                return R.error("操作失败；请先设置代理商国际定向通用检测产品等级信息");
            }
            if (!agent.getDirectCommonPrice().equals(param.getPrice())) {
                return R.error("操作失败；代理商国际定向通用检测产品价格已过期，请刷新");
            }

            BigDecimal payAmount = new BigDecimal(param.getPaymentAmount());
            if (rechargeNumber.compareTo(Integer.MAX_VALUE) > 0) {
                return R.error("操作失败；国际定向通用检测充值金额超过最大值");
            }

            if (payAmount.compareTo(new BigDecimal(agent.getDirectCommonMinPaymentAmount())) < 0) {
                return R.error("操作失败；国际定向通用检测最小充值金额" + agent.getDirectCommonMinPaymentAmount() + "元");
            }

            if (rechargeNumber.compareTo(agent.getDirectCommonMinRechargeNumber().intValue()) < 0) {
                return R.error("操作失败；国际定向通用检测最小充值条数" + agent.getDirectCommonMinRechargeNumber() + "条");
            }

            Date now = new Date();
            agentRecharge.setAgentLevel(agent.getDirectCommonLevel());
            agentRecharge.setName(agent.getCompanyName());
            agentRecharge.setPhone(agent.getLinkmanPhone());
            agentRecharge.setOrderNo("IDCC" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT)
                    + RandomUtil.randomNumbers(8));
            agentRecharge.setRechargeNumber(rechargeNumber.intValue());
            agentRecharge.setCreateTime(now);
            agentRecharge.setUpdateTime(now);

        }else if (AgentConstant.RECHARGE_CATEGORY_LINE_DIRECT.equals(param.getCategory())) {
            //国际line定向检测充值
            if (agent.getLineDirectPrice() == null || StringUtils.isBlank(agent.getLineDirectLevel())
                   || agent.getLineDirectMinPaymentAmount() == null || agent.getLineDirectMinRechargeNumber() == null) {
                return R.error("操作失败；请先设置代理商国际line定向检测产品等级信息");
            }
            if (!agent.getLineDirectPrice().equals(param.getPrice())) {
                return R.error("操作失败；代理商国际line定向检测产品价格已过期，请刷新");
            }

            BigDecimal payAmount = new BigDecimal(param.getPaymentAmount());
            if (rechargeNumber.compareTo(Integer.MAX_VALUE) > 0) {
                return R.error("操作失败；国际line定向检测充值金额超过最大值");
            }

            if (payAmount.compareTo(new BigDecimal(agent.getLineDirectMinPaymentAmount())) < 0) {
                return R.error("操作失败；国际line定向检测最小充值金额" + agent.getLineDirectMinPaymentAmount() + "元");
            }

            if (rechargeNumber.compareTo(agent.getLineDirectMinRechargeNumber().intValue()) < 0) {
                return R.error("操作失败；国际line定向检测最小充值条数" + agent.getLineDirectMinRechargeNumber() + "条");
            }

            Date now = new Date();
            agentRecharge.setAgentLevel(agent.getLineDirectLevel());
            agentRecharge.setName(agent.getCompanyName());
            agentRecharge.setPhone(agent.getLinkmanPhone());
            agentRecharge.setOrderNo("ILDC" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT)
                    + RandomUtil.randomNumbers(8));
            agentRecharge.setRechargeNumber(rechargeNumber.intValue());
            agentRecharge.setCreateTime(now);
            agentRecharge.setUpdateTime(now);

        }
        agentRecharge.setStatus(1);
        agentRecharge.setId(snowflake.nextId());
        agentRechargeMapper.insertSelective(agentRecharge);
        agentAccountMapper.addEmptyBalanceByAgentId(agentRecharge);
        logger.info("代理商充值成功，充值参数：{}", JSONObject.toJSONString(param));
        return R.ok();
    }

    private void agentInfoVerify(AgentInfoSaveParam param) {
        //验证登录账号是否重复
        SysUserEntity userVerify = sysUserService.queryByUserName(param.getLinkmanPhone());
        Assert.isNull(userVerify, "此手机号已被注册");
        //查询该手机号的代理商列表
        List<Agent> agentContactVerifyList = agentMapper.queryListByMobile(param.getLinkmanPhone());
        if (!CollectionUtils.isEmpty(agentContactVerifyList)) {
            throw new RRException("此手机号代理商已经存在");
        }
        //验证代理商名称是否重复
        Agent agentVerify = agentMapper.queryOneByCompanyName(param.getCompanyName());
        Assert.isNull(agentVerify, "代理商已存在");
        //验证营业执照编号是否重复
        Agent agentVerifyLicenseNo = agentMapper.queryOneByLicenseNo(param.getBusinessLicenseNumber());
        Assert.isNull(agentVerifyLicenseNo, "营业执照编号已存在");
    }

    public R listAgent(String name,Integer officialWeb) {
        List<Map> maps = agentMapper.listAgent(name,officialWeb);
        return R.ok(maps);
    }

    @Override
    public R findAgentListByName(String name) {
        List<Long> list = agentMapper.findAgentListByName(name);
        return R.ok(list);
    }

    /**
     * 代理商没有赠送一说，所有的条数都是实打实的冲来的
     * @param agentId
     * @return
     */
    public R getRefundableNumOfAgent(Long agentId){
        //查找当前余额
        //扣除历史的注册赠送以及赠送条数
        AgentAccount agentAccount = agentAccountMapper.queryOneByAgentId(agentId);
        AgentAccount total = agentRechargeMapper.findRechargeNumByAgentId(agentId);
        Map map = new HashMap();
        if(total==null){
            map.put("refundableEmpty",agentAccount.getEmptyBalance());
            map.put("refundableRealtime",agentAccount.getRealtimeBalance());
            map.put("refundableInternational",agentAccount.getInternationalBalance());
            map.put("refundableDirectCommon",agentAccount.getDirectCommonBalance());
            map.put("refundableLineDirect",agentAccount.getLineDirectBalance());
        }else {
            map.put("refundableEmpty",agentAccount.getEmptyBalance()>total.getEmptyBalance()?agentAccount.getEmptyBalance()-total.getEmptyBalance():0);
            map.put("refundableRealtime",agentAccount.getRealtimeBalance()>total.getRealtimeBalance()?agentAccount.getRealtimeBalance()-total.getRealtimeBalance():0);
            map.put("refundableInternational",agentAccount.getInternationalBalance()>total.getInternationalBalance()?agentAccount.getInternationalBalance()-total.getInternationalBalance():0);
            map.put("refundableDirectCommon",agentAccount.getDirectCommonBalance()>total.getDirectCommonBalance()?agentAccount.getDirectCommonBalance()-total.getDirectCommonBalance():0);
            map.put("refundableLineDirect",agentAccount.getLineDirectBalance()>total.getLineDirectBalance()?agentAccount.getLineDirectBalance()-total.getLineDirectBalance():0);
        }

        map.put("emptyBalance",agentAccount.getEmptyBalance());
        map.put("realtimeBalance",agentAccount.getRealtimeBalance());
        map.put("internationalBalance",agentAccount.getInternationalBalance());
        map.put("directCommonBalance",agentAccount.getDirectCommonBalance());
        map.put("lineDirectBalance",agentAccount.getLineDirectBalance());
        return R.ok(map);
    }

    @Override
    public R uploadMobileCube(MultipartFile file,Long agentId,String fileType) {
        String path = uploadUtils.uploadMobileCube(file, mobileCubePath);
        int i = agentMapper.updateMobileCubePath(path, agentId,fileType);
        if(i!=1){
            return R.error("上传替换号码魔方失败");
        }
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public R refundOfAgent(AgentRefundParam param){
        long agentId = param.getAgentId();
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        if (agent == null) {
            logger.error("通过主键未找到代理商{}", agentId);
            return R.error("操作失败；经销商不存在");
        }

        if (!AgentConstant.COMMON_NORMAL_STATUS_VALUE.equals(agent.getState())) {
            return R.error("操作失败；经销商未启用");
        }
        AgentRefund agentRefund = new AgentRefund();
        agentRefund.setName(agent.getCompanyName());
        //空号充值
        BeanUtils.copyProperties(param, agentRefund);
        agentRefund.setPhone(agent.getLinkmanPhone());
        agentRefund.setOrderNo(getOrderNoPrefix(param.getCategory()) + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT)+ RandomUtil.randomNumbers(8));
        agentRefund.setRefundNumber(param.getRefundNumber());
        agentRefund.setRefundAmount(param.getRefundAmount());
        if (AgentConstant.RECHARGE_CATEGORY_EMPTY.equals(param.getCategory())) {
            if (!agentRefund.getPrice().equals(param.getPrice())) {
                return R.error("操作失败；代理商价格已过期，请刷新");
            }
            if (agentRefund.getPrice().compareTo(agent.getPrice()) > 0) {
                return R.error("操作失败；退款单价超过代理商成本价");
            }

        } else if (AgentConstant.RECHARGE_CATEGORY_REALTIME.equals(param.getCategory())) {
            //实时检测退款
            if (agentRefund.getPrice().compareTo(agent.getRealPrice()) > 0) {
                return R.error("操作失败；退款单价超过代理商实时产品成本价");
            }
        }else if (AgentConstant.RECHARGE_CATEGORY_INTERNATIONAL.equals(param.getCategory())) {
            //国际检测退款
            if (agentRefund.getPrice().compareTo(agent.getInternationalPrice()) > 0) {
                return R.error("操作失败；退款单价超过代理商国际产品成本价");
            }
        }else if (AgentConstant.RECHARGE_CATEGORY_DIRECT_COMMON.equals(param.getCategory())) {
            //国际定向通用检测退款
            if (agentRefund.getPrice().compareTo(agent.getDirectCommonPrice()) > 0) {
                return R.error("操作失败；退款单价超过代理商定向通用产品成本价");
            }
        }else if (AgentConstant.RECHARGE_CATEGORY_LINE_DIRECT.equals(param.getCategory())) {
            //国际line定向检测退款
            if (agentRefund.getPrice().compareTo(agent.getLineDirectPrice()) > 0) {
                return R.error("操作失败；退款单价超过代理商line定向产品成本价");
            }
        }
        agentRefund.setStatus(1);
        agentRefund.setId(snowflake.nextId());
        int save = agentRefundMapper.save(agentRefund);

        int i = agentAccountMapper.plusEmptyBalanceByAgentId(agentRefund);
        if((save+i)!=2){
            return R.error("给代理商退款失败");
        }
        return R.ok();
    }
    
    private String getOrderNoPrefix(Integer category) {
    	switch (category) {
		case 0:
			return "EC";
		case 1:
			return "RC";
		case 2:
			return "IC";
		case 4:
			return "IDCC";
		case 5:
			return "ILDC";

		default:
			return "";
		}
    }
}
