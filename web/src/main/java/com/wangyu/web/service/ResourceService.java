package com.wangyu.web.service;

import com.platform.core.service.BaseService;
import com.wangyu.web.domain.Resource;
import java.util.List;

/**
 * 资源服务层
 *
 * @author wangyu
 * @date 2019-12-13 15:47
 */
public interface ResourceService extends BaseService<Resource> {

  /**
   * 根据url 查询资源信息
   *
   * @param matchUrl
   * @return
   */
  List<Resource> findByUrl(String matchUrl);
}