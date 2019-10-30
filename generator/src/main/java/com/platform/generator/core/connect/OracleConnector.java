package com.platform.generator.core.connect;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 功能描述：获取Mysql的连接池信息
 *
 * @author: wangyu
 * @date: 2019/10/26 22:56
 */
public class OracleConnector extends MysqlConnector {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OracleConnector.class);

    public OracleConnector(Properties properties) {
        super(properties);
    }

    @Override
    public Map<String, String> getPrimaryKey(String tableName) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            ResultSet resultSet = getDatabaseMetaData().getPrimaryKeys(null, null, tableName);
            while (resultSet.next()) {
                String primaryKey = resultSet.getString("COLUMN_NAME");
                String primaryKeyType = getColumnNameType(resultSet.getString("TABLE_NAME")).get(primaryKey);
                map.put("primaryKey", primaryKey);
                map.put("primaryKeyType", primaryKeyType);
            }

            if (map.isEmpty()) {
                Map<String, String> columnNameType = getColumnNameType(tableName);
                String primaryKey = (String) getProperties().get("oracle.primaryKey.name");
                map.put("primaryKey", primaryKey);
                map.put("primaryKeyType", columnNameType.get(primaryKey));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    @Override
    public List<String> getAllIndex(String tableName) {
        return Lists.newArrayList();
    }

    @Override
    protected Connection getConnection() {
        Connection connection;
        try {
            String driver = getProperties().getProperty("oracle.jdbc.driverClassName");
            Class.forName(driver);
            String url = getProperties().getProperty("oracle.jdbc.url");
            String username = getProperties().getProperty("oracle.jdbc.username");
            String pwd = getProperties().getProperty("oracle.jdbc.password");
            connection = DriverManager.getConnection(url, username, pwd);
            session.put(SessionType.connection, connection);
            return connection;
        } catch (Exception e) {
            LOGGER.error("获取Oracle JDBC信息失败!", e);
            throw new RuntimeException("连接Oracle失败");
        }
    }

    @Override
    protected Map<String, Integer> getColumnNameAndSize(String tableName) throws SQLException {
        return Maps.newHashMap();
    }

    @Override
    public List<String> getAutoIncrementCol(String tableName) {
        return Lists.newArrayList();
    }
}
