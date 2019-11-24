package com.wangyu.web.service.security;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.platform.core.entity.ResponseCode;
import com.platform.core.exception.UserVerificationException;
import com.wangyu.web.domain.BaseUserInfo;
import com.wangyu.web.domain.JwtUser;
import com.wangyu.web.domain.Role;
import com.wangyu.web.domain.SysUser;
import com.wangyu.web.service.BaseUserInfoService;
import com.wangyu.web.service.RoleService;
import com.wangyu.web.service.SysUserService;
import java.util.List;
import java.util.Set;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author wangyu
 * @date 2019/11/24 16:22
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private BaseUserInfoService baseUserInfoService;

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private RoleService roleService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    BaseUserInfo userInfo = baseUserInfoService.findByUsername(username);
    //查询系统用户ID
    SysUser sysUser = sysUserService.findByBaseUserInfoId(userInfo.getId());
    List<SimpleGrantedAuthority> authorities = Lists.newArrayList();

    //获取用户角色信息
    Set<Role> roles = roleService.findByUserId(userInfo.getId());
    if (roles == null || roles.isEmpty()) {
      throw new UserVerificationException(ResponseCode.USER_NOT_ROLES);
    }
    roles.forEach(role -> {
      SimpleGrantedAuthority authoritie = new SimpleGrantedAuthority(role.getName());
      authorities.add(authoritie);
    });
    return new JwtUser(userInfo, authorities);
  }
}
