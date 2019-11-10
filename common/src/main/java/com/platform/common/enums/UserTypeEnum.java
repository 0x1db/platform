package com.platform.common.enums;

/**
 * 用户类型枚举
 *
 * @author wangyu
 * @date 2019/11/2 15:58
 */
public enum UserTypeEnum implements BaseEnum<UserTypeEnum, String> {
    /**
     * 后台用户
     */
    MANAGE_USER("1", "后台用户"),
    /**
     * 前端用户
     */
    APP_USER("2", "前端用户");

    /**
     * value
     */
    private String value;
    /**
     * 描述
     */
    private String desc;

    UserTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
