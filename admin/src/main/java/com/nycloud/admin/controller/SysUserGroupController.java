package com.nycloud.admin.controller;

import com.nycloud.admin.dto.UserGroupDto;
import com.nycloud.admin.dto.UserGroupRoleDto;
import com.nycloud.admin.model.SysUserGroup;
import com.nycloud.admin.model.SysUserGroupPk;
import com.nycloud.admin.model.SysUserGroupRolePk;
import com.nycloud.admin.service.SysUserGroupPkService;
import com.nycloud.admin.service.SysUserGroupRolePkService;
import com.nycloud.admin.service.SysUserGroupService;
import com.nycloud.common.constants.SysConstant;
import com.nycloud.common.dto.RequestDto;
import com.nycloud.common.vo.HttpResponse;
import com.nycloud.admin.security.ResourcesMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/2 0002
 * @modified By:
 * @version: 1.0
 **/
@Api(value = "用户组管理", tags = {"用户组管理接口"})
@RestController
@RequestMapping(value = "api/sysUserGroup")
public class SysUserGroupController {

    @Autowired
    private SysUserGroupService sysUserGroupService;

    @Autowired
    private SysUserGroupPkService sysUserGroupPkService;

    @Autowired
    private SysUserGroupRolePkService sysUserGroupRolePkService;

    @ApiOperation(value = "用户组添加", notes = "根据SysUserGroup对象创建用户组")
    @ResourcesMapping(element = "添加", code = "sys_user_group_add")
    @PostMapping
    public HttpResponse save(@RequestBody SysUserGroup sysUserGroup, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.errorParams();
        }
        sysUserGroupService.insert(sysUserGroup);
        return new HttpResponse().success();
    }

    @ApiOperation(value = "用户组删除", notes = "根据用户组id删除用户组信息")
    @ResourcesMapping(element = "删除", code = "sys_user_group_delete")
    @DeleteMapping("/{id}")
    public HttpResponse delete(@PathVariable int id) {
        sysUserGroupService.deleteById(id);
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "用户组查询", notes = "可分页并可根据用户组名称模糊检索")
    @ResourcesMapping(element = "查询", code = "sys_user_group_query")
    @GetMapping
    public HttpResponse query(RequestDto requestDto) {
        requestDto.setKey("name");
        return new HttpResponse().success(sysUserGroupService.findByPageList(requestDto, SysUserGroup.class));
    }

    @ApiOperation(value = "用户组修改", notes = "根据传递的SysUserGroup对象来更新, SysUserGroup对象必须包含id")
    @ResourcesMapping(element = "修改", code = "sys_user_group_update")
    @PutMapping
    public HttpResponse update(@Validated @RequestBody SysUserGroup dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.errorParams();
        }
        sysUserGroupService.updateById(dto);
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "用户组详细信息查询", notes = "根据id查询用户组修改信息")
    @ResourcesMapping(element = "查询详情", code = "sys_user_group_info")
    @GetMapping("/{id}")
    public HttpResponse info(@PathVariable int id) {
        return HttpResponse.resultSuccess(sysUserGroupService.selectById(id));
    }

    @ApiOperation(value = "用户组关联用户批量添加",  notes = "添加多个SysUserGroupPk对象")
    @ResourcesMapping(element = "批量添加", code = "sys_group_user_add")
    @PostMapping("/batchUserAdd")
    public HttpResponse batchUserAdd(@RequestBody List<SysUserGroupPk> list) {
        if (list == null || list.size() == 0) {
            return HttpResponse.errorParams();
        }
        sysUserGroupPkService.batchInsert(list);
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "用户组关联用户批量删除",  notes = "根据用户id和多个角色id删除关联")
    @ResourcesMapping(element = "批量删除", code = "sys_group_user_delete")
    @PostMapping("/batchUserDelete")
    public HttpResponse batchUserDelete(@Validated @RequestBody UserGroupDto dto) {
        if (dto.getUserIds() == null || dto.getUserIds().length == 0) {
            return HttpResponse.errorParams();
        }
        sysUserGroupPkService.batchDelete(dto.getGroupId(), dto.getUserIds());
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "用户组未关联用户查询", notes = "根据用户id查询该用户未关联的角色并返回角色列表")
    @ResourcesMapping(element = "查询", code = "sys_group_no_user")
    @GetMapping("/groupNoRelationUserList")
    public HttpResponse groupNoRelationUserList(UserGroupDto dto) {
        return HttpResponse.resultSuccess(sysUserGroupPkService.loadGroupNoRelationUsers(dto));
    }

    @ApiOperation(value = "用户组已关联用户查询",  notes = "根据用户id查询该用户已关联的角色并返回角色列表")
    @ResourcesMapping(element = "查询", code = "sys_group_user")
    @GetMapping("/groupUserList")
    public HttpResponse groupUserList(UserGroupDto dto) {
        return HttpResponse.resultSuccess(sysUserGroupPkService.loadGroupUsers(dto));
    }

    @ApiOperation(value = "用户组关联角色批量添加",  notes = "保存多个SysRolePermissionPk对象")
    @ResourcesMapping(element = "批量添加", code = "sys_group_role_add")
    @PostMapping("/batchRoleAdd")
    public HttpResponse batchRoleAdd(@RequestBody List<SysUserGroupRolePk> list) {
        if (list == null || list.size() == 0) {
            return HttpResponse.errorParams();
        }
        sysUserGroupRolePkService.batchInsert(list);
        return new HttpResponse().success();
    }

    @ApiOperation(value = "用户组关联角色批量删除",  notes = "根据角色id和多个权限id删除关联")
    @ResourcesMapping(element = "批量删除", code = "sys_group_role_delete")
    @PostMapping("/batchRoleDelete")
    public HttpResponse batchRoleDelete(@Validated @RequestBody UserGroupRoleDto dto) {
        if (dto.getRoleIds() == null || dto.getRoleIds().length == 0) {
            return HttpResponse.errorParams();
        }
        sysUserGroupRolePkService.batchDelete(dto.getGroupId(), dto.getRoleIds());
        return new HttpResponse().success();
    }

    @ApiOperation(value = "用户组未关联角色查询", notes = "根据角色id查询该角色未关联的权限并返回权限列表")
    @ResourcesMapping(element = "查询", code = "sys_group_no_role")
    @GetMapping("/groupNoRelationRoleList")
    public HttpResponse groupNoRelationRoleList(UserGroupRoleDto dto) {
        return HttpResponse.resultSuccess(sysUserGroupRolePkService.loadGroupNoRelationRoles(dto));
    }

    @ApiOperation(value = "用户组已关联角色查询",  notes = "根据角色id查询该角色已关联的权限并返回权限列表")
    @ResourcesMapping(element = "查询", code = "sys_group_role")
    @GetMapping("/groupRoleList")
    public HttpResponse groupRoleList(UserGroupRoleDto dto) {
        return HttpResponse.resultSuccess(sysUserGroupRolePkService.loadGroupRoles(dto));
    }

    @ApiOperation(value = "用户所有可用资源查询", notes = "根据用户Id查询分配的角色权限下面的资源列表")
    @GetMapping(SysConstant.API_NO_PERMISSION + "exist")
    public HttpResponse exist(@RequestParam String name) {
        SysUserGroup sysUserGroup = new SysUserGroup();
        sysUserGroup.setName(name);
        return HttpResponse.resultSuccess(sysUserGroupService.selectOne(sysUserGroup) != null);
    }

}
