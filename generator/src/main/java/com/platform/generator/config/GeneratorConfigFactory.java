package com.platform.generator.config;


/**
 * 获取配置类工厂
 *
 * @author: wangyu
 * @date: 2019/10/26 22:20
 */
public class GeneratorConfigFactory {

    /**
     * 自动化生成代码配置器
     */
    private static GeneratorConfig generatorConfig;

    /**
     * 获取配置
     *
     * @return
     */
    public synchronized static GeneratorConfig getGeneratorConfig() {
        if (null == generatorConfig) {
            generatorConfig = new DefaultGeneratorConfig();
        }
        return generatorConfig;
    }

}
