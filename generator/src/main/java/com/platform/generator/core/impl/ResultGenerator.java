package com.platform.generator.core.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.platform.generator.config.CodeConfigType;
import com.platform.generator.core.connect.Connector;
import com.platform.generator.core.context.CodeContext;
import com.platform.generator.core.context.GeneratorType;
import com.platform.generator.core.utils.PropertiesUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ResultGenerator
 *
 * @author: wangyu
 * @date: 2019/10/26 22:56
 */
public class ResultGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, CodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
        velocityContext.put(CodeConfigType.SERIAL_VERSION_UID.getDesc(), String.valueOf(UUID.randomUUID().getLeastSignificantBits()));

        String tableName = cxt.getTableName();
        Connector connector = (Connector) cxt.getAttribute(CodeConfigType.JDBC_CONNECTOR);
        Map<String, String> columnNameTypeMap = connector.getColumnNameType(tableName);
        Map<String, String> columnRemarkMap = connector.getColumnRemark(tableName);

        List<String> allUpCaseCols = Lists.newArrayList();
        Map<String, String> remarkMap = Maps.newHashMap();
        for (String col : columnNameTypeMap.keySet()) {
            String upCaseCol = StringUtils.upperCase(col);
            allUpCaseCols.add(upCaseCol);
            remarkMap.put(upCaseCol, columnRemarkMap.get(col));
        }

        String noPrefixTableName = StringUtils.upperCase(tableName.toLowerCase().replaceFirst(PropertiesUtils.getTablePrefix(cxt.getProperties()), ""));
        velocityContext.put(CodeConfigType.NO_PREFIX_TABLE_NAME.getDesc(), noPrefixTableName);
        velocityContext.put(CodeConfigType.ALL_UP_CASE_COLS.getDesc(), allUpCaseCols);
        velocityContext.put(CodeConfigType.REMARK_MAP.getDesc(), remarkMap);
    }

    @Override
    protected GeneratorType getPackageConfigType() {
        return GeneratorType.result;
    }

    @Override
    protected String getDescription() {
        return "结果Result";
    }
}
