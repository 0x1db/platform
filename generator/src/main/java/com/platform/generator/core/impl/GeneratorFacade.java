package com.platform.generator.core.impl;

import com.google.common.collect.Maps;
import com.platform.generator.core.Generator;
import com.platform.generator.core.context.CodeContext;
import com.platform.generator.core.context.GeneratorType;
import org.apache.commons.collections.MapUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * 分发生成代码适配器
 *
 * @author: wangyu
 * @date: 2019/10/26 22:56
 */
public class GeneratorFacade implements Generator {

    private Map<GeneratorType, Generator> generatorMap = Maps.newHashMap();

    @Override
    public void defaultGenerator(CodeContext context, GeneratorType configType) {
        getGenerator(configType).defaultGenerator(context, configType);
    }

    @Override
    public void pluginGenerator(CodeContext context, GeneratorType configType) {
        getGenerator(configType).pluginGenerator(context, configType);
    }

    public void setGeneratorMap(Map<String, Generator> map) {
        if (MapUtils.isNotEmpty(map)) {
            Iterator<Map.Entry<String, Generator>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Generator> entry = iterator.next();
                String key = entry.getKey();
                GeneratorType configType = GeneratorType.getByType(key);
                if (null == configType) {
                    throw new RuntimeException("根据key找不到生成代码的Generator，key = " + key);
                }
                this.generatorMap.put(configType, entry.getValue());
            }
        }
    }

    public Generator getGenerator(GeneratorType configType) {
        return generatorMap.get(configType);
    }
}
