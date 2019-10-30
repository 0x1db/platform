package com.platform.generator.core;


import com.platform.generator.core.context.CodeContext;
import com.platform.generator.core.context.GeneratorType;

/**
 * 读取配置自动化生成代码接口
 *
 * @Author wangyu
 * @Date 2019/10/26 22:49
 */
public interface Generator {

    /**
     * 读取配置生成文件
     *
     * @param context
     * @param configType
     */
    void defaultGenerator(CodeContext context, GeneratorType configType);

    /**
     * 插件配置生成文件
     *
     * @param context
     * @param configType
     */
    void pluginGenerator(CodeContext context, GeneratorType configType);
}
