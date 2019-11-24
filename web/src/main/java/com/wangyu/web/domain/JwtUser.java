package com.wangyu.web.domain;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * JwtUser实体
 *
 * @author wangyu
 * @date 2019/11/24 16:24
 */
public class JwtUser implements UserDetails {

  private Long id;
  private String username;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public JwtUser(BaseUserInfo user, List<SimpleGrantedAuthority> authority) {
    id = user.getId();
    username = user.getUsername();
    password = user.getPassword();
    authorities = authority;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  /**
   * 账号是否未过期，默认是false
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * 校验账号是否被锁定,默认是false
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * 账号凭证是否未过期，默认是false，
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * 默认false
   */
  @Override
  public boolean isEnabled() {
    return true;
  }
}
