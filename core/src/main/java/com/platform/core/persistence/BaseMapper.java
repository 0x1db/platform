package com.platform.core.persistence;

import com.platform.core.entity.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * mapper基类
 *
 * @author wangyu
 * @date 2019/10/27 22:45
 */
public interface BaseMapper<T> {

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    T get(Serializable id);

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    T get(T entity);

    /**
     * 查询单个对象
     *
     * @param map
     * @return
     */
    T findOne(Map<String, Object> map);


    /**
     * 根据指定id，查询数据列表
     *
     * @param ids
     * @return
     */
    List<T> findByIds(Long[] ids);

    /**
     * 查询数据列表
     *
     * @param map
     * @return
     */
    List<T> findList(Map<String, Object> map);

    /**
     * 查询数据分页列表
     *
     * @param page
     * @return
     */
    List<T> findPages(Page<T> page);

    /**
     * 按照条件统计
     *
     * @param map
     * @return
     */
    long countNums(Map<String, Object> map);


    /**
     * 查询所有数据列表
     *
     * @return
     */
    List<T> findAll();

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    int insert(T entity);

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     * 删除数据
     *
     * @return
     * @see public int delete(T entity)
     */
    int delete(Serializable id);

    /**
     * 删除数据
     *
     * @param entity
     * @return
     */
    int delete(T entity);

    /**
     * 批量删除数据
     *
     * @param ids
     * @return
     */
    int batchDelete(Long[] ids);
}
