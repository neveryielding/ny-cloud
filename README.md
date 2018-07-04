# ny-cloud( 开源项目 )

ny-cloud 是基于Spring Cloud微服务开发开源权限管理系统，可用作于前期微服务权限系统搭建，定义统一权限管理，权限资源初始化，采用Spring Security + Oauth2.0，权限设计参照RABC模型，支持多个服务并行开发， 集成了网关(Gateway)，注册中心(Erueka)，认证服务(Auth)，权限服务(Admin)
模块介绍如下：<br>
			eureka-server ->  服务注册中心<br>
			api-gateway -> 服务网关<br>
			auth-server -> 服务认证授权中心<br>
			admin ->  权限管理服务<br>
此项目采用前后端分离的开发模式，前端为ny-vue项目，具体介绍，请移动步该项目<br>
项目访问地址：[点击访问](http://www.stars21.cn) 线上项目目前是使用docker容器在运行，仅供演示。<br>
账号密码： guest  123456 <br>
技术交流群： 807233785 <br>
此项目前端使用ny-vue项目<br>
项目启动顺序<br>
依次是 eureka-server(端口：8760)，auth-serve(端口：8761)，admin(端口：8762)，api-gateway(端口：7000)<br>
以上项目全部正常启动后 再启动前端ny-vue(端口：1000) 项目即可<br>
#### 前端项目UI截图：
具体前端项目介绍请移步：[点击移步](https://github.com/neveryielding/ny-vue)<br>
登录页面
[![登录页面](http://chuantu.biz/t6/331/1529650483x-1566688497.png "登录页面")](http://chuantu.biz/t6/331/1529650483x-1566688497.png "登录页面")
首页
[![首页](http://chuantu.biz/t6/331/1529650610x-1404817653.png "首页")](http://chuantu.biz/t6/331/1529650610x-1404817653.png "首页")
用户管理
[![用户管理](http://chuantu.biz/t6/331/1529650658x-1404817653.png "用户管理")](http://chuantu.biz/t6/331/1529650658x-1404817653.png "用户管理")
菜单管理
[![菜单管理](http://chuantu.biz/t6/331/1529650719x-1404817653.png "菜单管理")](http://chuantu.biz/t6/331/1529650719x-1404817653.png "菜单管理")
菜单树操作
[![菜单树操作](http://chuantu.biz/t6/331/1529650761x-1404817653.png "菜单树操作")](http://chuantu.biz/t6/331/1529650761x-1404817653.png "菜单树操作")
角色管理
[![角色管理](http://chuantu.biz/t6/331/1529651099x-1404817533.png "角色管理")](http://chuantu.biz/t6/331/1529651099x-1404817533.png "角色管理")
角色关联权限
[![角色关联权限](http://chuantu.biz/t6/331/1529651144x-1404817533.png "角色关联权限")](http://chuantu.biz/t6/331/1529651144x-1404817533.png "角色关联权限")
用户组管理
[![用户组管理](http://chuantu.biz/t6/331/1529651196x-1404817533.png "用户组管理")](http://chuantu.biz/t6/331/1529651196x-1404817533.png "用户组管理")
资源管理
[![资源管理](http://chuantu.biz/t6/331/1529651262x-1404817533.png "资源管理")](http://chuantu.biz/t6/331/1529651262x-1404817533.png "资源管理")
权限管理
[![权限管理](http://chuantu.biz/t6/331/1529651300x-1404817533.png "权限管理")](http://chuantu.biz/t6/331/1529651300x-1404817533.png "权限管理")
权限关联资源
[![权限关联资源](https://s22.postimg.cc/sb0f7ikk1/permission_Resource.png "权限关联资源")](https://s22.postimg.cc/sb0f7ikk1/permission_Resource.png "权限关联资源")

