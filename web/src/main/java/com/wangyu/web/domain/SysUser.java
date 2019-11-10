package com.wangyu.web.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.core.entity.BaseEntity;

/**
 * 后台用户领域对象
 *
 * @author wangyu
 * @date 2019-11-05 21:23
 */
public class SysUser extends BaseEntity<SysUser> {
    private static final long serialVersionUID = -6707531024046264450L;

    /**
     * 用户基本信息ID
     */
    private Long baseUserId;

    /**
     * 用户头像
     */
    private String headImg;

    /**
     * 性别(0:女 1:男 2:未知)
     */
    private String gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private Long createDate;

    /**
     * 修改时间
     */
    private Long modifyDate;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 修改人
     */
    private Long modifier;

    /**
     * 删除标识（1：已删除 0：正常）
     */
    private Boolean delFlag;

    /**
     * 用户基础信息
     */
    private BaseUserInfo baseUserInfo;

    public Long getBaseUserId() {
        return baseUserId;
    }

    public void setBaseUserId(Long baseUserId) {
        this.baseUserId = baseUserId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH：mm：ss")
    public Long getCreateDate() {
        return createDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH：mm：ss")
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

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public BaseUserInfo getBaseUserInfo() {
        return baseUserInfo;
    }

    public void setBaseUserInfo(BaseUserInfo baseUserInfo) {
        this.baseUserInfo = baseUserInfo;
    }
}
