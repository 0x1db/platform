package com.wangyu.web.service;

import com.github.pagehelper.PageInfo;
import com.platform.core.service.BaseService;
import com.wangyu.web.domain.BaseUserInfo;
import com.wangyu.web.domain.SysUser;

import java.util.Map;

/**
 * 后台用户服务层
 *
 * @author wangyu
 * @date 2019-11-05 21:23
 */
public interface SysUserService extends BaseService<SysUser> {
    /**
     * 新增后台用户
     *
     * @param userInfo 基础用户信息
     * @return
     */
    int insert(BaseUserInfo userInfo);

    /**
     * 条件分页查询
     *
     * @param params 参数
     * @return
     */
    PageInfo<SysUser> findPages(Map<String, Object> params);
}