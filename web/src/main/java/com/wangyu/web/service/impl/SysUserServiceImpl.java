package com.wangyu.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.common.enums.UserTypeEnum;
import com.platform.core.entity.ResponseCode;
import com.platform.core.exception.ParameterInvalidException;
import com.platform.core.exception.UserVerificationException;
import com.platform.core.service.impl.BaseServiceImpl;
import com.wangyu.web.dao.SysUserMapper;
import com.wangyu.web.domain.BaseUserInfo;
import com.wangyu.web.domain.Role;
import com.wangyu.web.domain.SysUser;
import com.wangyu.web.dto.SysUserDTO;
import com.wangyu.web.service.BaseUserInfoService;
import com.wangyu.web.service.RoleService;
import com.wangyu.web.service.SysUserService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  private RoleService roleService;

  @Override
  public int insert(SysUserDTO sysUserDTO) {
    BaseUserInfo userInfo = new BaseUserInfo();
    BeanUtils.copyProperties(sysUserDTO, userInfo);
    userInfo.setType(UserTypeEnum.MANAGE_USER);
    userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
    LOGGER.info("加密密码[{}]", bCryptPasswordEncoder.encode(userInfo.getPassword()));
    userInfoService.insert(userInfo);

    //基础用户信息ID
    SysUser sysUser = sysUserDTO.convertToSysUser();
    sysUser.setBaseUserId(userInfo.getId());
    sysUser.setCreateDate(System.currentTimeMillis());
    return sysUserMapper.insert(sysUser);
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

  @Override
  public void update(SysUserDTO sysUserDTO) {
    if (sysUserDTO.getBaseUserId() == null || sysUserDTO.getId() == null) {
      throw new ParameterInvalidException(ResponseCode.PARAM_IS_BLANK,
          "baseInfoId and id is not be null");
    }
    BaseUserInfo userInfo = userInfoService.get(sysUserDTO.getBaseUserId());
    BeanUtils.copyProperties(sysUserDTO, userInfo);
    userInfo.setType(UserTypeEnum.MANAGE_USER);
    userInfoService.update(userInfo);

    SysUser sysUser = sysUserMapper.get(sysUserDTO.getId());
    sysUser.setGender(sysUserDTO.getGender());
    sysUser.setHeadImg(sysUserDTO.getHeadImg());
    sysUser.setEmail(sysUserDTO.getEmail());
    sysUser.setModifyDate(System.currentTimeMillis());

    sysUserMapper.update(sysUser);
  }

  @Override
  public SysUser findByBaseUserInfoId(Long baseUserId) {
    if (baseUserId == null) {
      throw new ParameterInvalidException(ResponseCode.PARAM_IS_BLANK,
          "baseInfoId and id is not be null");
    }
    return sysUserMapper.findByBaseUserInfoId(baseUserId);
  }

  @Override
  public void roleSetup(Long userId, Long roleId) {
    //校验参数
    if (userId == null || roleId == null) {
      throw new ParameterInvalidException(ResponseCode.PARAM_IS_BLANK,
          "parameter is not be null");
    }

    //查询校验用户
    SysUser sysUser = sysUserMapper.get(userId);
    if (sysUser == null) {
      throw new UserVerificationException(ResponseCode.USER_NOT_EXIST, "user is not exists");
    }

    //校验角色
    Role role = roleService.get(roleId);
    if (role == null) {
      throw new UserVerificationException(ResponseCode.USER_NOT_ROLES, "role is not exists");
    }

    //设置角色
    sysUserMapper.roleSetup(userId, roleId);
  }
}