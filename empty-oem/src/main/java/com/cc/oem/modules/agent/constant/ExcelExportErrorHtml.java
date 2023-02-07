package com.cc.oem.modules.agent.constant;

/**
 * @author chenzj
 * @since 2018/9/11
 */
public class ExcelExportErrorHtml {
    private static final String ERROR_HTML="<html>"+
            "<head>"+
            "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> "+
            "  <title>导出错误</title>"+
            "</head>"+
            "<body>"+
            "  <div style=\"margin:0 auto; width:400px; height:80px;position:absolute; left:0; top: 0; bottom: 0; right: 0; margin: auto; \">╮(╯﹏╰)╭&nbsp;&nbsp; 出错了，错误原因：%s</div>"+
            "</body>"+
            "</html>";

    public static String format(String message){
        return String.format(ERROR_HTML, message);
    }

}
