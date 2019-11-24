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

  public ParameterInvalidException(ResponseCode responseCode, String message) {
    super(message, responseCode);
  }

  public ParameterInvalidException(String message) {
    super(message);
  }

  public ParameterInvalidException(ResponseCode responseCode) {
    super(responseCode);
  }

  /**
   * 重写堆栈填充，不填充错误堆栈信息，提高性能
   */
  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
