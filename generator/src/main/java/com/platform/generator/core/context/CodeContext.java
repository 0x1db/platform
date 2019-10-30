package com.platform.generator.core.context;

import com.google.common.collect.Maps;
import com.platform.generator.config.CodeConfigType;
import com.platform.generator.core.utils.GeneratorStringUtils;
import com.platform.generator.core.utils.PropertiesUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

/**
 * Velocity渲染模板配置上下文信息
 *
 * @author: wangyu
 * @date: 2019/10/26 22:56
 */
public class CodeContext implements Serializable {
    private static final long serialVersionUID = -8244453504134436716L;

    /**
     * 配置文件配置
     */
    private Properties properties;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 类的名称
     */
    private String upClassName;

    /**
     * 按照JAVA规范，类对应的变量小写
     */
    private String lowClassName;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 主键类型
     */
    private String primaryKeyType;

    /**
     * 主键
     */
    private String primaryKey;

    /**
     * 上下文参数
     */
    private Map<CodeConfigType, Object> attributes = Maps.newHashMap();

    /**
     * 构造生成代码上下文
     *
     * @param tableName  表名称
     * @param propMap    JDBC数据map
     * @param properties 配置
     */
    public CodeContext(String tableName, Map<String, String> propMap, Properties properties) {
        this.tableName = tableName;
        this.upClassName = GeneratorStringUtils.firstUpperAndNoPrefix(tableName, properties);
        this.lowClassName = GeneratorStringUtils.formatAndNoPrefix(tableName, properties);
        this.packageName = PropertiesUtils.getPackage(properties);
        this.primaryKeyType = propMap.get(CodeConfigType.PRIMARY_KEY_TYPE.getType());
        this.primaryKey = GeneratorStringUtils.firstUpperNoFormat(GeneratorStringUtils.format(propMap.get(CodeConfigType.PRIMARY_KEY.getType())));
        this.properties = properties;
    }

    public Object getAttribute(CodeConfigType key) {
        if (key == null) {
            return null;
        }
        return this.attributes.get(key);
    }

    public void addAttribute(CodeConfigType key, Object value) {
        if (key == null) {
            return;
        }
        this.attributes.put(key, value);
    }

    public Properties getProperties() {
        return properties;
    }

    public String getTableName() {
        return tableName;
    }

    public String getUpClassName() {
        return upClassName;
    }

    public String getLowClassName() {
        return lowClassName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getPrimaryKeyType() {
        return primaryKeyType;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

}
