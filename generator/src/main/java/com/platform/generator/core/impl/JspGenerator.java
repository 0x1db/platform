package com.platform.generator.core.impl;

import com.google.common.collect.Lists;
import com.platform.generator.config.CodeConfigType;
import com.platform.generator.core.connect.Connector;
import com.platform.generator.core.context.CodeContext;
import com.platform.generator.core.context.GeneratorType;
import com.platform.generator.core.utils.GeneratorStringUtils;
import org.apache.velocity.VelocityContext;

import java.util.List;
import java.util.Map;

/**
 * JSP生成器
 *
 * @author: wangyu
 * @date: 2019/10/26 22:56
 */
public class JspGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, CodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);

        // 获取所有的属性
        String tableName = cxt.getTableName();
        Connector connector = (Connector) cxt.getAttribute(CodeConfigType.JDBC_CONNECTOR);
        Map<String, String> columnNameTypeMap = connector.getColumnNameType(tableName);

        List<String> fields= Lists.newArrayList();
        for (String col : columnNameTypeMap.keySet()) {
            String field = GeneratorStringUtils.format(col);
            fields.add(field);
        }
        velocityContext.put(CodeConfigType.FIELDS.getDesc(), fields);
    }

    @Override
    protected GeneratorType getPackageConfigType() {
        return GeneratorType.JSP;
    }

    @Override
    protected String getDescription() {
        return "";
    }
}
