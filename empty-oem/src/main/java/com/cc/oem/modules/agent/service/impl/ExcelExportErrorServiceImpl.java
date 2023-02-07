package com.cc.oem.modules.agent.service.impl;

import com.cc.oem.modules.agent.constant.ExcelExportErrorHtml;
import com.cc.oem.modules.agent.service.ExcelExportErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author chenzj
 * @since 2018/9/11
 */
@Service
public class ExcelExportErrorServiceImpl implements ExcelExportErrorService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void error(HttpServletResponse response, Exception e) throws IOException {
        logger.error("导出excel出错了",e);
        PrintWriter pw = new PrintWriter(response.getOutputStream());
        String msg = ExcelExportErrorHtml.format(e.getMessage());
        pw.write(msg);
        pw.flush();
    }

}
