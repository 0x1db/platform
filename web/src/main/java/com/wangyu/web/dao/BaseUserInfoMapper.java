package com.wangyu.web.dao;

import com.platform.core.persistence.BaseMapper;
import com.wangyu.web.domain.BaseUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户基本信息 持久层Mapper
 *
 * @author wangyu
 * @date 2019-11-02 17:36
 */
@Mapper
@Repository
public interface BaseUserInfoMapper extends BaseMapper<BaseUserInfo> {


  /**
   * 根据用户名查询
   *
   * @param username 用户名
   * @return BaseUserInfo 基础用户实体
   */
  BaseUserInfo findByUsername(String username);
}
