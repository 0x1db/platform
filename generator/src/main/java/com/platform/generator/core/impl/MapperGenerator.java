package com.platform.generator.core.impl;

import com.google.common.collect.Lists;
import com.platform.generator.config.CodeConfigType;
import com.platform.generator.core.connect.Connector;
import com.platform.generator.core.context.CodeContext;
import com.platform.generator.core.context.GeneratorType;
import com.platform.generator.core.utils.GeneratorStringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import java.util.List;
import java.util.Map;

/**
 * Mapper.xml代码生成
 *
 * @author: wangyu
 * @date: 2019/10/26 22:56
 */
public class MapperGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, CodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);

        String tableName = cxt.getTableName();
        Connector connector = (Connector) cxt.getAttribute(CodeConfigType.JDBC_CONNECTOR);
        Map<String, String> columnNameTypeMap = connector.getColumnNameType(tableName);
        List<String> allIndexs = connector.getAllIndex(tableName);
        List<String> autoIncrementCols = connector.getAutoIncrementCol(tableName);

        List<String> resultMapColumns = Lists.newArrayList();
        List<String> whereConditions = Lists.newArrayList();
        List<String> columns = Lists.newArrayList();
        List<String> insertValueConditions = Lists.newArrayList();
        List<String> insertColsConditions = Lists.newArrayList();
        List<String> updateConditions = Lists.newArrayList();

        final String pk = (String) velocityContext.get(CodeConfigType.PRIMARY_KEY.getDesc());
        for (String col : columnNameTypeMap.keySet()) {
            String field = GeneratorStringUtils.format(col);
            columns.add(tableName + "." + col);
            StringBuilder cloumBf = new StringBuilder();
            cloumBf.append("<result property=\"").append(field).append("\" column=\"").append(col).append("\"/>");
            resultMapColumns.add(cloumBf.toString());

            // 对应主键自增,不生成代码
            boolean autoIdKey = false;
            if (StringUtils.equals(col, pk) && CollectionUtils.isNotEmpty(autoIncrementCols)) {
                autoIdKey = CollectionUtils.exists(autoIncrementCols, new Predicate() {
                    @Override
                    public boolean evaluate(Object o) {
                        return StringUtils.equals((String) o, pk);
                    }
                });
            }

            String defaultFieldStr = "<if test=\"" + field + "!=null and ''!=" + field + "\">\n";
            String defaultFieldStrs = "<if test=\"" + field + "s" + "!=null and ''!=" + field + "s" + "\">\n";
            if (("Date").equals(columnNameTypeMap.get(col))) {
                StringBuilder conditionBfs = new StringBuilder();
                conditionBfs.append(defaultFieldStr)
                        .append("\t\t\t\tAND ").append(tableName).append(".").append(col).append(" &gt;= #{").append(CodeConfigType.DYNAMIC_FILEDS.getDesc()).append(field).append("}\n")
                        .append("\t\t\t</if>");
                whereConditions.add(conditionBfs.toString());
                StringBuilder conditionBfe = new StringBuilder();
                conditionBfe.append(defaultFieldStr)
                        .append("\t\t\t\tAND ").append(tableName).append(".").append(col).append(" &lt; #{").append(CodeConfigType.DYNAMIC_FILEDS.getDesc()).append(field).append("}\n")
                        .append("\t\t\t</if>");
                whereConditions.add(conditionBfe.toString());
            } else {
                // 非String类型的,使用null
                String colShowType = columnNameTypeMap.get(col);
                if (!StringUtils.equals(colShowType, "String")) {
                    defaultFieldStr = "<if test=\"" + field + "!=null\">\n";
                }
                StringBuilder conditionBf = new StringBuilder();
                conditionBf.append(defaultFieldStr)
                        .append("\t\t\t\tAND ").append(tableName).append(".").append(col).append(" = #{").append(field).append("}\n")
                        .append("\t\t\t</if>");
                whereConditions.add(conditionBf.toString());


                boolean isKey = false;
                if (!CollectionUtils.isEmpty(allIndexs)) {
                    for (String allIndex : allIndexs) {
                        if (StringUtils.contains(allIndex, col)) {
                            isKey = true;
                            break;
                        }
                    }
                }

                if (!StringUtils.equals(colShowType, "String")) {
                    defaultFieldStrs = "<if test=\"" + field + "s" + "!=null\">\n";
                }
                if ((StringUtils.equals(field, pk) || isKey)) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(defaultFieldStrs)
                            .append("\t\t\t\tAND ").append(tableName).append(".").append(col).append(" IN\n")
                            .append("\t\t\t\t<foreach collection=\"").append(field).append("s\" item=\"").append(field).append("\" open=\"(\" close=\")\" separator=\",\">\n")
                            .append("\t\t\t\t\t").append("#{").append(field).append("}\n")
                            .append("\t\t\t\t</foreach>\n")
                            .append("\t\t\t</if>");
                    whereConditions.add(builder.toString());
                }
            }

            if (!autoIdKey) {
                if (col.startsWith("gmt") || StringUtils.equals(field, "created") || StringUtils.equals(field, "modified")) {
                    insertValueConditions.add("UNIX_TIMESTAMP(NOW()),");
                    insertColsConditions.add(col + ", ");
                } else {
                    StringBuilder conditionValueBf = new StringBuilder();
                    conditionValueBf.append(defaultFieldStr)
                            .append("\t\t\t\t").append("#{").append(field).append("},\n")
                            .append("\t\t\t</if>");
                    insertValueConditions.add(conditionValueBf.toString());

                    StringBuilder conditionColBf = new StringBuilder();
                    conditionColBf.append(defaultFieldStr)
                            .append("\t\t\t\t").append(col).append(",\n")
                            .append("\t\t\t</if>");
                    insertColsConditions.add(conditionColBf.toString());
                }
            }

            String[] updateFilters = {pk, "created"};
            if (!ArrayUtils.contains(updateFilters, field)) {
                StringBuilder upBf = new StringBuilder();
                if (StringUtils.equals(field, "modified")) {
                    upBf.append(tableName).append(".").append(col).append(" = UNIX_TIMESTAMP(NOW()),");
                } else {
                    upBf.append(defaultFieldStr)
                            .append("\t\t\t\t").append(tableName).append(".").append(col).append(" = #{").append(field).append("},\n")
                            .append("\t\t\t</if>");
                }
                updateConditions.add(upBf.toString());
            }
        }

        velocityContext.put(CodeConfigType.RESULT_MAP_COLUMNS.getDesc(), resultMapColumns);
        velocityContext.put(CodeConfigType.WHERE_CONDITIONS.getDesc(), whereConditions);
        velocityContext.put(CodeConfigType.INSERT_VALUE_CONDITIONS.getDesc(), insertValueConditions);
        velocityContext.put(CodeConfigType.INSERT_COLS_CONDITIONS.getDesc(), insertColsConditions);
        velocityContext.put(CodeConfigType.UPDATE_CONDITIONS.getDesc(), updateConditions);
        for (int i = 0; i < columns.size() - 1; i++) {
            String tempCol = columns.get(i) + ",";
            columns.set(i, tempCol);
        }
        velocityContext.put(CodeConfigType.COLUMNS.getDesc(), columns);
    }

    @Override
    protected GeneratorType getPackageConfigType() {
        return GeneratorType.MAPPER;
    }

    @Override
    protected String getDescription() {
        return " 持久层Mapper";
    }


}
