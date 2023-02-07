package com.cc.oem.modules.agent.service.impl;

import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.service.UploadImageService;
import com.cc.oem.modules.agent.service.UploadPictureService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author chenzj
 * @since 2018/8/8
 */
@Service
public class UploadImageServiceImpl implements UploadImageService {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${fileUploadPath.common}")
    private String fileUploadPath;
    @Autowired
    UploadPictureService uploadPictureService;


    @Override
    public String uploadLicense(Long sysUserId, MultipartFile file, Integer imageType) {
        String agentPirctureUrl = uploadPictureService.uploadAgentPictureService(sysUserId, file, imageType);
        return agentPirctureUrl;
    }

    @Override
    public R downLoadImage(String path, HttpServletResponse response) {
        String realPath = fileUploadPath + "/" + path;
        try {
            InputStream in = new FileInputStream(realPath);
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            out.flush();
            out.close();
            in.close();
        } catch (FileNotFoundException e) {
            logger.error("图片下载失败", e);
            return R.error(HttpStatus.SC_BAD_REQUEST, "路径不存在");
        } catch (IOException e) {
            logger.error("图片下载失败", e);
            return R.error(HttpStatus.SC_BAD_REQUEST, e.getMessage());
        }
        return R.ok();
    }

}
