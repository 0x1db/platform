package com.platform.core.exception;

import com.platform.core.entity.ResponseCode;

/**
 * 业务异常基类
 *
 * @Author wangyu
 * @Date 2019-11-22 11:39
 */
public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = -6106495517784451173L;

  /**
   * 异常代码
   */
  private ResponseCode responseCode;

  /**
   * 异常描述
   */
  private String message;


  public BusinessException(String message, ResponseCode responseCode) {
    super(message);
    this.responseCode = responseCode;
    this.message = message;
  }

  public BusinessException(String message) {
    super(message);
    this.message = message;
    this.responseCode = ResponseCode.UNSPECIFIED;
  }

  public BusinessException(ResponseCode responseCode) {
    this.message = responseCode.getDesc();
    this.responseCode = responseCode;
  }

  public ResponseCode getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(ResponseCode responseCode) {
    this.responseCode = responseCode;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * 重写堆栈填充，不填充错误堆栈信息，提高性能
   */
  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
