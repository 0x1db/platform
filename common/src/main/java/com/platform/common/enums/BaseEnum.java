package com.platform.common.enums;

/**
 * 枚举接口
 *
 * @author wangyu
 * @date 2019/11/2 15:59
 */
public interface BaseEnum<E extends Enum<?>, T> {
    /**
     * getValue
     *
     * @return
     */
    T getValue();

    /**
     * getDesc
     *
     * @return
     */
    String getDesc();
}
