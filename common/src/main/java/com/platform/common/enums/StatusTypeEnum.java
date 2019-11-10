package com.platform.common.enums;

/**
 * 状态枚举类型
 *
 * @author wangyu
 * @date 2019/11/2 16:29
 */
public enum StatusTypeEnum implements BaseEnum<StatusTypeEnum, String> {
    /**
     * 禁用
     */
    FORBIDDEN("0", "禁用"),
    /**
     * 正常
     */
    NORMAL("1", "正常");

    private String value;
    private String desc;

    StatusTypeEnum(String value, String desc) {
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
