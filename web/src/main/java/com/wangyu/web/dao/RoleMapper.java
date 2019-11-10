package com.wangyu.web.dao;

import com.platform.core.persistence.BaseMapper;
import com.wangyu.web.domain.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息 持久层Mapper
 *
 * @author wangyu
 * @date 2019-11-05 00:45
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {


}
