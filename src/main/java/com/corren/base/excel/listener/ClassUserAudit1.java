package com.corren.base.excel.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author ZhangGR
 * created on 2020/2/16
 * @description
 **/
@Data
public class ClassUserAudit1 {

    @ExcelProperty(index = 1)
    private Long userId;

    @ExcelProperty(index = 3)
    private String orderNumber;

    @ExcelProperty(index = 7)
    private Long classId;

    @ExcelProperty(index = 12)
    private String message;

    @ExcelProperty(index = 15)
    private String sql;
}
