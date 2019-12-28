package com.corren.base.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author ZhangGR
 * created on 2019/12/28
 * @description
 **/
@Data
public class UserImmigration {

    @ExcelProperty(index = 0)
    private Long originClassId;

    @ExcelProperty(index = 1)
    private Long targetClassId;
}
