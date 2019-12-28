package com.corren.base.excel.model;

import lombok.Data;

/**
 * @author ZhangGR
 * created on 2019/12/28
 * @description
 **/
@Data
public class UserClassSave {

    private Integer code;
    private String msg;

    private Boolean updateFlag;

    private Long classId;
    private Long transferIntoClassId;
}
