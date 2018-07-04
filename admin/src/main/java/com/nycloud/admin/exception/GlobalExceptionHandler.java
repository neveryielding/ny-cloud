package com.nycloud.admin.exception;

import com.nycloud.common.vo.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/6/4 0004
 * @modified By:
 * @version: 1.0
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public HttpResponse processException(Exception ex, HttpServletRequest request, HttpServletResponse response) {

        if (ex instanceof MissingServletRequestParameterException) {
            return HttpResponse.resultError(500, ex.getMessage());
        }

        if (ex instanceof DuplicateKeyException) {
            LOGGER.error("=======违反主键约束：主键重复插入=======");
            return HttpResponse.resultError(400, "主键重复插入！");
        }

        /**
         * 未知异常
         */
        LOGGER.error(ex.toString());
        return HttpResponse.resultError(500, ex.getMessage());
    }

}
