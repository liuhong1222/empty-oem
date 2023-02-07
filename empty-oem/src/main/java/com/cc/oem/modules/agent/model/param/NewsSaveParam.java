package com.cc.oem.modules.agent.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 新闻列表查询参数
 *
 */
@Data
public class NewsSaveParam {
    /**
     * 新闻id
     */
    private String newsId;

    /**
     * 新闻标题
     */
    @NotBlank(message = "新闻标题不能为空")
    private String title;

    /**
     * 新闻内容带标签
     */
    @NotBlank(message = "新闻内容不能为空")
    private String message;
    
    /**
     * 新闻内容不带标签
     */
    @NotBlank(message = "新闻内容不能为空")
    private String newsContent;
	
}
