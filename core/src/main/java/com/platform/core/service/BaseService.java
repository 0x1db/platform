package com.platform.core.service;

import com.platform.core.entity.BaseEntity;
import com.platform.core.entity.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 通用service
 * @author wangyu
 * @date 2019/10/27 23:24
 */
public interface BaseService<T extends BaseEntity> {
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
     * 获取单条数据
     *
     * @param map
     * @return
     */
    T findOne(Map<String, Object> map);

    /**
     * 查询列表数据
     *
     * @param map
     * @return
     */
    List<T> findList(Map<String, Object> map);

    /**
     * 查询所有数据
     *
     * @return
     */
    List<T> findAll();

    /**
     * 查询列表数据,根据数据ids
     *
     * @param ids
     * @return
     */
    List<T> findByIds(Long[] ids);

    /**
     * 查询分页数据
     *
     * @param page 分页对象
     * @return
     */
    Page<T> findPages(Page<T> page);


    /**
     * 统计个数
     *
     * @param map
     * @return
     */
    long countNums(Map<String, Object> map);

    /**
     * 插入数据
     *
     * @param entity
     */
    int insert(T entity);

    /**
     * 更新数据
     *
     * @param entity
     */
    int update(T entity);

    /**
     * 删除数据
     *
     * @param entity
     */
    int delete(T entity);

    /**
     * 删除数据
     *
     * @param id
     */
    int delete(Serializable id);


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int batchDelete(Long[] ids);
}
