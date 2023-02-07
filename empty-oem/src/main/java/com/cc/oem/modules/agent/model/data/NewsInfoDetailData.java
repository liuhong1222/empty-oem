package com.cc.oem.modules.agent.model.data;

import lombok.Data;

/**
 * @author chenzj
 * @since 2018/10/8
 */
@Data
public class NewsInfoDetailData {

    /**
     * 新闻id
     */
    private String id;


    /**
     * 新闻标题
     */
    private String title;

    /**
     * 新闻内容
     */
    private String content;

}
