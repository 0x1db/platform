package com.wangyu.web.config.filter;

import com.google.common.collect.Lists;
import com.platform.common.utils.JwtUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * 鉴权拦截
 *
 * @author wangyu
 * @date 2019/11/24 16:58
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    String tokenHeader = request.getHeader(JwtUtils.TOKEN_HEADER);
    // 如果请求头中没有Authorization信息则直接放行了
    if (tokenHeader == null || !tokenHeader.startsWith(JwtUtils.TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    // 如果请求头中有token，则进行解析，并且设置认证信息
    SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
    super.doFilterInternal(request, response, chain);
  }

  /**
   * 这里从token中获取用户信息并新建一个token
   */
  private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
    String token = tokenHeader.replace(JwtUtils.TOKEN_PREFIX, "");
    String username = JwtUtils.getUsername(token);
    //获取角色信息
    List<String> userRoles = JwtUtils.getUserRoles(token);
    //封装角色信息到UsernamePasswordAuthenticationToken中
    List<SimpleGrantedAuthority> authorities = Lists.newArrayList();
    userRoles.forEach(role -> {
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
      authorities.add(authority);
    });

    if (username != null) {
      return new UsernamePasswordAuthenticationToken(username, null,
          authorities);
    }
    return null;
  }
}
