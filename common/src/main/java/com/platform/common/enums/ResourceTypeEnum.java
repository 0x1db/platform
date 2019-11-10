package com.platform.common.enums;

/**
 * @author wangyu
 * @date 2019/11/2 16:45
 */
public enum ResourceTypeEnum implements BaseEnum<ResourceTypeEnum, String> {
    /**
     * 菜单
     */
    MENU("1", "菜单"),

    /**
     * 按钮
     */
    BUTTON("2", "按钮");

    private String value;
    private String desc;

    ResourceTypeEnum(String value, String desc) {
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
