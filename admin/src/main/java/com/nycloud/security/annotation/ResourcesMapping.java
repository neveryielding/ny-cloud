package com.nycloud.security.annotation;

import java.lang.annotation.*;

/**
 * @description: 此注解用于数据初始化用，主要是初始化前端页面元素和接口资源的整合，在初始化完成后，请将下面这一项修改成@Retention(RetentionPolicy.SOURCE)
 * @author: super.wu
 * @date: Created in 2018/5/30 0030
 * @modified By:
 * @version: 1.0
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ResourcesMapping {
    String element();
    String code();
}
