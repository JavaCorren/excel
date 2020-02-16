package com.corren.base.excel.model;

import lombok.Data;

/**
 * @author ZhangGR
 * created on 2020/2/16
 * @description
 **/
@Data
public class ClassUserAuditVO {

    private Long classId;
    private String className;
    private Integer classCourseGroup;
    private Long userId;
    private Long operatorUid;
    private String operatorName;
    private Integer operationType;
    private String operationDetail;
    private Integer businessType;
    private String orderNo;
    private Integer userSource;
    private String thirdPartyOrderNo;
    private Long ctime;
}
