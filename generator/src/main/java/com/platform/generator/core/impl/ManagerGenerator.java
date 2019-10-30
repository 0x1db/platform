package com.platform.generator.core.impl;

import com.platform.generator.core.context.CodeContext;
import com.platform.generator.core.context.GeneratorType;
import org.apache.velocity.VelocityContext;

/**
 * Manager层代码生成
 *
 * @author: wangyu
 * @date: 2019/10/26 22:56
 */
public class ManagerGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, CodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
    }

    @Override
    protected GeneratorType getPackageConfigType() {
        return GeneratorType.MANAGER;
    }

    @Override
    protected String getDescription() {
        return " Manager";
    }
}
