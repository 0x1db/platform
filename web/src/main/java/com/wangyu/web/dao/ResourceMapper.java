package com.wangyu.web.dao;

import com.platform.core.persistence.BaseMapper;
import com.wangyu.web.domain.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 资源 持久层Mapper
 *
 * @author wangyu
 * @date 2019-12-13 15:47
 */
@Mapper
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {


}
