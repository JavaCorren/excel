package com.corren.base.excel.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ZhangGR
 * created on 2020/2/12
 * @description
 **/
@ApiModel("班级数据")
@Getter
@Setter
@NoArgsConstructor
public class ClassInfo {

    @ApiModelProperty("班级id")
    private Long classId;

    @ApiModelProperty("班级名称")
    private String className;

    @ApiModelProperty("班级类型")
    private Integer classType;

    @ApiModelProperty("课程包类型")
    private Integer courseGroup;

    //班主任信息
    @ApiModelProperty("班主任id")
    private Long counselorId;

    //班级课程版本
    @ApiModelProperty("课程包id")
    private Long coursePackageId;

    //学期id
    @ApiModelProperty("学期id")
    private Long termId;

    @ApiModelProperty("学期名称")
    private String termName;

    @ApiModelProperty("课程版本")
    private Integer courseVersion;

    @ApiModelProperty("班级开课时间")
    private Long classopenTime;

    @ApiModelProperty("班级状态")
    private String status;
}
