package com.nycloud.admin.service;

import com.github.pagehelper.PageHelper;
import com.nycloud.admin.model.SysUser;
import com.nycloud.admin.util.EntityUtils;
import com.nycloud.common.dto.RequestDto;
import com.nycloud.common.vo.ResponsePage;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
/**
 * Created by Mr.AG
 * Date: 17/1/13
 * Time: 15:13
 * Version 1.0.0
 */
public abstract class BaseService<M extends Mapper<T>, T> {

    @Autowired
    protected M mapper;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }


    public T selectById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }


    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }


    public List<T> selectListAll() {
        return mapper.selectAll();
    }


    public Long selectCount(T entity) {
        return new Long(mapper.selectCount(entity));
    }


    public void insert(T entity) {
        EntityUtils.setCreateAndUpdateInfo(entity);
        mapper.insert(entity);
    }


    public void insertSelective(T entity) {
        EntityUtils.setCreateAndUpdateInfo(entity);
        mapper.insertSelective(entity);
    }


    public void delete(T entity) {
        mapper.delete(entity);
    }


    public void deleteById(Object id) {
        mapper.deleteByPrimaryKey(id);
    }


    public void updateById(T entity) {
        EntityUtils.setUpdatedInfo(entity);
        mapper.updateByPrimaryKey(entity);
    }


    public void updateSelectiveById(T entity) {
        EntityUtils.setUpdatedInfo(entity);
        mapper.updateByPrimaryKeySelective(entity);

    }

    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    public ResponsePage<T> findByPageList(RequestDto dto, Class cls) {
        PageHelper.startPage(dto.getPage(), dto.getSize());
        Example example = new Example(cls);
        if (StringUtils.isNotBlank(dto.getName())) {
            Example.Criteria criteria = example.createCriteria();
            criteria.andLike(dto.getKey(), "%" + dto.getName() + "%");
        }
        List<T> list = mapper.selectByExample(example);
        return new ResponsePage<>(list);
    }

}