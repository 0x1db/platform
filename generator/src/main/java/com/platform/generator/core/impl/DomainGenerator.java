package com.platform.generator.core.impl;

import com.platform.generator.config.CodeConfigType;
import com.platform.generator.core.connect.Connector;
import com.platform.generator.core.context.CodeContext;
import com.platform.generator.core.context.GeneratorType;
import org.apache.velocity.VelocityContext;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Domain代码生成器
 *
 * @author: wangyu
 * @date: 2019/10/26 22:56
 */
public class DomainGenerator extends ServiceGenerator {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, CodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
        velocityContext.put("SerialVersionUID", String.valueOf(UUID.randomUUID().getLeastSignificantBits()));

        String tableName = cxt.getTableName();
        Connector connector = (Connector) cxt.getAttribute(CodeConfigType.JDBC_CONNECTOR);

        Map<String, String> colMap = connector.getColumnNameType(tableName);
        Map<String, String> columnRemarkMap = connector.getColumnRemark(tableName);

        Set<String> keySet = colMap.keySet();
        Set<String> importSets = new HashSet<String>();

        for (String key : keySet) {
            String value = colMap.get(key);
            if ("BigDecimal".equals(value) && !importSets.contains("BigDecimal")) {
                importSets.add("import java.math.BigDecimal;\n");
            } else if ("Date".equals(value) && !importSets.contains("Date")) {
                importSets.add("import java.util.Date;\n");
            } else if ("Timestamp".equals(value) && !importSets.contains("Timestamp")) {
                importSets.add("import java.sql.Timestamp;\n");
            }
        }

        velocityContext.put("methods", generateGetAndSetMethods(colMap, cxt));
        velocityContext.put("fields", generateFields(colMap, columnRemarkMap));
        velocityContext.put("importSets", importSets);
    }


    @Override
    protected GeneratorType getPackageConfigType() {
        return GeneratorType.DOMAIN;
    }

    @Override
    protected String getDescription() {
        return "领域对象";
    }
}
