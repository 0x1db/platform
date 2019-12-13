package com.wangyu.web.dao;

import com.platform.core.persistence.BaseMapper;
import com.wangyu.web.domain.Role;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 角色信息 持久层Mapper
 *
 * @author wangyu
 * @date 2019-11-05 00:45
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {


  /**
   * 根据用户ID查询角色信息
   *
   * @param userId 系统用户ID
   * @return set
   */
  Set<Role> findByUserId(Long userId);

  /**
   * 根据资源ID查询角色信息
   *
   * @param resourceId
   * @return
   */
  List<Role> findByResourceId(Long resourceId);
}
