package com.wangyu.web.config.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.core.entity.ResponseCode;
import com.platform.core.entity.ResponseModel;
import com.platform.core.entity.ResponseUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * url拦截异常处理器
 *
 * @author wangyu
 * @date 2019/12/13 23:15
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException e) throws IOException, ServletException {
    ResponseModel error = ResponseUtil
        .error(ResponseCode.USER_FORBIDDEN_ACCESS, e.getMessage());
    ObjectMapper mapper = new ObjectMapper();
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    mapper.writeValue(response.getWriter(), error);
  }
}
