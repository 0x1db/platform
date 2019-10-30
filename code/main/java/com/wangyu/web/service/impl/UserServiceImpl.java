package com.wangyu.web.user.service.impl;

import com.platform.core.service.impl.BaseServiceImpl;
import com.wangyu.web.dao.UserMapper;
import com.wangyu.web.domain.User;
import com.wangyu.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 
 * User服务层
 * 
 * @author wangyu
 * @date 2019-10-31 00:23
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    /**
    * sl4j
    */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

}