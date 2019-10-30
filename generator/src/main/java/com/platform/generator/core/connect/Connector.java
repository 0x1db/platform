package com.platform.generator.core.connect;

import java.util.List;
import java.util.Map;

/**
 * 连接数据库接口
 *
 * @author wangyu
 * @date 2019/10/26 22:50
 */
public interface Connector {

    /**
     * 获取表的键值类型
     *
     * @param tableName
     * @return
     */
    Map<String, String> getPrimaryKey(String tableName);

    /**
     * 获取类型
     *
     * @param tableName
     * @return
     */
    Map<String, String> getColumnNameType(String tableName);

    /**
     * 获取列备注
     *
     * @param tableName
     * @return
     */
    Map<String, String> getColumnRemark(String tableName);

    /**
     * 获取表注释
     *
     * @param tableName
     * @return
     */
    String getTableRemark(String tableName);

    /**
     * 获取所有的索引信息
     *
     * @param tableName
     * @return
     */
    List<String> getAllIndex(String tableName);

    /**
     * 关闭JDBC连接
     */
    void closeConnection();

    /**
     * 获取表格自增字段
     *
     * @param tableName
     * @return
     */
    List<String> getAutoIncrementCol(String tableName);


}
