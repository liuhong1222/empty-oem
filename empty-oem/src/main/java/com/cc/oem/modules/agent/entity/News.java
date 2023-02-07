package com.cc.oem.modules.agent.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("news")
public class News {
    private Long id;

    private Long agentId;

    private String agentName;

    private String title;

    private Integer state;

    private Integer version;

    private Date createtime;

    private String content;

    private Date updateTime;

    private String remark;
	
}
