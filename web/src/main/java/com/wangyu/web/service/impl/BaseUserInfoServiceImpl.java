package com.wangyu.web.service.impl;

import com.platform.core.entity.ResponseCode;
import com.platform.core.exception.ParameterInvalidException;
import com.platform.core.service.impl.BaseServiceImpl;
import com.wangyu.web.dao.BaseUserInfoMapper;
import com.wangyu.web.domain.BaseUserInfo;
import com.wangyu.web.service.BaseUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户基本信息服务层
 *
 * @author wangyu
 * @date 2019-11-02 17:36
 */
@Service
public class BaseUserInfoServiceImpl extends BaseServiceImpl<BaseUserInfo> implements BaseUserInfoService {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseUserInfoServiceImpl.class);

    @Autowired
    private BaseUserInfoMapper baseUserInfoMapper;

  @Override
  public BaseUserInfo findByUsername(String username) {
    if (StringUtils.isBlank(username)) {
      throw new ParameterInvalidException(ResponseCode.PARAM_IS_BLANK);
    }

    return baseUserInfoMapper.findByUsername(username);
  }
}