package com.wangyu.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.core.service.impl.BaseServiceImpl;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.wangyu.web.dao.SysUserMapper;
import com.wangyu.web.domain.BaseUserInfo;
import com.wangyu.web.domain.SysUser;
import com.wangyu.web.service.BaseUserInfoService;
import com.wangyu.web.service.SysUserService;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.validator.constraints.EAN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 后台用户服务层
 *
 * @author wangyu
 * @date 2019-11-05 21:23
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private BaseUserInfoService userInfoService;

    @Override
    public int insert(BaseUserInfo baseUserInfo) {
        userInfoService.insert(baseUserInfo);
        SysUser sysUser = new SysUser();
        //基础用户信息ID
        sysUser.setBaseUserId(baseUserInfo.getId());
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUserMapper.insert(sysUser);
        return 0;
    }

    @Override
    public PageInfo<SysUser> findPages(Map<String, Object> params) {
        //分页查询排序条件 "字段 空格 排序方式"
        String orderBy = "sys_user.create_date desc";
        int pageSize = Integer.parseInt(String.valueOf(params.get("pageSize")));
        int pageNum = Integer.parseInt(String.valueOf(params.get("pageNum")));
        PageHelper.startPage(pageNum, pageSize, orderBy);

        //编写xml分页方法 自定义多条件查询
        List<SysUser> list = sysUserMapper.findPages(params);
        PageInfo page = new PageInfo(list);
        return page;
    }
}