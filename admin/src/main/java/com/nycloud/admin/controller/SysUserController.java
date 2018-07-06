package com.nycloud.admin.controller;

import com.nycloud.admin.dto.SysUserDto;
import com.nycloud.admin.dto.UserRoleDto;
import com.nycloud.admin.model.MenuTree;
import com.nycloud.admin.model.SysUser;
import com.nycloud.admin.model.SysUserRolePk;
import com.nycloud.admin.security.UserEntity;
import com.nycloud.admin.service.SysMenuService;
import com.nycloud.admin.service.SysUserRolePkService;
import com.nycloud.admin.service.SysUserService;
import com.nycloud.common.constants.SysConstant;
import com.nycloud.common.dto.RequestDto;
import com.nycloud.common.vo.HttpResponse;
import com.nycloud.admin.security.ResourcesMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/2 0002
 * @modified By:
 * @version: 1.0
 **/
@Api(value = "用户管理", tags = {"用户管理接口"})
@RestController
@RequestMapping(value = "api/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRolePkService sysUserRolePkService;

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "用户添加", notes = "根据UserDto保存用户对象")
    @ResourcesMapping(element = "添加", code = "sys_user_add")
    @PostMapping
    public HttpResponse add(@Validated @RequestBody SysUserDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || StringUtils.isBlank(dto.getPassword())) {
            return HttpResponse.errorParams();
        }
        sysUserService.save(dto);
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "角色删除", notes = "根据角色id删除角色信息")
    @ResourcesMapping(element = "删除", code = "sys_user_delete")
    @DeleteMapping("/{id}")
    public HttpResponse delete(@PathVariable Long id) {
        sysUserService.delete(id);
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "用户查询", notes = "可分页并可根据用户名称模糊检索")
    @ResourcesMapping(element = "查询", code = "sys_user_query")
    @GetMapping
    public HttpResponse query(RequestDto requestDto) {
        requestDto.setKey("username");
        return HttpResponse.resultSuccess(sysUserService.findByPageList(requestDto, SysUser.class));
    }

    @ApiOperation(value = "用户修改", notes = "根据传递的SysUser对象来更新, SysUser对象必须包含id")
    @ResourcesMapping(element = "修改", code = "sys_user_update")
    @PutMapping
    public HttpResponse update(@Validated @RequestBody SysUserDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || StringUtils.isBlank(dto.getId())) {
            return HttpResponse.errorParams();
        }
        sysUserService.update(dto);
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "用户详情查询", notes = "根据id查询用户详细信息")
    @ResourcesMapping(element = "查询详情", code = "sys_user_detail")
    @GetMapping("/{id}")
    public HttpResponse info(@PathVariable String id) {
        SysUser sysUser = new SysUser();
        sysUser.setId(Long.valueOf(id));
        return HttpResponse.resultSuccess(sysUserService.selectOne(sysUser));
    }

    @ApiOperation(value = "用户编辑信息查询", notes = "根据id查询用户修改信息")
    @ResourcesMapping(element = "查询编辑详情", code = "sys_user_edit_info")
    @GetMapping("/edit/{id}")
    public HttpResponse editInfo(@PathVariable String id) {
        return HttpResponse.resultSuccess(sysUserService.selectUserGroupInfo(Long.valueOf(id)));
    }

    @ApiOperation(value = "用户关联角色批量添加",  notes = "保存多个SysUserRolePk对象")
    @ResourcesMapping(element = "添加", code = "sys_user_role_add")
    @PostMapping("/batchRoleAdd")
    public HttpResponse batchRoleAdd(@RequestBody List<SysUserRolePk> list) {
        if (list == null || list.size() == 0) {
            return HttpResponse.errorParams();
        }
        sysUserRolePkService.batchInsert(list);
        return new HttpResponse().success();
    }

    @ApiOperation(value = "用户关联角色批量删除",  notes = "根据用户id和多个角色id删除关联")
    @ResourcesMapping(element = "删除", code = "sys_user_role_delete")
    @PostMapping("/batchRoleDelete")
    public HttpResponse batchRoleDelete(@Validated @RequestBody UserRoleDto dto) {
        if (dto.getRoleIds() == null || dto.getRoleIds().length == 0) {
            return HttpResponse.errorParams();
        }
        sysUserRolePkService.batchDelete(dto.getUserId(), dto.getRoleIds());
        return new HttpResponse().success();
    }

    @ApiOperation(value = "用户已关联角色查询",  notes = "根据用户id查询该用户已关联的角色并返回角色列表")
    @ResourcesMapping(element = "查询", code = "sys_user_role")
    @GetMapping("/userRoleList")
    public HttpResponse userRoleList(UserRoleDto dto) {
        return HttpResponse.resultSuccess(sysUserRolePkService.loadUserRoles(dto));
    }

    @ApiOperation(value = "用户未关联角色查询", notes = "根据用户id查询该用户未关联的角色并返回角色列表")
    @ResourcesMapping(element = "查询", code = "sys_user_no_role")
    @GetMapping("/userNoRelationRoleList")
    public HttpResponse userNoRelationRoleList(UserRoleDto dto) {
        return HttpResponse.resultSuccess(sysUserRolePkService.loadUserNoRelationRoles(dto));
    }


    /**
     * 下面是不要校验权限的接口，注意请加上常量声明 Start--->
     */

    @ApiOperation(value = "查询用户名是否存在", notes = "根据用户Id查询分配的角色权限下面的资源列表")
    @GetMapping(SysConstant.API_NO_PERMISSION + "checkUserNameIsExist")
    public HttpResponse checkUserNameIsExist(@RequestParam String username) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        return HttpResponse.resultSuccess(sysUserService.selectOne(sysUser) != null);
    }

    @ApiOperation(value = "获取登陆授权后的用户信息", notes = "根据授权Authentication中UserEntity中的userId获取")
    @GetMapping(SysConstant.API_NO_PERMISSION + "userInfo")
    public HttpResponse userInfo(Authentication authentication) {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        SysUser sysUser = new SysUser();
        sysUser.setId(userEntity.getUserId());
        return HttpResponse.resultSuccess(sysUserService.selectOne(sysUser));
    }

    @ApiOperation(value = "用户可用菜单树查询", notes = "根据用户权限查询已分配好的菜单")
    @GetMapping(SysConstant.API_NO_PERMISSION +  "userMenuTree")
    public HttpResponse userMenuTree(Authentication authentication) {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        // 如果是超级管理员则直接返回所有菜单
        HttpResponse response = new HttpResponse();
        List<MenuTree> result = new ArrayList<>();
        if (userEntity.isAdmin()) {
            result = sysMenuService.loadAllMenuTree();
        } else if (userEntity.isRoles()){
            result = sysUserService.selectUserMenuTree(userEntity.getUserId());
        }
        return response.success(result);
    }

    @ApiOperation(value = "用户所有资源编码查询",  notes = "根据用户id查询该用户已关联的角色并返回角色列表")
    @GetMapping(SysConstant.API_NO_PERMISSION +  "userAllResourceCodes")
    public HttpResponse userAllResourceCodes(Authentication authentication) {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        return HttpResponse.resultSuccess(sysUserService.selectUserAllResourceCodes(userEntity.getUserId()));
    }

    @ApiOperation(value = "用户多个资源编码匹配",  notes = "根据用户id查询该用户已关联的角色并返回角色列表")
    @GetMapping(SysConstant.API_NO_PERMISSION +  "userResourceCodes")
    public HttpResponse userResourceCodes(Authentication authentication, String[] resourceCodes) {
        if (ArrayUtils.isEmpty(resourceCodes)) {
            return HttpResponse.errorParams();
        }
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        // 如果是超级管理员则直接返回资源
        if (userEntity.isAdmin()) {
            return HttpResponse.resultSuccess(resourceCodes);
        }
        Map<String, Object> map = new HashMap<String, Object>(2) {{
           put("userId", userEntity.getUserId());
           put("codes", resourceCodes);
        }};
        return HttpResponse.resultSuccess(sysUserService.selectUserResourceCodes(map));
    }

    /**
     * 上面是不要校验权限的接口，注意请加上常量声明 <---End
     */

}
