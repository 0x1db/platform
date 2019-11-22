package com.platform.core.exception;

import com.platform.core.entity.ResponseCode;

/**
 * 参数类异常
 *
 * @Author wangyu
 * @Date 2019-11-22 13:35
 */
public class ParameterInvalidException extends BusinessException {

  private static final long serialVersionUID = 3344425353133882158L;

  public ParameterInvalidException(String message,
      ResponseCode responseCode) {
    super(message, responseCode);
  }

  public ParameterInvalidException(String message) {
    super(message);
  }

  public ParameterInvalidException(ResponseCode responseCode) {
    super(responseCode);
  }
}
