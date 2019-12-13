package com.wangyu.web.domain;


import com.platform.core.entity.BaseEntity;
import javax.validation.constraints.NotBlank;

/**
 * 角色信息领域对象
 *
 * @author wangyu
 * @date 2019-11-05 00:45
 */
public class Role extends BaseEntity<Role> {
    private static final long serialVersionUID = -5294490544520473867L;

    /**
     * 名称
     */
    @NotBlank(message = "角色名不能为空")
    private String name;

    /**
     * 标识
     */
    @NotBlank(message = "角色表示不能为空")
    private String flag;

    /**
     * 描述
     */
    private String description;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
