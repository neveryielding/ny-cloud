
## 简介
基于Spring Cloud微服务框架开发的权限管理系统，可用作于微服务权限系统搭建，目前实现了服务统一授权，资源分配，接口权限，页面元素是否可用，权限资源脚本初始化等功能 <br/>
-  [在线访问](http://www.stars21.cn/) <br/><br/>
- **参考架构图**<br/>![enter image description here](http://chuantu.biz/t6/338/1530784528x-1404775647.png)
- **1. 工程和以及运行环境截图** <br/>
 开发工具idea: <br/>
![enter image description here](http://chuantu.biz/t6/338/1530778237x-1404775647.png)<br/>
运行环境**Docker**:(太穷了，所有的软件以及服务全都部署在阿里云一台机器上)，服务器上只开放了80端口，服务调用和其他都是走内网，目前没有做服务集群，机器配置跑不起<br/>
![enter image description here](http://chuantu.biz/t6/338/1530783438x-1404775647.png)
- **2.  具体服务介绍** <br/>
	 eureka-server: 服务注册发现中心 <br/>
	 api-gateway: 服务Zuul网关 <br/>
	 auth-server: 授权服务 <br/>
	 admin: 权限管理服务 <br/>
- **3.  版本功能规划**
- **v1.0 开发完成** <br/>
目前划分了如上面四个服务，整理的大致实现思路参考 [微服务架构中整合网关、权限服务](http://blueskykong.com/2017/12/10/integration/)这里只是提供实现思路，在实际中消化了该思路，并引用了部分代码，以及自己的一些方式去实现，由于1.0版本主要是开发权限服务的业务代码，所以并没有对Spring OAuth2.0 /oauth/token  /oauth/check_token 这两个接口进行重写，只是自定义了部分鉴权部分的UserDetailsService 和 ClientDetailsService<br/>
auth-server 使用了Jwt  生成token, 主要负责token的鉴权与token的分发<br/>
api-gateway 网关路由分发，授权服务鉴权调用 <br/>
admin 权限系统(用户管理，用户组管理，角色管理，权限管理，菜单管理，资源管理)， 接口权限，页面元素权限，权限资源脚本初始化，权限拦截，权限校验等功能<br/>
- **v2.0 开发中** <br/>
**auth-server**<br/>
重写Spring Security OAuth 中 oauth/token oauth/check_token 这两个接口，目前设计诸多不合理，需要修改token生成以及token的校验，token黑名单，白名单，token 加密(采用RSA)以及token自定义缓存等功能<br/>
**api-gateway**<br/>
在网关中加入token的缓存校验机制，以及黑名单过滤功能，在缓存可用的情况下可以不需要调用auth-server进行token校验，直接由网关加入校验功能验证后转发到具体的服务即可，这样减轻了auth的压力，优化网关代码<br/>
**admin**<br/>
代码优化，需要加入多数据源，读写分离，支持多个分布式系统权限的校验处理，支持远程权限校验调用，用户缓存，权限缓存，缓存更新，完善日志管理，异常处理，服务管理等功能<br/>
**goods 暂定**<br/>
商品服务，简单的商品基本信息维护，用于权限Admin校验测试等功能<br/>
**分离模块**<br/>
分离 admin 项目中的common 模块和 security 模块，common 模块为公用的代码部分，security 业务权限处理部分
- **v3.0 功能规划中** <br/>
多租户实现，数据权限等，定制微服务权限开发脚手架    功能后续规划中.........<br/>
- **4.  项目启动顺序** <br/>
 注：由于授权服务使用的redis缓存，所以电脑必须要装redis，不然会导致授权服务无法正常使用<br/>
4.1 初始化数据脚本，mysql 账号密码 root 123456 如果不一致请自行修改项目中的配置文件，在mysql中创建nycloud-admin-v2数据库并设置为utf-8字符集，创建好后将admin/src/db/nycloud-admin-v2.sql脚本导入到该数据库执行，初始化数据库工作完成，<br/>
4.2 先启动eureka-server 再启动其他三个服务，最后再启动前端[ny-vue](https://github.com/neveryielding/ny-vue)工程，即可访问 [localhost](http://localhost:1000/)<br/>
#### 效果截图：
具体前端项目介绍请移步：[点击移步](https://github.com/neveryielding/ny-vue)<br>
[![登录页面](http://chuantu.biz/t6/331/1529650483x-1566688497.png "登录页面")](http://chuantu.biz/t6/331/1529650483x-1566688497.png "登录页面")
[![首页](http://chuantu.biz/t6/331/1529650610x-1404817653.png "首页")](http://chuantu.biz/t6/331/1529650610x-1404817653.png "首页")
[![用户管理](http://chuantu.biz/t6/331/1529650658x-1404817653.png "用户管理")](http://chuantu.biz/t6/331/1529650658x-1404817653.png "用户管理")
[![菜单管理](http://chuantu.biz/t6/331/1529650719x-1404817653.png "菜单管理")](http://chuantu.biz/t6/331/1529650719x-1404817653.png "菜单管理")
[![菜单树操作](http://chuantu.biz/t6/331/1529650761x-1404817653.png "菜单树操作")](http://chuantu.biz/t6/331/1529650761x-1404817653.png "菜单树操作")
[![角色管理](http://chuantu.biz/t6/331/1529651099x-1404817533.png "角色管理")](http://chuantu.biz/t6/331/1529651099x-1404817533.png "角色管理")
[![角色关联权限](http://chuantu.biz/t6/331/1529651144x-1404817533.png "角色关联权限")](http://chuantu.biz/t6/331/1529651144x-1404817533.png "角色关联权限")
[![用户组管理](http://chuantu.biz/t6/331/1529651196x-1404817533.png "用户组管理")](http://chuantu.biz/t6/331/1529651196x-1404817533.png "用户组管理")
[![资源管理](http://chuantu.biz/t6/331/1529651262x-1404817533.png "资源管理")](http://chuantu.biz/t6/331/1529651262x-1404817533.png "资源管理")
[![权限管理](http://chuantu.biz/t6/331/1529651300x-1404817533.png "权限管理")](http://chuantu.biz/t6/331/1529651300x-1404817533.png "权限管理")
[![权限关联资源](https://s22.postimg.cc/sb0f7ikk1/permission_Resource.png "权限关联资源")](https://s22.postimg.cc/sb0f7ikk1/permission_Resource.png "权限关联资源")

