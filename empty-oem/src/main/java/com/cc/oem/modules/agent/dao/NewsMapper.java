package com.cc.oem.modules.agent.dao;


import com.cc.oem.modules.agent.entity.News;
import com.cc.oem.modules.agent.model.data.NewsInfoData;
import com.cc.oem.modules.agent.model.param.NewsInfoParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NewsMapper {

    News selectByPrimaryKey(String id);

    int insert(News record);

    /**
     * 查询新闻列表
     */
    List<NewsInfoData> queryNewsList(NewsInfoParam param);

    /**
     * 审核新闻
     */
    int auditNews(@Param("id") String id, @Param("auditState") Integer auditState, @Param("auditRemark") String auditRemark);

    /**
     * 修改新闻
     */
    int modifyNews(News news);

    /**
     * 删除新闻
     */
    int deleteNews(@Param("id") Long id, @Param("agentId") Long agentId);

    /**
     * 数秒内最近的新闻
     */
    long countByCreateByWithinSeconds(@Param("agentId") Long agentId, @Param("seconds") Long seconds);

}
