package com.wangyu.web.service;

import com.github.pagehelper.PageInfo;
import com.platform.core.service.BaseService;
import com.wangyu.web.domain.BaseUserInfo;
import com.wangyu.web.domain.SysUser;
import com.wangyu.web.dto.SysUserDTO;

import java.util.Map;

/**
 * 后台用户服务层
 *
 * @author wangyu
 * @date 2019-11-05 21:23
 */
public interface SysUserService extends BaseService<SysUser> {

  /**
   * 新增后台用户
   *
   * @param sysUserDTO 系统用户传输对象
   */
  int insert(SysUserDTO sysUserDTO);

  /**
   * 条件分页查询
   *
   * @param params 参数
   */
  PageInfo<SysUser> findPages(Map<String, Object> params);

  /**
   * 修改后台用户信息
   */
  void update(SysUserDTO sysUserDTO);

  /**
   * 根据基本用户ID查询系统用户ID
   */
  SysUser findByBaseUserInfoId(Long baseUserId);
}