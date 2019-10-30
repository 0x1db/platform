package com.platform.generator.core.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.platform.generator.config.CodeConfigType;
import com.platform.generator.core.connect.Connector;
import com.platform.generator.core.context.CodeContext;
import com.platform.generator.core.context.GeneratorType;
import com.platform.generator.core.utils.GeneratorStringUtils;
import com.platform.generator.core.utils.PropertiesUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import java.util.*;

/**
 * Service代码生成
 *
 * @author: wangyu
 * @date: 2019/10/26 22:56
 */
public class ServiceGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, CodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
        velocityContext.put(CodeConfigType.SERIAL_VERSION_UID.getDesc(), String.valueOf(UUID.randomUUID().getLeastSignificantBits()));

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

        Properties properties = cxt.getProperties();

        velocityContext.put(CodeConfigType.METHODS.getDesc(), generateGetAndSetMethods(colMap, cxt));
        velocityContext.put(CodeConfigType.FIELDS.getDesc(), generateFields(colMap, columnRemarkMap));
        velocityContext.put(CodeConfigType.IMPORT_SETS.getDesc(), importSets);
        velocityContext.put(CodeConfigType.CONVERT_DOMAINS.getDesc(), getCovertDomainFields(colMap, properties));
        velocityContext.put(CodeConfigType.CONVERTS.getDesc(), getCovertFields(colMap, properties));
        String noPrefixTableName = StringUtils.upperCase(tableName.toLowerCase()
                .replaceFirst(PropertiesUtils.getTablePrefix(cxt.getProperties()), ""));
        velocityContext.put(CodeConfigType.NO_PREFIX_TABLE_NAME.getDesc(), noPrefixTableName);
        velocityContext.put(CodeConfigType.ADD_UTILS.getDesc(), getUtilFields(colMap, columnRemarkMap, noPrefixTableName));

        Map<String, String> checkUpdateMap = Maps.newHashMap();
        String primaryKey = (String) cxt.getAttribute(CodeConfigType.PRIMARY_KEY);
        for (String col : colMap.keySet()) {
            if (StringUtils.equals(col, primaryKey)) {
                checkUpdateMap.put(col, colMap.get(col));
            }
        }
        velocityContext.put(CodeConfigType.UPT_UTILS.getDesc(), getUtilFields(checkUpdateMap, columnRemarkMap, noPrefixTableName));
    }

    /**
     * domain模板
     *
     * @param map
     * @param properties
     * @return
     */
    protected List<String> getCovertDomainFields(Map<String, String> map, Properties properties) {
        Set<String> keySet = map.keySet();
        List<String> converts = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            String field = GeneratorStringUtils.format(key);
            sb.append(velocityContext.get(CodeConfigType.LOW_CLASS_NAME.getDesc()))
                    .append(properties.get(CodeConfigType.DOMAIN.getType()))
                    .append(".set" + GeneratorStringUtils.firstUpperNoFormat(field) + "(")
                    .append(velocityContext.get(CodeConfigType.LOW_CLASS_NAME.getDesc()))
                    .append(".get" + GeneratorStringUtils.firstUpperNoFormat(field) + "())");
            converts.add(sb.toString());
        }
        return converts;
    }

    /**
     * 转换类
     *
     * @param colMap
     * @param properties
     * @return
     */
    protected List<String> getCovertFields(Map<String, String> colMap, Properties properties) {
        Set<String> keySet = colMap.keySet();
        List<String> converts = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            String field = GeneratorStringUtils.format(key);
            sb.append(velocityContext.get(CodeConfigType.LOW_CLASS_NAME.getDesc()))
                    .append(".set" + GeneratorStringUtils.firstUpperNoFormat(field) + "(")
                    .append(velocityContext.get(CodeConfigType.LOW_CLASS_NAME.getDesc()))
                    .append(properties.get(CodeConfigType.DOMAIN.getType()))
                    .append(".get" + GeneratorStringUtils.firstUpperNoFormat(field) + "())");
            converts.add(sb.toString());
        }
        return converts;
    }

    /**
     * 组装工具类模板
     *
     * @param colMap
     * @param columnRemarkMap
     * @param noPrefixTableName
     * @return
     */
    protected List<String> getUtilFields(Map<String, String> colMap, Map<String, String> columnRemarkMap, String noPrefixTableName) {
        Set<String> keySet = colMap.keySet();
        List<String> utils = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            String field = GeneratorStringUtils.format(key);
            String colType = colMap.get(key);
            if (StringUtils.equals(colType, "String")) {
                sb.append("\tif (StringUtils.isBlank(")
                        .append(velocityContext.get(CodeConfigType.LOW_CLASS_NAME.getDesc()))
                        .append(".get")
                        .append(GeneratorStringUtils.firstUpperNoFormat(field))
                        .append("())) {\n");
            } else {
                sb.append("\tif (")
                        .append(velocityContext.get(CodeConfigType.LOW_CLASS_NAME.getDesc()))
                        .append(".get")
                        .append(GeneratorStringUtils.firstUpperNoFormat(field))
                        .append("() == null) {\n");
            }
            String remark = columnRemarkMap.get(key);
            sb.append("\t\t\tLOGGER.warn(\"")
                    .append(field)
                    .append(remark)
                    .append("为空, ")
                    .append(velocityContext.get(CodeConfigType.LOW_CLASS_NAME.getDesc()))
                    .append("=\" + ")
                    .append(velocityContext.get(CodeConfigType.LOW_CLASS_NAME.getDesc()))
                    .append(");\n");
            sb.append("\t\t\t")
                    .append("throw new ")
                    .append(velocityContext.get(CodeConfigType.UP_CLASS_NAME.getDesc()))
                    .append("Exception(")
                    .append(velocityContext.get(CodeConfigType.UP_CLASS_NAME.getDesc()))
                    .append("Result.")
                    .append(noPrefixTableName)
                    .append("_")
                    .append(StringUtils.upperCase(key))
                    .append("_NULL);\n");
            sb.append("\t\t}\n");
            utils.add(sb.toString());
        }
        return utils;
    }

    @Override
    protected GeneratorType getPackageConfigType() {
        return GeneratorType.SERVICE;
    }

    @Override
    protected String getDescription() {
        return "服务层";
    }
}
