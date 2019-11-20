package com.platform.core.entity;

/**
 * @author wangyu
 * @date 2019/11/19 22:52
 */
public class ResponseUtil {

    /**
     * 只返回成功状态，不返回任何信息
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseModel success() {
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
    public static <T> ResponseModel success(T entity) {
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
    public static <T> ResponseModel error(ResponseCode responseCode, String message) {
        ResponseModel result = new ResponseModel(System.currentTimeMillis(), null, responseCode.getCode(),
                message);
        return result;
    }
}
