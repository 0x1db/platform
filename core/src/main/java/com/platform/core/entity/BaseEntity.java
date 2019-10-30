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

    /**
     * 创建时间
     */
    protected Long createDate;

    /**
     * 修改时间
     */
    protected Long modifyDate;

    /**
     * 创建人
     */
    protected Long creator;

    /**
     * 修改人
     */
    protected Long modifyer;

    /**
     * 删除标识 ：（0：删除 1：正常）
     */
    protected Integer delFlag;

    /**
     * 备注
     */
    protected String remark;

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

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getModifyer() {
        return modifyer;
    }

    public void setModifyer(Long modifyer) {
        this.modifyer = modifyer;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
