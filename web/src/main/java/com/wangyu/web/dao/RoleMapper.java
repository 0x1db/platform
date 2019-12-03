package com.wangyu.web.dao;

import com.platform.core.persistence.BaseMapper;
import com.wangyu.web.domain.Role;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息 持久层Mapper
 *
 * @author wangyu
 * @date 2019-11-05 00:45
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {


  /**
   * 根据用户ID查询角色信息
   *
   * @param userId 系统用户ID
   * @return set
   */
  Set<Role> findByUserId(Long userId);
}
