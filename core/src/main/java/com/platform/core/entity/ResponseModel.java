package com.platform.core.entity;

/**
 *
 * @author wangyu
 * @date 2019/10/28 0:08
 */
public class ResponseModel {
    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 正常情况下返回的数据在这里进行记录和描述
     */
    private Object data;

    /**
     * 响应标记，正常情况下是200
     */
    private ResponseCode responseCode = ResponseCode.SUCCESS;

    /** 异常信息描述 */
    private String errorMsg;

    public ResponseModel() {
    }

    public ResponseModel(Long timestamp, Object data, ResponseCode responseCode, String errorMsg) {
        this.timestamp = timestamp;
        this.data = data;
        this.responseCode = responseCode;
        this.errorMsg = errorMsg;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
