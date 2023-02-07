package com.cc.oem.modules.agent.service;

import java.util.Map;

/**
 * @author chenzj
 * @since 2018/8/31
 */
public interface OcrService {

    Map<String, String> agentBizLicenseByFilePath(String filePath, String orderNo) throws Exception;

}
