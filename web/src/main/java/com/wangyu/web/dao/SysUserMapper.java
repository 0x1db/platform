package com.wangyu.web.dao;

import com.platform.core.persistence.BaseMapper;
import com.wangyu.web.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 后台用户 持久层Mapper
 *
 * @author wangyu
 * @date 2019-11-05 21:23
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {


    /**
     * 条件分页查询
     *
     * @param params
     * @return
     */
    List<SysUser> findPages(Map<String, Object> params);
}