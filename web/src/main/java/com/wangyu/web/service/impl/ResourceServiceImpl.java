package com.wangyu.web.service.impl;

import com.google.common.collect.Maps;
import com.platform.core.service.impl.BaseServiceImpl;
import com.wangyu.web.dao.ResourceMapper;
import com.wangyu.web.domain.Resource;
import com.wangyu.web.service.ResourceService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资源服务层
 *
 * @author wangyu
 * @date 2019-12-13 15:47
 */
@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceService {

  /**
   * sl4j
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServiceImpl.class);

  @Autowired
  private ResourceMapper resourceMapper;

  @Override
  public List<Resource> findByUrl(String matchUrl) {
    Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
    params.put("url", matchUrl);
    return resourceMapper.findList(params);
  }
}