package com.cc.oem.modules.agent.service.impl;

import com.cc.oem.common.exception.RRException;
import com.cc.oem.common.utils.UploadUtils;
import com.cc.oem.modules.agent.service.UploadPictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author chenzj
 * @since 2018/8/10
 */
@Service
public class UploadPictureServiceImpl implements UploadPictureService {

    private static final Logger logger = LoggerFactory.getLogger(UploadPictureServiceImpl.class);

    @Value("${fileUploadPath.common}")
    private String commonPrefix;
    @Value("${fileUploadPath.picture.license}")
    private String licensePath;

    @Autowired
    UploadUtils uploadUtils;


    @Override
    public String uploadAgentPictureService(Long sysUserId, MultipartFile file, Integer type) {
        try {
            logger.info("上传图片(sysUserId:{},type:{})", sysUserId, type);
            String basePath = commonPrefix;
            if(type==0){
                //营业执照
                basePath +=licensePath;
            }
            String fileUrl = uploadUtils.uploadPicture(file, basePath);
            if(type==0){
                fileUrl = licensePath+ File.separator+fileUrl;
            }
            return fileUrl;
        } catch (IOException ex){
            throw new RRException("AgentLevel出错");
        }
    }
}
