package com.platform.generator.config;

import java.util.Properties;

/**
 *  自动化生成配置接口
 *
 * @author: wangyu
 * @date: 2019/10/26 22:56
 */
public interface GeneratorConfig {

    /**
     * 代码生成包
     */
    String GENERATOR_PACKAGE = "com.zhiyuan";

    /**
     * 生成代码存放位置
     */
    String GENERATOR_LOCATION = "code";

    /**
     * 所属系统项目
     */
    String GENERATOR_PROJECT_NAME = "";

    /**
     * 过滤表名前缀
     */
    String GENERATOR_TABLEPREFIX = "";

    /**
     * 强类型转换，数据库float转换为BigDecimal，不配置转换为Double
     */
    String GENERATOR_PRECISION = "";

    /**
     * domain后缀名称
     */
    String GENERATOR_DOMAIN = "";

    /**
     * 默认配置文件名称
     */
    String LOCAL_GENERATOR_PATH = "generator.properties";

    /**
     * project下配置的内容
     */
    String CONFIG_GENERATOR_PATH = "config-generator.properties";

    /**
     * Spring配置文件
     */
    String SPRING_CONFIG = "spring-generator.xml";

    /**
     * JAVA文件生成的所在包文件
     */
    String JAVA_SRC = "java";

    /**
     * oracle schema
     */
    String ORACLE_SCHEMA = "APPS";

    /**
     * 获取配置文件配置集
     *
     * @return 配置文件配置集
     */
    Properties getProperties();

    /**
     * 初始化默认参数
     */
    void initConfigParams();

}
