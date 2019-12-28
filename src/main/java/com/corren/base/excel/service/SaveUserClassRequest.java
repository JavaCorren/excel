package com.corren.base.excel.service;

import com.corren.base.excel.User;
import lombok.Data;

import java.util.List;

/**
 * @author ZhangGR
 * created on 2019/12/28
 * @description
 **/
@Data
public class SaveUserClassRequest {

    private List<User> users;
    private Integer businessType;
}
