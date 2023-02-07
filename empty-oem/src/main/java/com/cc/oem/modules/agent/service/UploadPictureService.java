package com.cc.oem.modules.agent.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author wade
 */
public interface UploadPictureService {

    /**
     * 上传图片文件
     */
    String uploadAgentPictureService(Long sysUserId, MultipartFile file, Integer type);

}
