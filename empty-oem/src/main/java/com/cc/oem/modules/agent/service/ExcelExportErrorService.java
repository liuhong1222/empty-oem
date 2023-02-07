package com.cc.oem.modules.agent.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenzj
 * @since 2018/9/11
 */
public interface ExcelExportErrorService {

    void error(HttpServletResponse response,Exception e) throws IOException;

}
