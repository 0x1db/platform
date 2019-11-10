package com.platform.core.web.controller;

import com.platform.core.entity.ResponseCode;
import com.platform.core.entity.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 控制层基类
 *
 * @author wangyu
 * @date 2019/10/29 23:08
 */
public class BaseController {
    /**
     * 日志
     */
    public final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * 只返回成功状态，不返回任何信息
     *
     * @param <T>
     * @return
     */
    protected <T> ResponseModel buildSuccessResult() {
        ResponseModel result = new ResponseModel(System.currentTimeMillis(), null, ResponseCode.SUCCESS.getCode(), null);
        return result;
    }

    /**
     * 返回成功状态，并返回消息内容
     *
     * @param entity
     * @param <T>
     * @return
     */
    protected <T> ResponseModel buildSuccessResult(T entity) {
        ResponseModel result = new ResponseModel(System.currentTimeMillis(), null, ResponseCode.SUCCESS.getCode(), null);
        if (entity == null) {
            return result;
        }
        result.setData(entity);
        return result;
    }

    /**
     * 返回失败状态，并返回异常消息
     *
     * @param <T>
     * @return
     */
    protected <T> ResponseModel buildErrorReslut(ResponseCode responseCode, String message) {
        ResponseModel result = new ResponseModel(System.currentTimeMillis(), null, responseCode.getCode(),
                message);
        return result;
    }
}
