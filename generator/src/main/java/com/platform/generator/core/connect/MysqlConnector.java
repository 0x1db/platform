package com.platform.generator.core.connect;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.platform.generator.config.CodeConfigType;
import com.platform.generator.core.utils.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * Mysql Connector实现
 *
 * @author: wangyu
 * @date: 2019/10/26 22:50
 */
@SuppressWarnings("ALL")
public class MysqlConnector implements Connector {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlConnector.class);

    /**
     * Connector session
     */
    public Map<SessionType, Object> session = Maps.newHashMap();

    /**
     * 配置properties
     */
    protected Properties properties;

    /**
     * 构造带配置
     *
     * @param properties
     */
    public MysqlConnector(Properties properties) {
        this.properties = properties;
    }

    /**
     * 获取所有属性和类型的map
     * key:属性 value:类型
     *
     * @param tableName
     * @return
     */
    @Override
    public Map<String, String> getColumnNameType(String tableName) {
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        DatabaseMetaData meta = getDatabaseMetaData();
        try {
            ResultSet colRet = meta.getColumns(null, "%", tableName, "%");
            while (colRet.next()) {
                String columnName = colRet.getString("COLUMN_NAME");
                int digits = colRet.getInt("DECIMAL_DIGITS");
                int dataType = colRet.getInt("DATA_TYPE");

                Integer columnSize = null;
                Map<String, Integer> columnNameAndSizeMap = getColumnNameAndSize(tableName);
                if (columnNameAndSizeMap != null && !columnNameAndSizeMap.isEmpty()) {
                    columnSize = columnNameAndSizeMap.get(columnName);
                }
                if (columnSize == null) {
                    columnSize = colRet.getInt("COLUMN_SIZE");
                }
                String columnType = getDataType(dataType, digits, columnSize);
                colMap.put(columnName, columnType);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return colMap;
    }


    /**
     * 获取表结构字段描述备注
     *
     * @param tableName
     * @return
     */
    @Override
    public Map<String, String> getColumnRemark(String tableName) {
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        DatabaseMetaData meta = getDatabaseMetaData();
        try {
            ResultSet colRet = meta.getColumns(null, "%", tableName, "%");
            while (colRet.next()) {
                String columnName = colRet.getString("COLUMN_NAME");
                String columnRemark = colRet.getString("REMARKS");
                colMap.put(columnName, columnRemark);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return colMap;
    }

    /**
     * 获取表备注
     *
     * @param tableName
     * @return
     */
    @Override
    public String getTableRemark(String tableName) {
        Connection connection = getConnection();
        String sql = "select TABLE_COMMENT from information_schema.tables where table_name = ? and table_schema = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tableName);
            preparedStatement.setString(2, connection.getCatalog());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String columnName = resultSet.getString("TABLE_COMMENT");
                return columnName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(tableName + "表没有注释", e);
        }

        return "";
    }

    /**
     * 获取所有的表索引
     *
     * @param tableName
     * @return
     */
    @Override
    public List<String> getAllIndex(String tableName) {
        try {
            List<String> indexs = Lists.newArrayList();
            ResultSet resultSet = getDatabaseMetaData().getIndexInfo(null, null, tableName, false, true);
            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                indexs.add(columnName);
            }
            return indexs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 尝试关闭Connection
     */
    @Override
    public void closeConnection() {
        try {
            Object connection = session.get(SessionType.connection);
            if (connection != null) {
                Connection conn = (Connection) connection;
                try {
                    conn.close();
                } catch (Exception ex) {
                    LOGGER.error("关闭Connection异常", ex);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("关闭Connection失败", ex);
        }
    }

    /**
     * 获取自增长的列
     *
     * @param tableName
     * @return
     */
    @Override
    public List<String> getAutoIncrementCol(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return Lists.newArrayList();
        }

        List<String> columnNames = Lists.newArrayList();
        Connection connection;
        try {
            connection = (Connection) session.get(SessionType.connection);
            Statement statement = connection.createStatement();
            //doto 可以不用查所有数据
            ResultSet result = statement.executeQuery("Select * from " + tableName);
            int columnCount = result.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                if (result.getMetaData().isAutoIncrement(i)) {
                    columnNames.add(result.getMetaData().getColumnName(i));
                }
            }
        } catch (Exception e) {
            LOGGER.warn("获取Mysql AUTO_INCREMENT字段异常!", e);
            return Lists.newArrayList();
        }

        return columnNames;
    }

    /**
     * 获取主键
     *
     * @param tableName
     * @return
     */
    @Override
    public Map<String, String> getPrimaryKey(String tableName) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            ResultSet resultSet = getDatabaseMetaData().getPrimaryKeys(null, null, tableName);
            while (resultSet.next()) {
                String primaryKey = resultSet.getString("COLUMN_NAME");
                String primaryKeyType = getColumnNameType(resultSet.getString("TABLE_NAME")).get(primaryKey);

                map.put(CodeConfigType.PRIMARY_KEY.getDesc(), primaryKey);
                map.put(CodeConfigType.PRIMARY_KEY_TYPE.getDesc(), primaryKeyType);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * translate database type into java type
     *
     * @param type
     * @return
     */
    private String getDataType(int type, int digits, int columnSize) {
        String dataType = "";
        switch (type) {
            //12
            case Types.VARCHAR:
                dataType = "String";
                break;
            //4
            case Types.INTEGER:
                dataType = columnSize >= 8 ? "Long" : "Integer";
                break;
            //-7
            case Types.BIT:
                dataType = "Integer";
                break;
            //-1
            case Types.LONGVARCHAR:
                dataType = "String";
                break;
            //-5
            case Types.BIGINT:
                dataType = "Long";
                break;
            //8
            case Types.DOUBLE:
                dataType = getPrecisionType();
                break;
            //7
            case Types.REAL:
                dataType = getPrecisionType();
                break;
            //6
            case Types.FLOAT:
                dataType = getPrecisionType();
                break;
            //3
            case Types.DECIMAL:
                dataType = "BigDecimal";
                break;
            //2
            case Types.NUMERIC:
                switch (digits) {
                    case 0:
                        dataType = "Integer";
                        break;
                    default:
                        dataType = getPrecisionType();
                }
                break;
            //91
            case Types.DATE:
                dataType = "Date";
                break;
            //93
            case Types.TIMESTAMP:
                dataType = "Date";
                break;
            default:
                dataType = "String";
        }
        return dataType;
    }

    /**
     * 是否类型转换
     *
     * @return
     */
    private String getPrecisionType() {
        String dataType;
        String str = "high";
        if (str.equals(PropertiesUtils.getPrecision(properties))) {
            dataType = "BigDecimal";
        } else {
            dataType = "Double";
        }
        return dataType;
    }

    /**
     * 获取JDBC连接信息
     *
     * @return
     */
    protected Connection getConnection() {
        Connection connection = (Connection) session.get(SessionType.connection);
        if (connection != null) {
            return connection;
        }

        try {
            String driverClassName = properties.getProperty("jdbc.driverClassName");
            String url = properties.getProperty("jdbc.url");
            String userName = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        session.put(SessionType.connection, connection);
        return connection;
    }

    /**
     * 获取DatabaseMetaData
     *
     * @return
     */
    protected DatabaseMetaData getDatabaseMetaData() {
        Connection connection = getConnection();
        DatabaseMetaData meta = (DatabaseMetaData) session.get(SessionType.DatabaseMetaData);
        if (meta != null) {
            return meta;
        }

        try {
            meta = connection.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        session.put(SessionType.DatabaseMetaData, meta);
        return meta;
    }

    /**
     * session配置类型
     */
    protected enum SessionType {
        /**
         * 连接
         */
        connection,

        /**
         * 数据库类型
         */
        DatabaseMetaData

    }


    /**
     * 获取表的列大小
     *
     * @param tableName
     * @return
     * @throws SQLException
     */
    protected Map<String, Integer> getColumnNameAndSize(String tableName) throws SQLException {
        Connection connection = getConnection();
        String sql = "select COLUMN_NAME,COLUMN_TYPE from information_schema.columns where table_name = ? and table_schema = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, tableName);
        preparedStatement.setString(2, connection.getCatalog());
        ResultSet resultSet = preparedStatement.executeQuery();

        Map<String, Integer> columnNameAndSizeMap = Maps.newHashMap();
        while (resultSet.next()) {
            String columnName = resultSet.getString("COLUMN_NAME");
            String columnType = resultSet.getString("COLUMN_TYPE");
            if (StringUtils.contains(columnType, "(") && StringUtils.contains(columnType, ")")) {
                String substringBetween = StringUtils.substringBetween(columnType, "(", ")");
                if (StringUtils.isNumeric(substringBetween)) {
                    columnNameAndSizeMap.put(columnName, Integer.parseInt(substringBetween));
                }
            }
        }
        return columnNameAndSizeMap;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
