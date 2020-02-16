package com.corren.base.excel.model;

import lombok.Data;

import java.util.List;

/**
 * @author ZhangGR
 * created on 2020/2/12
 * @description
 **/
@Data
public class UserOrderResponse {
    private Integer pageNum;
    private Integer pageSize;
    private Integer total;
    private List<UserOrderInfo> list;
}
