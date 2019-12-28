package com.corren.base.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author ZhangGR
 * created on 2019/12/28
 * @description
 **/
@Data
public class UserInfo {

    @ExcelProperty(index = 0)
    private Long originClassId;

    @ExcelProperty(index = 1)
    private Long userId;

    @ExcelProperty(index = 2)
    private Long targetClassId;

    @ExcelProperty(index = 3)
    private String success;

    @ExcelProperty(index = 4)
    private String message;
}
