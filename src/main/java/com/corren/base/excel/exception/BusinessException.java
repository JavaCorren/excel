package com.corren.base.excel.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZhangGR
 * created on 2020/2/16
 * @description
 **/
@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private int code;
    private String message = "success";
}
