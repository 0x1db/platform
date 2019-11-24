package com.wangyu.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.core.entity.ResponseCode;
import com.platform.core.entity.ResponseModel;
import com.platform.core.entity.ResponseUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 在ExceptionTranslationFilter中使用到AuthenticationEntryPoint，当ExceptionTranslationFilter截获AuthenticationException
 * 或者AccessDeniedException异常时，就会调用AuthenticationEntryPoint的commence
 *
 * @author wangyu
 * @date 2019/11/24 14:18
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e)
      throws IOException, ServletException {
    //统一异常处理返回
    ResponseModel responseModel = ResponseUtil.error(ResponseCode.NOT_AUTHORIZED, "未授权");

    ObjectMapper mapper = new ObjectMapper();
    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    mapper.writeValue(httpServletResponse.getWriter(), responseModel);
  }
}
