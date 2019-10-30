package com.platform.generator.starter.impl;

import com.platform.generator.core.Generator;
import com.platform.generator.core.context.CodeContext;
import com.platform.generator.core.context.GeneratorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自动化生成代码插件执行抽象实现
 *
 * @author: wangyu
 * @date: 2019/10/26 12:56
 */
public class MySqlPluginGeneratorStarterImpl extends MysqlDefaultGeneratorStarterImpl {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MySqlPluginGeneratorStarterImpl.class);

    @Override
    protected void executeGenerator(Generator generator, CodeContext generatorContext, GeneratorType configType) {
        generator.pluginGenerator(generatorContext, configType);
    }
}
