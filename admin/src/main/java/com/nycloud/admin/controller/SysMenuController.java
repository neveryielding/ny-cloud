package com.nycloud.admin.controller;

import com.nycloud.admin.model.SysMenu;
import com.nycloud.admin.service.SysMenuService;
import com.nycloud.common.constants.SysConstant;
import com.nycloud.common.dto.RequestDto;
import com.nycloud.common.vo.HttpResponse;
import com.nycloud.admin.security.ResourcesMapping;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/10 0010
 * @modified By:
 * @version: 1.0
 **/
@Api(value = "菜单管理", tags = {"菜单管理接口"})
@RestController
@RequestMapping("api/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "菜单添加", notes = "根据SysMenu添加菜单")
    @ResourcesMapping(element = "添加", code = "sys_menu_add")
    @PostMapping
    public HttpResponse add(@Validated @RequestBody SysMenu sysMenu, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.errorParams();
        }
        sysMenuService.insert(sysMenu);
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "菜单删除", notes = "根据菜单id删除菜单信息")
    @ResourcesMapping(element = "删除", code = "sys_menu_delete")
    @DeleteMapping("/{id}")
    public HttpResponse delete(@PathVariable int id) {
        sysMenuService.deleteById(id);
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "菜单查询", notes = "可分页并可根据菜单名称模糊查询")
    @ResourcesMapping(element = "查询", code = "sys_menu_query")
    @GetMapping
    public HttpResponse query(RequestDto requestDto) {
        requestDto.setKey("name");
        return HttpResponse.resultSuccess(sysMenuService.findByPageList(requestDto, SysMenu.class));
    }

    @ApiOperation(value = "菜单修改", notes = "根据传递的SysMenu对象来更新, SysMenu对象必须包含id")
    @ResourcesMapping(element = "修改", code = "sys_menu_update")
    @PutMapping
    public HttpResponse update(@Validated @RequestBody SysMenu sysMenu, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.errorParams();
        }
        sysMenuService.updateById(sysMenu);
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "菜单详情查询", notes = "根据id查询菜单详细信息")
    @ResourcesMapping(element = "修改", code = "sys_menu_update")
    @GetMapping("/{id}")
    public HttpResponse info(@PathVariable int id) {
        sysMenuService.selectById(id);
        return HttpResponse.resultSuccess();
    }

    @ApiOperation(value = "菜单树查询",  notes = "查询所有可用菜单并返回树状结构")
    @ResourcesMapping(element = "菜单树查询", code = "sys_menu_tree")
    @GetMapping("/tree")
    public HttpResponse tree() {
        return HttpResponse.resultSuccess(sysMenuService.loadAllMenuTree());
    }

    @ApiOperation(value = "菜单是否已存在", notes = "根据SysMenu对象设定的字段值来查询判断")
    @GetMapping(SysConstant.API_NO_PERMISSION + "/exist")
    public HttpResponse exist(SysMenu sysMenu) {
        if (sysMenu == null) {
            return HttpResponse.errorParams();
        }
        return HttpResponse.resultSuccess(sysMenuService.selectOne(sysMenu) != null);
    }

}