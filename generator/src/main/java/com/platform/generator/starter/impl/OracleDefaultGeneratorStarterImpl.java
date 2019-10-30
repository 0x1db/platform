package com.platform.generator.starter.impl;

import com.platform.generator.config.GeneratorConfig;
import com.platform.generator.config.GeneratorConfigFactory;
import com.platform.generator.core.connect.OracleConnector;
import com.platform.generator.core.context.GeneratorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 功能描述：oracle默认实现
 *
 * @author: wangyu
 * @date: 2019/10/26 21:56
 */
public class OracleDefaultGeneratorStarterImpl extends MysqlDefaultGeneratorStarterImpl {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OracleDefaultGeneratorStarterImpl.class);

    /**
     * 重新初始化上线文信息
     */
    @Override
    public void buildConnector() {
        GeneratorConfig generatorConfigurer = GeneratorConfigFactory.getGeneratorConfig();
        properties = generatorConfigurer.getProperties();
        generatorConfigurer.initConfigParams();
        context = new ClassPathXmlApplicationContext(GeneratorConfig.SPRING_CONFIG);
        setConnector(new OracleConnector(properties));
        setProperties(properties);
        setContext(context);
    }

    @Override
    protected boolean isLoop(GeneratorType configType) {
        return configType != GeneratorType.MAPPER;
    }
}
