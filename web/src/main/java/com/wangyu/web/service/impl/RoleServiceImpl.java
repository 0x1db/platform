package com.wangyu.web.service.impl;

import com.platform.core.service.impl.BaseServiceImpl;
import com.wangyu.web.dao.RoleMapper;
import com.wangyu.web.domain.Role;
import com.wangyu.web.service.RoleService;
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

}