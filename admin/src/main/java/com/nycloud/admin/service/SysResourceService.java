package com.nycloud.admin.service;

import com.github.pagehelper.PageHelper;
import com.nycloud.admin.dto.SysResourceQueryDto;
import com.nycloud.admin.mapper.SysResourceMapper;
import com.nycloud.admin.model.SysResource;
import com.nycloud.admin.model.SysUser;
import com.nycloud.common.dto.RequestDto;
import com.nycloud.common.utils.ListUtils;
import com.nycloud.common.vo.ResponsePage;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/15 0015
 * @modified By:
 * @version: 1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class SysResourceService extends BaseService<SysResourceMapper, SysResource> {

    public ResponsePage<SysResource> findByPageList(SysResourceQueryDto dto) {
        PageHelper.startPage(dto.getPage(), dto.getSize());
        Example example = new Example(SysResource.class);
        Example.Criteria criteria = example.createCriteria();
        if (dto.getType() != 0) {
            criteria.andCondition("type=", dto.getType());
        }
        if (StringUtils.isNotBlank(dto.getName())) {
            criteria.andLike(dto.getKey(), "%" + dto.getName() + "%");
        }
        List<SysResource> list = mapper.selectByExample(example);
        return new ResponsePage<>(list);
    }

    public List<SysResource> selectSysResourceTree() {
        List<SysResource> allSysResources = this.mapper.selectAllResources();
        List<SysResource> sysResources = new ArrayList<>();
        SysResource parent = null;
        for (int i = 0; i < allSysResources.size(); i ++) {
            SysResource item = allSysResources.get(i);
            if (item.getLevel() == 1) {
                sysResources.add(item);
            } else {
                if (parent == null || !item.getParentId().equals(parent.getId())) {
                    for (int j = 0; j < sysResources.size(); j ++) {
                        SysResource parentItem = sysResources.get(j);
                        if (item.getParentId().equals(parentItem.getId())) {
                            parent = parentItem;
                            parent.setChildren(new ArrayList<>());
                            break;
                        }
                    }
                }
                parent.getChildren().add(item);
            }
        }
        return sysResources;
    }

    public SysResource selectUserRolePermissionResource(Map<String, Object> map) {
        return this.mapper.selectUserRolePermissionResource(map);
    }


}
