package com.wangyu.web.domain;


import com.platform.common.enums.StatusTypeEnum;
import com.platform.common.enums.UserTypeEnum;
import com.platform.core.entity.BaseEntity;

/**
 * 用户基本信息领域对象
 *
 * @author wangyu
 * @date 2019-11-02 17:04
 */
public class BaseUserInfo extends BaseEntity<BaseUserInfo> {
    private static final long serialVersionUID = -8684228746823690629L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态(0:禁用 1：正常)
     */
    private StatusTypeEnum status;

    /**
     * 用户类型(1.后台用户 2.前端用户)
     */
    private UserTypeEnum type;

    /**
     * 备注
     */
    private String remark;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StatusTypeEnum getStatus() {
        return status;
    }

    public void setStatus(StatusTypeEnum status) {
        this.status = status;
    }

    public UserTypeEnum getType() {
        return type;
    }

    public void setType(UserTypeEnum type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
