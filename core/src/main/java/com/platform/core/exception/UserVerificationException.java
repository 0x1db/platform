package com.platform.core.exception;

import com.platform.core.entity.ResponseCode;

/**
 * 用户验证异常
 *
 * @Author wangyu
 * @Date 2019-11-22 13:50
 */
public class UserVerificationException extends BusinessException {

  private static final long serialVersionUID = -6748320324761801180L;

  public UserVerificationException(String message,
      ResponseCode responseCode) {
    super(message, responseCode);
  }

  public UserVerificationException(String message) {
    super(message);
  }

  public UserVerificationException(ResponseCode responseCode) {
    super(responseCode);
  }
}
