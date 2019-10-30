package com.wangyu.web.user.domain;

import java.util.Date;


import com.platform.core.entity.DataEntity;
import java.io.Serializable;

/** 
 * User领域对象
 * 
 * @author wangyu
 * @date 2019-10-31 00:23
 */
public class User extends BaseEntity<User> {
    private static final long serialVersionUID = -5764826072208512069L;

    /** 
	 * 
	 */
	private Long id;

    /** 
	 * 
	 */
	private String userName;

    /** 
	 * 
	 */
	private String password;

    /** 
	 * 
	 */
	private String trueName;

    /** 
	 * 
	 */
	private Long creator;

    /** 
	 * 
	 */
	private Long modifier;

    /** 
	 * 
	 */
	private Date createDate;

    /** 
	 * 
	 */
	private Date modifyDate;

    /** 
	 * 
	 */
	private Integer delFlag;

    public Long getId() {
		return id;
	}

    public void setId(Long id) {
		this.id = id;
	}

    public String getUserName() {
		return userName;
	}

    public void setUserName(String userName) {
		this.userName = userName;
	}

    public String getPassword() {
		return password;
	}

    public void setPassword(String password) {
		this.password = password;
	}

    public String getTrueName() {
		return trueName;
	}

    public void setTrueName(String trueName) {
		this.trueName = trueName;
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

    public Date getCreateDate() {
		return createDate;
	}

    public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    public Date getModifyDate() {
		return modifyDate;
	}

    public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

    public Integer getDelFlag() {
		return delFlag;
	}

    public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}


}
