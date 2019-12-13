package com.wangyu.web.service.security;

import java.util.Collection;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

/**
 * @Author wangyu
 * @Date 2019-12-13 15:27
 */
public class MyAccessDecisionManager implements AccessDecisionManager {

  @Override
  public void decide(Authentication authentication, Object o,
      Collection<ConfigAttribute> collection)
      throws AccessDeniedException, InsufficientAuthenticationException {

  }

  @Override
  public boolean supports(ConfigAttribute configAttribute) {
    return false;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return false;
  }
}
