package com.platform.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.platform.core.entity.BaseEntity;
import com.platform.core.entity.Page;
import com.platform.core.persistence.BaseMapper;
import com.platform.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用service实现
 *
 * @author wangyu
 * @date 2019/10/27 23:26
 */
@Service
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    @Autowired
    protected BaseMapper<T> baseMapper;

    @Override
    public T get(Serializable id) {
        return baseMapper.get(id);
    }

    @Override
    public T get(T entity) {
        return baseMapper.get(entity);
    }

    @Override
    public T findOne(Map<String, Object> map) {
        return baseMapper.findOne(map);
    }

    @Override
    public List<T> findByIds(Long[] ids) {
        if (ids != null && ids.length > 0) {
            return baseMapper.findByIds(ids);
        }
        return Lists.newArrayList();
    }

    @Override
    public List<T> findList(Map<String, Object> map) {
        return baseMapper.findList(map);
    }


    @Override
    public List<T> findAll() {
        return baseMapper.findList(Maps.newHashMapWithExpectedSize(16));
    }

    @Override
    public long countNums(Map<String, Object> map) {
        return baseMapper.countNums(map);
    }

    @Override
    public int insert(T entity) {
        return baseMapper.insert(entity);
    }

    @Override
    public int update(T entity) {
        return baseMapper.update(entity);
    }

    @Override
    public int delete(T entity) {
        return baseMapper.delete(entity);
    }

    @Override
    public int delete(Serializable id) {
        return baseMapper.delete(id);
    }

    @Override
    public int batchDelete(Long[] ids) {
        return baseMapper.batchDelete(ids);
    }
}
