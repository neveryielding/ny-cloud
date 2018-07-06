package com.nycloud.admin.controller;

import com.nycloud.admin.dto.RolePermissionDto;
import com.nycloud.admin.model.SysRole;
import com.nycloud.admin.model.SysRolePermissionPk;
import com.nycloud.admin.service.SysRolePermissionServicePk;
import com.nycloud.admin.service.SysRoleService;
import com.nycloud.common.constants.SysConstant;
import com.nycloud.common.dto.RequestDto;
import com.nycloud.common.utils.ListUtils;
import com.nycloud.common.vo.HttpResponse;
import com.nycloud.admin.security.ResourcesMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/10 0010
 * @modified By:
 * @version: 1.0
 **/
@Api(value = "角色管理", tags = {"角色管理接口"})
@RestController
@RequestMapping(value = "api/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRolePermissionServicePk sysRolePermissionPkService;

    @ApiOperation(value = "角色添加", notes = "根据SysRole对象创建角色")
    @ResourcesMapping(element = "添加", code = "sys_role_add")
    @PostMapping
    public HttpResponse add(@Validated @RequestBody SysRole role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.errorParams();
        }
        sysRoleService.insert(role);
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "角色删除", notes = "根据角色id删除角色信息")
    @ResourcesMapping(element = "删除", code = "sys_role_delete")
    @DeleteMapping("/{id}")
    public HttpResponse delete(@PathVariable int id) {
        sysRoleService.deleteById(id);
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "角色查询", notes = "可分页并可根据角色名称模糊检索")
    @ResourcesMapping(element = "查询", code = "sys_role_query")
    @GetMapping
    public HttpResponse query(RequestDto requestDto) {
        requestDto.setKey("name");
        return HttpResponse.resultSuccess(sysRoleService.findByPageList(requestDto, SysRole.class));
    }

    @ApiOperation(value = "角色修改", notes = "根据传递的SysRole对象来更新, SysRole对象必须包含id")
    @ResourcesMapping(element = "修改", code = "sys_role_update")
    @PutMapping
    public HttpResponse update(@Validated @RequestBody SysRole sysRole, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.errorParams();
        }
        sysRoleService.updateById(sysRole);
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "用户详情查询", notes = "根据id查询用户详细信息")
    @ResourcesMapping(element = "查询详情", code = "sys_role_detail")
    @GetMapping("/{id}")
    public HttpResponse info(@PathVariable int id) {
        return HttpResponse.resultSuccess(sysRoleService.selectById(id));
    }

    @ApiOperation(value = "角色关联权限批量保存",  notes = "保存多个SysRolePermissionPk对象")
    @ResourcesMapping(element = "添加", code = "sys_role_permission_add")
    @PostMapping("/batchPermissionAdd")
    public HttpResponse batchPermissionAdd(@RequestBody List<SysRolePermissionPk> list) {
        if (ListUtils.isEmpty(list)) {
            return HttpResponse.errorParams();
        }
        sysRolePermissionPkService.batchInsert(list);
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "角色关联权限批量删除",  notes = "根据角色id和多个权限id删除关联")
    @ResourcesMapping(element = "删除", code = "sys_role_permission_delete")
    @PostMapping("/batchPermissionDelete")
    public HttpResponse batchPermissionDelete(@Validated @RequestBody RolePermissionDto dto) {
        if (ArrayUtils.isEmpty(dto.getPermissionIds())) {
            return HttpResponse.errorParams();
        }
        sysRolePermissionPkService.batchDelete(dto.getRoleId(), dto.getPermissionIds());
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "角色未关联权限查询", notes = "根据角色id查询该角色未关联的权限并返回权限列表")
    @ResourcesMapping(element = "查询", code = "sys_role_no_permission")
    @GetMapping("/roleNoRelationPermissionList")
    public HttpResponse roleNoRelationPermissionList(RolePermissionDto dto) {
        return HttpResponse.resultSuccess(sysRolePermissionPkService.loadRoleNoRelationPermissions(dto));
    }

    @ApiOperation(value = "角色已关联权限查询",  notes = "根据角色id查询该角色已关联的权限并返回权限列表")
    @ResourcesMapping(element = "查询", code = "sys_role_permission")
    @GetMapping("/rolePermissionList")
    public HttpResponse rolePermissionList(RolePermissionDto dto) {
        if (ArrayUtils.isEmpty(dto.getPermissionIds())) {
            return HttpResponse.errorParams();
        }
        return HttpResponse.resultSuccess(sysRolePermissionPkService.loadRolePermissions(dto));
    }

    @ApiOperation(value = "角色是否已存在", notes = "根据SysRole对象设定的字段值来查询判断")
    @GetMapping(SysConstant.API_NO_PERMISSION + "exist")
    public HttpResponse exist(SysRole sysRole) {
        if (sysRole == null) {
            return HttpResponse.errorParams();
        }
        return HttpResponse.resultSuccess(sysRoleService.selectOne(sysRole) != null);
    }

}
