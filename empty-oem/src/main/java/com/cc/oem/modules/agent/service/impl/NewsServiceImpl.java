package com.cc.oem.modules.agent.service.impl;

import com.cc.oem.common.exception.RRException;
import com.cc.oem.modules.agent.dao.CreUserMapper;
import com.cc.oem.modules.agent.dao.NewsMapper;
import com.cc.oem.modules.agent.entity.News;
import com.cc.oem.modules.agent.enums.NewsAuditStateEnum;
import com.cc.oem.modules.agent.model.data.NewsInfoData;
import com.cc.oem.modules.agent.model.data.NewsInfoDetailData;
import com.cc.oem.modules.agent.model.param.NewsAuditParam;
import com.cc.oem.modules.agent.model.param.NewsInfoParam;
import com.cc.oem.modules.agent.model.param.NewsSaveParam;
import com.cc.oem.modules.agent.service.NewsService;
import com.cc.oem.modules.config.utils.Snowflake;
import com.cc.oem.modules.sys.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * 新闻发布操作类
 */

@Service
public class NewsServiceImpl implements NewsService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CreUserMapper creUserMapper;
    @Autowired
    NewsMapper newsMapper;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    private Snowflake snowflake;

    private final static String CREATE_BY_OEM = "[OEM]sysUserId:%s,agentId:%s";

    private final static String NON_AGENT_ROLE ="该用户不是代理商";

    /**
     * 审核代理商新闻
     */
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void newsAllAudit(NewsAuditParam param, Long sysUserId) {
        //判断审核状态
        if (!param.getAuditState().equals(NewsAuditStateEnum.AUDITED.getCode()) &&
                !param.getAuditState().equals(NewsAuditStateEnum.REJECTED.getCode())) {
            throw new RRException("非法操作");
        }
        //1.更新新闻状态
        int update = newsMapper.auditNews(param.getNewsId(), param.getAuditState(), param.getAuditRemark());
        if (update == 0) {
            throw new RRException("新闻不存在或不可审核");
        }
    }

    /**
     * 查看新闻详情
     */
    private NewsInfoDetailData newsDetail(String newsId, Long agentId) {
        NewsInfoDetailData newsInfoDetailData = new NewsInfoDetailData();
        News news = newsMapper.selectByPrimaryKey(newsId);
        if (news != null) {
            //判断是否为代理商id是否相同
            if (agentId != null && !agentId.equals(news.getAgentId())) {
                throw new RRException("无权查看此新闻");
            }
            BeanUtils.copyProperties(news, newsInfoDetailData);
        }
        return newsInfoDetailData;
    }

    /**
     * 查看代理商新闻详情
     */
    @Override
    public NewsInfoDetailData newsAllDetail(String newsId) {
        return newsDetail(newsId, null);
    }

    /**
     * 删除代理商新闻
     */
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void newsAllDelete(Long newsId, Long sysUserId) {
        //1.删除新闻
        int update = newsMapper.deleteNews(newsId, null);
        if (update == 0) {
            throw new RRException("新闻不存在或不可删除");
        }
    }

    /**
     * 查询新闻列表
     */
    @Override
    public PageInfo queryNewsList(NewsInfoParam param) {
        //查找并验证代理商主账号信息
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        List<NewsInfoData> list = newsMapper.queryNewsList(param);
        PageInfo<NewsInfoData> pageInfo = new PageInfo<>(list);
        pageInfo.getList().forEach(e -> e.setAuditStateName(NewsAuditStateEnum.getDescriByCode(e.getAuditState())));
        return pageInfo;
    }

    /**
     * 发布我的新闻
     */
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void publicMyNews(NewsSaveParam param,Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        //60秒内只能发送一条新闻
        if (newsMapper.countByCreateByWithinSeconds(agentId, 60L) > 0) {
            throw new RRException("60s内只能发送一条新闻");
        }
        News news = new News();
        news.setId(snowflake.nextId());
        news.setAgentId(agentId);
        news.setTitle(param.getTitle());
        news.setContent(param.getMessage());
        news.setState(NewsAuditStateEnum.TO_AUDIT.getCode());
        news.setCreatetime(new Date());
        news.setUpdateTime(new Date());
        news.setVersion(0);
        if (newsMapper.insert(news) == 0) {
            throw new RRException("插入失败，请重试");
        }
    }

    /**
     * 修改我的新闻
     */
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void newsMyUpdate(NewsSaveParam param,Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        String newsId = param.getNewsId();
        if (StringUtils.isBlank(newsId)) {
            throw new RRException("参数校验失败，新闻ID不能为空");
        }
        News news = newsMapper.selectByPrimaryKey(newsId);
        Assert.notNull(news, "新闻不存在");
        if (!agentId.equals(news.getAgentId())) {
            throw new RRException("参数校验失败，新闻ID非法");
        }
        Integer auditState;
        //审核状态赋值
        if (NewsAuditStateEnum.TO_AUDIT.getCode().equals(news.getState())) {
            auditState = NewsAuditStateEnum.TO_AUDIT.getCode();
        } else if (NewsAuditStateEnum.MODIFIED.getCode().equals(news.getState()) || NewsAuditStateEnum.REJECTED.getCode().equals(news.getState())
                || NewsAuditStateEnum.AUDITED.getCode().equals(news.getState())) {
            auditState = NewsAuditStateEnum.MODIFIED.getCode();
        } else {
            throw new RRException("新闻为不可编辑状态");
        }
        news.setTitle(param.getTitle());
        news.setContent(param.getMessage());
        news.setState(auditState);
        news.setUpdateTime(new Date());
        //1.修改新闻
        if (newsMapper.modifyNews(news) == 0) {
            throw new RRException("修改失败，不可编辑状态");
        }
    }

    /**
     * 查看我的新闻详情
     */
    @Override
    public NewsInfoDetailData newsMyDetail(String newsId, Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        return newsDetail(newsId, agentId);
    }

    /**
     * 删除我的新闻
     */
    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public void newsMyDelete(Long newsId,Long agentId) {
        Assert.notNull(agentId, NON_AGENT_ROLE);
        //1.删除新闻
        if (newsMapper.deleteNews(newsId, agentId) == 0) {
            throw new RRException("新闻不存在或不可删除");
        }
    }


}
