package com.wangyu.web.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.platform.core.entity.ResponseModel;
import com.platform.core.entity.ResponseUtil;
import com.platform.core.web.controller.BaseController;
import com.wangyu.web.domain.SysUser;
import com.wangyu.web.dto.SysUserDTO;
import com.wangyu.web.service.SysUserService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台用户控制器
 *
 * @author wangyu
 * @date 2019/11/6 0:19
 */
@CrossOrigin
@RestController
@RequestMapping("/sys_user")
public class SysUserController extends BaseController {

  @Autowired
  private SysUserService sysUserService;

  /**
   * 注册后台用户
   */
  @PostMapping("/")
  public ResponseModel addUser(@RequestBody @Valid SysUserDTO sysUserDTO) {
    sysUserService.insert(sysUserDTO);
    return this.buildSuccessResult();
  }

  /**
   * 条件分页查询
   */
  @GetMapping("/paging")
  public ResponseModel getPages(@RequestParam("pageSize") @Valid Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "15") Integer pageNum,
      @RequestParam("baseUserId") String baseUserId) {
    Map<String, Object> params = Maps.newHashMap();
    params.put("pageNum", pageNum);
    params.put("pageSize", pageSize);
    params.put("baseUserId", baseUserId);
    PageInfo<SysUser> pages = sysUserService.findPages(params);
    return this.buildSuccessResult(pages);
  }

  /**
   * 删除后台用户
   */
  @DeleteMapping("/")
  public ResponseModel deleteUser(@RequestParam("id") String id) {
    sysUserService.delete(id);
    return this.buildSuccessResult();
  }

  /**
   * 根据ID查询后台用户详情
   */
  @GetMapping("/{id}")
  public ResponseModel findById(@PathVariable("id") String id) {
    SysUser sysUser = sysUserService.get(id);
    SysUserDTO result = new SysUserDTO().convertFor(sysUser);
    return ResponseUtil.success(result);
  }

  /**
   * 后台用户信息修改
   */
  @PatchMapping("/")
  public ResponseModel updateUser(@Valid SysUserDTO sysUserDTO) {
    sysUserService.update(sysUserDTO);
    return this.buildSuccessResult();
  }


}
