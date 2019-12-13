package com.wangyu.web.dao;

import com.platform.core.persistence.BaseMapper;
import com.wangyu.web.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 后台用户 持久层Mapper
 *
 * @author wangyu
 * @date 2019-11-05 21:23
 */
@Mapper
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {


  /**
   * 条件分页查询
   */
  List<SysUser> findPages(Map<String, Object> params);

  /**
   * 根据基本用户ID查询系统用户信息
   */
  SysUser findByBaseUserInfoId(Long baseUserId);

  /**
   * 用户角色信息设置
   *
   * @param userId
   * @param roleId
   */
  void roleSetup(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
