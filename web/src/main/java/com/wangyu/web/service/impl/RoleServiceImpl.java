package com.wangyu.web.service.impl;

import com.google.common.collect.Maps;
import com.platform.core.entity.ResponseCode;
import com.platform.core.exception.ParameterInvalidException;
import com.platform.core.service.impl.BaseServiceImpl;
import com.wangyu.web.dao.RoleMapper;
import com.wangyu.web.domain.Role;
import com.wangyu.web.service.RoleService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色信息服务层
 *
 * @author wangyu
 * @date 2019-11-05 00:45
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

  /**
   * sl4j
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

  @Autowired
  private RoleMapper roleMapper;

  @Override
  public Set<Role> findByUserId(Long userId) {
    if (userId == null) {
      throw new ParameterInvalidException(ResponseCode.PARAM_IS_BLANK, "用户ID不能为空");
    }
    return roleMapper.findByUserId(userId);
  }

  @Override
  public List<Role> findByResourceId(Long resourceId) {
    if (resourceId == null) {
      throw new ParameterInvalidException(ResponseCode.PARAM_IS_BLANK);
    }
    return roleMapper.findByResourceId(resourceId);
  }
}