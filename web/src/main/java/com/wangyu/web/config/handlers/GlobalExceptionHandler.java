package com.wangyu.web.config.handlers;

import com.platform.core.entity.ResponseCode;
import com.platform.core.entity.ResponseModel;
import com.platform.core.entity.ResponseUtil;
import com.platform.core.exception.ParameterInvalidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @Author wangyu
 * @Date 2019-11-22 14:20
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * slf4j
   */
  private Logger LOG = LoggerFactory.getLogger(this.getClass());

  /**
   * 参数校验异常
   *
   * @param pe
   * @return
   */
  @ExceptionHandler(value = ParameterInvalidException.class)
  public ResponseModel ParameterInvalidException(ParameterInvalidException pe) {
    LOG.error(pe.getMessage(), pe);
    return ResponseUtil.error(pe.getResponseCode(), pe.getMessage());
  }

  /**
   * 请求参数异常（hibernate-validate）
   *
   * @param be
   * @return
   */
  @ExceptionHandler(value = BindException.class)
  public ResponseModel ParameterInvalidException(BindException be) {
    LOG.error(be.getMessage(), be);
    String errorMessage = be.getFieldError().getDefaultMessage();
    LOG.error(errorMessage);
    return ResponseUtil.error(ResponseCode.BAD_REQUEST, be.getMessage());
  }

  /**
   * 对方法参数校验异常处理方法(前端提交的方式为json格式出现异常时会被该异常类处理) json格式提交时，spring会采用json数据的数据转换器进行处理（进行参数校验时错误是抛出MethodArgumentNotValidException异常）
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseModel handlerArgumentNotValidException(MethodArgumentNotValidException me) {
    LOG.error(me.getMessage(), me);
    return ResponseUtil.error(ResponseCode.BAD_REQUEST, me.getMessage());
  }
}
