package com.cc.oem.modules.agent.service;

import com.cc.oem.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author chenzj
 * @since 2018/8/8
 */
public interface UploadImageService {


    String uploadLicense(Long sysUserId, MultipartFile file, Integer imageType);

    R downLoadImage(String path, HttpServletResponse response);

}
