package com.platform.generator.core.impl;

import com.platform.generator.config.CodeConfigType;
import com.platform.generator.core.context.CodeContext;
import com.platform.generator.core.context.GeneratorType;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

/**
 * Controller生成器
 *
 * @author: wangyu
 * @date: 2019/10/26 22:56
 */
public class ControllerGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, CodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
        String description = (String) velocityContext.get("classTitle");
        String replace = StringUtils.replace(description, "{classDescription}",
                velocityContext.get(CodeConfigType.UP_CLASS_NAME.getDesc()) + "控制器");
        velocityContext.put(CodeConfigType.CLASS_TITLE.getDesc(), replace);
    }

    @Override
    protected GeneratorType getPackageConfigType() {
        return GeneratorType.CONTROLLER;
    }

    @Override
    protected String getDescription() {
        return "控制器";
    }

}
