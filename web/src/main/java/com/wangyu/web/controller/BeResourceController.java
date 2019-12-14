package com.wangyu.web.controller;

import com.platform.core.entity.ResponseModel;
import com.platform.core.entity.ResponseUtil;
import com.platform.core.web.controller.BaseController;
import com.wangyu.web.domain.Resource;
import com.wangyu.web.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源权限控制器
 *
 * @author wangyu
 * @date 2019/12/14 13:36
 */
@RestController
@RequestMapping("/resource")
public class BeResourceController extends BaseController {

  @Autowired
  private ResourceService resourceService;

  /**
   * 新增资源
   */
  @PostMapping("/")
  public ResponseModel addResource(@RequestBody Resource resource) {
    resourceService.insert(resource);
    return ResponseUtil.success();
  }

  /**
   * 修改资源
   */
  @PatchMapping("/")
  public ResponseModel updateResource(@RequestBody Resource resource) {
    resourceService.update(resource);
    return ResponseUtil.success();
  }
}
