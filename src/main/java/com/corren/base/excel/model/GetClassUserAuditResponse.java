package com.corren.base.excel.model;

import lombok.Data;

import java.util.List;

/**
 * @author ZhangGR
 * created on 2020/2/16
 * @description
 **/
@Data
public class GetClassUserAuditResponse {

    private List<ClassUserAuditVO> audits;
}
