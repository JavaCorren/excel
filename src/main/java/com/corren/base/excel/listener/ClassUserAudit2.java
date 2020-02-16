package com.corren.base.excel.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author ZhangGR
 * created on 2020/2/16
 * @description
 **/
@Data
public class ClassUserAudit2 {

    @ExcelProperty(index = 5)
    private Long classId1;

    @ExcelProperty(index = 1)
    private Long userId;

    @ExcelProperty(index = 12)
    private Long classId2;

    @ExcelProperty(index = 2)
    private String orderNumber1;

    @ExcelProperty(index = 9)
    private String orderNumber2;

    @ExcelProperty(index = 20)
    private String message1;
    @ExcelProperty(index = 22)
    private String sql1;
    @ExcelProperty(index = 21)
    private String message2;
    @ExcelProperty(index = 23)
    private String sql2;
}
