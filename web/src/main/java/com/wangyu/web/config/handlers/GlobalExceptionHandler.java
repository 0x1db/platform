package com.wangyu.web.config.handlers;

import com.platform.core.entity.ResponseCode;
import com.platform.core.entity.ResponseModel;
import com.platform.core.entity.ResponseUtil;
import com.platform.core.exception.ParameterInvalidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

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
  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * 参数校验异常
   */
  @ExceptionHandler(value = ParameterInvalidException.class)
  public ResponseModel parameterInvalidException(ParameterInvalidException pe) {
    LOG.error(pe.getMessage(), pe);
    pe.printStackTrace();
    return ResponseUtil.error(pe.getResponseCode(), pe.getMessage());
  }

  /**
   * 请求参数校验异常（hibernate-validate）
   */
  @ExceptionHandler(value = BindException.class)
  public ResponseModel parameterInvalidException(BindException be) {
    LOG.error(be.getMessage(), be);
    String errorMessage = be.getFieldError().getDefaultMessage();
    be.printStackTrace();
    return ResponseUtil.error(ResponseCode.NOT_SUPPORT_METHOD, errorMessage);
  }

  /**
   * 对方法参数校验异常处理方法(前端提交的方式为json格式出现异常时会被该异常类处理) json格式提交时，spring会采用json数据的数据转换器进行处理（进行参数校验时错误是抛出MethodArgumentNotValidException异常）
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseModel handlerArgumentNotValidException(MethodArgumentNotValidException me) {
    LOG.error(me.getMessage(), me);
    me.printStackTrace();
    return ResponseUtil.error(ResponseCode.NOT_SUPPORT_METHOD, me.getMessage());
  }

  /**
   * 拦截405请求 对于方法调用错误或者资源路径错误进行统一处理
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseModel httpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException hme) {
    LOG.error(hme.getMessage(), hme);
    hme.printStackTrace();
    return ResponseUtil
        .error(ResponseCode.BAD_REQUEST, "Method " + hme.getMethod() + " is not supported");
  }

  /**
   * 拦截404请求 对于请求资源不存在的请求返回异常信息统一处理 TODO 暂时无效
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseModel notFoundPage404(NoHandlerFoundException ne) {
    LOG.error(ne.getMessage(), ne);
    ne.printStackTrace();
    return ResponseUtil.error(ResponseCode.NOT_FOUND, "No access resource found");
  }

  /**
   * 400错误，bad request
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseModel badRequestException(IllegalArgumentException ie) {
    LOG.error(ie.getMessage(), ie);
    ie.printStackTrace();
    return ResponseUtil.error(ResponseCode.BAD_REQUEST, "bad request");
  }

  /**
   * 拦截参数不匹配异常 400 getPropertyName()获取数据类型不匹配参数名称  getRequiredType()实际要求客户端传递的数据类型
   */
  @ExceptionHandler(value = TypeMismatchException.class)
  public ResponseModel requestTypeMismatch(TypeMismatchException ex) {
    //拼接详细错误信息
    StringBuilder sb = new StringBuilder();
    sb.append("参数类型不匹配,参数：").append(ex.getPropertyName());
    sb.append("类型应该为：" + ex.getRequiredType());
    LOG.error(ex.getMessage(), ex);
    ex.printStackTrace();
    return ResponseUtil.error(ResponseCode.BAD_REQUEST, sb.toString());
  }

  /**
   * 缺少参数异常 getParameterName() 缺少的参数名称 400
   */
  @ExceptionHandler(value = MissingServletRequestParameterException.class)
  public ResponseModel requestMissingServletRequest(MissingServletRequestParameterException ex) {
    LOG.error(ex.getMessage(), ex);
    ex.printStackTrace();
    return ResponseUtil.error(ResponseCode.BAD_REQUEST, "缺少必要参数,参数名称为：" + ex.getParameterName());
  }
}
