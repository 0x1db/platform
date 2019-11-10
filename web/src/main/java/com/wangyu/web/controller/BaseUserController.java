package com.wangyu.web.controller;

import com.platform.core.entity.ResponseModel;
import com.platform.core.web.controller.BaseController;
import com.wangyu.web.domain.BaseUserInfo;
import com.wangyu.web.service.BaseUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangyu
 * @date 2019/11/3 1:24
 */
@RestController
@RequestMapping("/base_user")
public class BaseUserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseUserInfoService baseUserInfoService;

    @GetMapping("/test")
    public void test() {
        logger.info("test");
        logger.error("test");
        logger.warn("12321312");
    }

    /**
     * 新增
     *
     * @param baseUserInfo
     * @return
     */
    @PostMapping("/")
    public ResponseModel add(@RequestBody BaseUserInfo baseUserInfo) {
        int insert = baseUserInfoService.insert(baseUserInfo);
        return this.buildSuccessResult(insert);
    }
}
