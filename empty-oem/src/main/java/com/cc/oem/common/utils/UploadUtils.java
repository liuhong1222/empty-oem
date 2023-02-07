package com.cc.oem.common.utils;

import io.swagger.models.auth.In;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

/**
 * @author chenzj
 * @since 2018/6/6
 */
@Component
public class UploadUtils {

    private static final Logger log = LoggerFactory.getLogger(UploadUtils.class);
    /**
     * 上传图片  存放路径: 201806/3efff300a48a4d3dbcd6c0c1eac49f44.png
     */
    public String uploadPicture(MultipartFile file, String basePath) throws IOException {
        String fileName;
        String type;
        String suffix;
        try {
            SimpleImageInfo imageInfo = new SimpleImageInfo(file.getInputStream());
            type = imageInfo.getImageType().getType();
            suffix = "." + type;
            fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        } catch (IOException e) {
            throw new RuntimeException("无法识别图片类型");
        }
        String yearMonth = DateTimeUtil.formateDateToYearMonthInt(new Date()) + "/";


        String path =  basePath  + "/" + yearMonth + fileName;
        File targetFile = new File(path);
        if(!targetFile.getParentFile().exists()){
            targetFile.getParentFile().mkdirs();
        }
        // 上传
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
        } catch (IOException e) {

            throw new RuntimeException("无法保存图片");
        }
        return yearMonth + fileName;
    }

    /**
     * 上传txt  存放路径: 201806/3efff300a48a4d3dbcd6c0c1eac49f44.txt
     */
    public String uploadTxt(MultipartFile file, String basePath, String folderName) {
        String fileName;
        String type = "txt";
        String suffix;
        suffix = "." + type;
        fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;

        String yearMonth = String.valueOf(DateTimeUtil.formateDateToYearMonthInt(new Date())) + "/";
        String path = basePath + folderName + "/" + yearMonth;
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        // 上传
        try {
            Files.write(Paths.get(path + fileName), file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("无法保存文件");
        }
        return folderName + "/" + yearMonth + fileName;
    }


    public String uploadMobileCube(MultipartFile file,String parentPath) {

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        String targetfileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        String path =  parentPath + fileName;
        File targetFile = new File(path);
        if(!targetFile.getParentFile().exists()){
            targetFile.getParentFile().mkdirs();
        }
        // 上传
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
        } catch (IOException e) {
        	log.error("号码魔方文件包替换异常，fileName:{},info:",fileName,e);
            throw new RuntimeException("无法保存");
        }
        return fileName;
    }
}
