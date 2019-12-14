package com.wangyu.web.service.security;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wangyu.web.domain.Resource;
import com.wangyu.web.domain.Role;
import com.wangyu.web.service.ResourceService;
import com.wangyu.web.service.RoleService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

/**
 * 权限资源管理器 为权限决断器提供支持
 *
 * @Author wangyu
 * @Date 2019-12-13 15:28
 */
@Service("mySecurityMetadataSource")
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

  @Autowired
  private ResourceService resourceService;
  @Autowired
  private RoleService roleService;

  /**
   * 忽略权限判断的url
   */
  @Value("${author.ignoreUrls}")
  private String[] ignoreUrls;

  private Map<String, Collection<ConfigAttribute>> map = null;

  /**
   * 加载权限表中所有操作请求权限
   */
  public void loadResourceDefine() {
    map = Maps.newHashMapWithExpectedSize(16);
    Collection<ConfigAttribute> configAttributes;
    ConfigAttribute cfg;
    // 获取启用的权限操作请求
    List<Resource> permissions = resourceService.findAll();
    for (Resource permission : permissions) {
      if (StringUtils.isNotBlank(permission.getFlag()) && StringUtils
          .isNotBlank(permission.getUrl())) {
        configAttributes = Lists.newArrayList();
        cfg = new SecurityConfig(permission.getFlag());
        //作为MyAccessDecisionManager类的decide的第三个参数
        configAttributes.add(cfg);
        //用权限的path作为map的key，用ConfigAttribute的集合作为value
        map.put(permission.getUrl(), configAttributes);
      }
    }
  }

  /**
   * 判定用户请求的url是否在权限表中 如果在权限表中，则返回给decide方法，用来判定用户是否有此权限 如果不在权限表中则放行
   *
   * @param o
   * @return
   * @throws IllegalArgumentException
   */
  @Override
  public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
    FilterInvocation filterInvocation = (FilterInvocation) o;
    String url = filterInvocation.getHttpRequest().getRequestURI();
    HttpServletRequest request = filterInvocation.getHttpRequest();
    List<ConfigAttribute> configs = new ArrayList<>();
    String method = request.getMethod();

    for (String ignoreUrl : ignoreUrls) {
      AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(ignoreUrl);
      if (requestMatcher.matches(request)) {
        return this.fillAnonymous(configs);
      }
    }

    // 2、===================
    int index = 0;
    String matchUrl = "";
    List<Resource> currentResource = null;
    try {
      URI currentUri = new URI(url);
      do {
        // 拼凑对比url用的表达式
        matchUrl = currentUri.toString();
        for (int sum = 0; sum < index; sum++) {
          matchUrl += "/{param}";
        }
        // 查询数据库，找到可能存在的满足url格式的功能信息
        currentResource = this.queryByRequestUrl(matchUrl);
        // 如果条件成立，说明找到了那个要找的权限功能绑定信息
        if (currentResource != null && !currentResource.isEmpty()) {
          break;
        }

        currentUri = currentUri.resolve(".");
        // 去掉最后的“/”
        String currentUriValue = currentUri.toString();
        currentUriValue = currentUriValue.substring(0, currentUriValue.length() - 1);
        if (StringUtils.equals("", currentUriValue)) {
          break;
        }
        // 这是上一级uri
        currentUri = new URI(currentUriValue);
        index++;
      } while (true);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    //进行request method的过滤，只有满足method方式的角色名，才能写入到configs集合中
    for (Resource resource : currentResource) {
      String methods = resource.getMethod();
      if (methods.indexOf(method) != -1) {
        // 取得当前权限和角色的绑定关系
        List<Role> roles = roleService.findByResourceId(resource.getId());
        if (roles != null && !roles.isEmpty()) {
          for (Role role : roles) {
            SecurityConfig securityConfig = new SecurityConfig(role.getFlag());
            configs.add(securityConfig);
          }
        }
      }
    }

    if (configs.isEmpty()) {
      return this.fillAnonymous(configs);
    } else {
      return configs;
    }
  }

  private List<Resource> queryByRequestUrl(String matchUrl) {
    //匹配url 查看url是否存在
    return resourceService.findByUrl(matchUrl);
  }

  /**
   * 在角色集合为empty的时候，默认填充“超级管理员”角色
   *
   * @param configs
   * @return
   */
  private Collection<ConfigAttribute> fillAnonymous(List<ConfigAttribute> configs) {
    if (configs.isEmpty()) {
      configs.add(new SecurityConfig("ROLE_ADMIN"));
      configs.add(new SecurityConfig("ROLE_ANONYMOUS"));
    }
    return configs;
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return true;
  }
}
