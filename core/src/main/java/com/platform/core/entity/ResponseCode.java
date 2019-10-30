package com.platform.core.entity;

/**
 * 响应编码，记录了所有执行者向调用者返回的处理结果编码
 *
 * @author wangyu
 * @date 2019/10/28 23:02
 */
public enum ResponseCode {
    /**
     * 200表示成功
     */
    SUCCESS(200, "成功"),
    /**
     * 参数错误
     * 1001-1999
     */
    PARAM_IS_INVALID(1002, "参数无效"),
    PARAM_IS_BLANK(1001, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /**
     * 用户错误
     * 2001-2999
     */
    USER_NOT_LOGIN(2001, "用户未登录，访问路径需要登录验证验证"),
    USER_LOGIN_ERROR(2002, "账号或密码错误"),
    USER_ACCOUNT_FORBIDDEN(2003, "账号被禁用"),
    USER_NOT_EXIST(2004, "用户不存在"),
    USER_HAS_EXISTED(2005, "用户已存在");

    private Integer code;
    private String desc;

    ResponseCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
