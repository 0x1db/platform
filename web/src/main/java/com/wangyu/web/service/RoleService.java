package com.wangyu.web.service;

import com.platform.core.service.BaseService;
import com.wangyu.web.domain.Role;
import java.util.List;
import java.util.Set;

/**
 * 角色信息服务层
 *
 * @author wangyu
 * @date 2019-11-05 00:45
 */
public interface RoleService extends BaseService<Role> {

  /**
   * 根据用户ID查询角色信息
   *
   * @param userId 用户ID
   * @return 角色集合
   */
  Set<Role> findByUserId(Long userId);

  /**
   * 根据资源ID查询
   *
   * @param id
   * @return
   */
  List<Role> findByResourceId(Long id);
}