package com.corren.base.excel.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author ZhangGR
 * created on 2020/2/12
 * @description
 **/
@Data
public class UserClass {

    @ExcelProperty(index = 0)
    private Long userId;

    @ExcelProperty(index = 1)
    private Long classId;

    @ExcelProperty(index = 2)
    private Integer status;

    @ExcelProperty(index = 3)
    private String ctime;

    @ExcelProperty(index = 4)
    private String utime;

    @ExcelProperty(index = 5)
    private String orderNo;

    @ExcelProperty(index = 6)
    private String sql;
}
