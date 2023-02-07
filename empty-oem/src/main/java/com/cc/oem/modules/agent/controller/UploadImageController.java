package com.cc.oem.modules.agent.controller;

import com.cc.oem.common.annotation.SysLog;
import com.cc.oem.common.utils.R;
import com.cc.oem.modules.agent.service.UploadImageService;
import com.cc.oem.modules.sys.controller.AbstractController;
import com.cc.oem.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzj
 * @since 2018/8/8
 */
@RestController
@RequestMapping("/open/file")
@Api("图片上传操作")
public class UploadImageController extends AbstractController {

    @Autowired
    UploadImageService uploadImageService;
    @Autowired
    SysUserService sysUserService;

    /**
     * 0 营业执照,1, "代表签字图片,2, "公司红章图片,3 logo，4icon,5 客户身份证正面，6 客户身份证反面
     */
    //@RepeatCommitToken
    @PostMapping("/image/upload")
    public R uploadLicense(@Valid MultipartFile file, Integer imageType) {
        long fileSize = file.getSize();
        if (fileSize <= 0) {
            return R.error("上传失败:文件为空");
        } else if (fileSize > (50 * 1024 * 1024)) {
            return R.error("上传失败:文件大小不能超过50M");
        }
        String agentPictureUrl = uploadImageService.uploadLicense(getUserId(), file, imageType);
        Map<String, String> map = new HashMap<>();
        map.put("licenseUrl", agentPictureUrl);
        return R.ok(map);
    }

    /**
     * http://172.16.4.160:9999/open/file/download?path=201808/1.jpg&token=02993b684849f10c4aed93519cdd1c57
     * 前端暂时没有采用，这个属于后期加的
     */
    @SysLog("文件下载")
    @GetMapping("/download")
    public R uploadLicense(String path, HttpServletResponse response) {
        if (StringUtils.isBlank(path) || path.contains("..")) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "路径不对!");
        }
        return uploadImageService.downLoadImage(path, response);
    }


}
