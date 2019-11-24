package com.wangyu.web.controller;

import com.platform.common.utils.FileUploadUtils;
import com.platform.core.entity.ResponseCode;
import com.platform.core.entity.ResponseModel;
import com.platform.core.entity.ResponseUtil;
import com.platform.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author wangyu
 * @date 2019/11/19 22:48
 */
@CrossOrigin
@RestController
@RequestMapping("/upload")
public class UploadController extends BaseController {

    @Value("${upload.headImg.win_location}")
    private String winLocation;

    /**
     * 头像上传
     */
    @PostMapping("/headImg")
    public ResponseModel uploadHeadImg(MultipartFile file) {
        try {
            FileInputStream inputStream = (FileInputStream) file.getInputStream();
            String name = file.getOriginalFilename();
          String filePath = FileUploadUtils.uploadImg(inputStream, winLocation, name);
            return ResponseUtil.success(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseUtil.error(ResponseCode.PARAM_IS_BLANK, e.getMessage());
        }
    }
}
