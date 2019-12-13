package com.wangyu.web.controller;

import com.platform.core.entity.ResponseModel;
import com.platform.core.entity.ResponseUtil;
import com.platform.core.web.controller.BaseController;
import com.wangyu.web.domain.Role;
import com.wangyu.web.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统角色信息控制器
 *
 * @Author wangyu
 * @Date 2019-12-13 11:55
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

  @Autowired
  private RoleService roleService;

  @PostMapping("/")
  public ResponseModel addRole(@RequestBody Role role) {
    roleService.insert(role);
    return ResponseUtil.success();
  }
}
