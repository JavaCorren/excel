package com.corren.base.excel.exception;

import com.hetao101.common.api.Resp;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author ZhangGR
 * created on 2020/2/16
 * @description
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Resp handleBusinessException(BusinessException e) {
        e.printStackTrace();
        return Resp.error(e.getCode(), e.getMessage());
    }
}
