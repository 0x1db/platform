package com.platform.core.entity;

import java.io.Serializable;

/**
 * Entity 基类
 *
 * @author wangyu
 * @date 2019/10/27 0:36
 */
public class BaseEntity<T> implements Serializable {


    private static final long serialVersionUID = -8901476519900616574L;

    /**
     * 实体编号（唯一标识）
     */
    protected Long id;

    public BaseEntity() {

    }

    public BaseEntity(Long id) {
        this();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
