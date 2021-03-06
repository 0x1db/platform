package com.wangyu.web.service.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

/**
 * 权限管理拦截器，监控用户行为
 *
 * @Author wangyu
 * @Date 2019-12-13 15:21
 */
@Service("myFilterSecurityInterceptor")
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

  @Autowired
  private FilterInvocationSecurityMetadataSource securityMetadataSource;

  @Autowired
  public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
    super.setAccessDecisionManager(myAccessDecisionManager);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    FilterInvocation fi = new FilterInvocation(request, response, chain);
    invoke(fi);
  }

  @Override
  public void destroy() {

  }

  public void invoke(FilterInvocation fi) throws IOException, ServletException {
    InterceptorStatusToken token = super.beforeInvocation(fi);
    try {
      fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
    } finally {
      super.afterInvocation(token, null);
    }
  }

  @Override
  public Class<?> getSecureObjectClass() {
    return FilterInvocation.class;
  }

  @Override
  public SecurityMetadataSource obtainSecurityMetadataSource() {
    return this.securityMetadataSource;
  }
}
