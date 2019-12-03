package com.wangyu.web.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.platform.common.utils.JwtUtils;
import com.platform.core.entity.ResponseCode;
import com.platform.core.entity.ResponseModel;
import com.platform.core.entity.ResponseUtil;
import com.wangyu.web.domain.BaseUserInfo;
import com.wangyu.web.domain.JwtUser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 拦截用户登录信息
 *
 * @author wangyu
 * @date 2019/11/24 16:50
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    //登录请求地址
    super.setFilterProcessesUrl("/sys_user/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    // 从输入流中获取到登录的信息
    try {
      BaseUserInfo userInfo = new ObjectMapper()
          .readValue(request.getInputStream(), BaseUserInfo.class);
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userInfo.getUsername(), userInfo.getPassword(),
              new ArrayList<>())
      );
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  //
  //

  /**
   * 成功验证后调用的方法 如果验证成功，就生成token并返回
   */
  @Override
  protected void successfulAuthentication(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult) throws IOException, ServletException {

    // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
    // 所以就是JwtUser啦
    JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
    Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
    String[] roles = new String[authorities.size()];
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    while (iterator.hasNext()) {
      GrantedAuthority authority = iterator.next();
      int i = 0;
      roles[i] = authority.getAuthority();
      i++;
    }

    String token = JwtUtils
        .createToken(jwtUser.getUsername(), false, roles);
    // 返回创建成功的token
    // 但是这里创建的token只是单纯的token
    // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
    response.setHeader("token", JwtUtils.TOKEN_PREFIX + token);
  }

  /**
   * 这是验证失败时候调用的方法
   */
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    ResponseModel error = ResponseUtil
        .error(ResponseCode.USER_ACCOUNT_FORBIDDEN, failed.getMessage());
    ObjectMapper mapper = new ObjectMapper();
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    mapper.writeValue(response.getWriter(), error);
  }
}
