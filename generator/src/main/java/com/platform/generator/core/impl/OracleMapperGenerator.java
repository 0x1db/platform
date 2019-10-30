package com.platform.generator.core.impl;

import com.platform.generator.config.CodeConfigType;
import com.platform.generator.core.context.CodeContext;
import com.platform.generator.core.context.GeneratorType;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OracleMapperGenerator
 *
 * @author: wangyu
 * @date: 2019/10/26 22:56
 */
public class OracleMapperGenerator extends MapperGenerator {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OracleMapperGenerator.class);

    @Override
    public void initVelocityContext(VelocityContext velocityContext, CodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
        velocityContext.put(CodeConfigType.ORACLE_SCHEMA.getDesc(), cxt.getAttribute(CodeConfigType.ORACLE_SCHEMA));
    }

    @Override
    protected GeneratorType getPackageConfigType() {
        return GeneratorType.ORACLE_MAPPER;
    }
}
