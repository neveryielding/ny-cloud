package com.nycloud.common.vo;

import com.nycloud.common.constants.HttpConstant;
import lombok.*;

/**
 * @description: 通用Http返回模型
 * @author: super.wu
 * @date: Created in 2018/4/20 0020
 * @modified By:
 * @version: 1.0
 **/
@Data
public class HttpResponse <T> {

    /** 错误码 **/
    private int code;
    /** 提示消息文本 **/
    private String msg;
    /** 返回数据 **/
    private T data;

    public HttpResponse success() {
        this.code = HttpConstant.RESULT_SUCCESS;
        this.msg = HttpConstant.SUCCESS;
        return this;
    }

    public HttpResponse success(T data) {
        this.data = data;
        return success();
    }

    public HttpResponse error(int code) {
        return this.error(code, null, null);
    }

    public HttpResponse error(int code, String msg) {
        return this.error(code, msg, null);
    }

    public HttpResponse error(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public static HttpResponse errorParams() {
        return new HttpResponse().error(HttpConstant.PARAM_ERROR, HttpConstant.PARAM_ERROR_TEXT);
    }

    public static HttpResponse resultError(int code, String msg) {
        return new HttpResponse().error(code, msg);
    }

    public static HttpResponse resultSuccess() {
        return new HttpResponse().success();
    }

    public static HttpResponse resultSuccess(Object data) {
        return new HttpResponse().success(data);
    }


}

