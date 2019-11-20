package com.wangyu.web.dto;

/**
 * 后台用户DTO对象
 *
 * @author wangyu
 * @date 2019/11/20 22:18
 */
public class SysUserDTO {
    /**
     * 后台用户id
     */
    private Long id;

    /**
     * 基础用户ID
     */
    private Long baseUserId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBaseUserId() {
        return baseUserId;
    }

    public void setBaseUserId(Long baseUserId) {
        this.baseUserId = baseUserId;
    }

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

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "SysUserDTO{" +
                "id=" + id +
                ", baseUserId=" + baseUserId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", headImg='" + headImg + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
