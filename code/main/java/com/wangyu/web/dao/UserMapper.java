package com.wangyu.web.user.dao;

import com.platform.core.persistence.BaseMapper;
import com.wangyu.web.domain.User;
import org.apache.ibatis.annotations.Mapper;

/** 
 * User 持久层Mapper
 * 
 * @author wangyu
 * @date 2019-10-31 00:23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


}
