package com.wangyu.web.domain;


import com.platform.core.entity.BaseEntity;

/**
 * 资源领域对象
 *
 * @author wangyu
 * @date 2019-12-13 15:47
 */
public class Resource extends BaseEntity<Resource> {

  private static final long serialVersionUID = -6294492660685303689L;

  /**
   * 主键
   */
  private Long id;

  /**
   * 名称
   */
  private String name;

  /**
   * 标识
   */
  private String flag;

  /**
   * url地址
   */
  private String url;

  /**
   * 请求方式
   */
  private String method;

  /**
   * 类别(1.菜单 2.按钮)
   */
  private String type;

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * 描述
   */
  private String description;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


}
