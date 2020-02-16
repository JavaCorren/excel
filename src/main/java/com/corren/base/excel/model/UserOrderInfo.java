package com.corren.base.excel.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ZhangGR
 * created on 2020/2/12
 * @description
 **/
@Data
public class UserOrderInfo {

    // 订单id
    @ApiModelProperty(name = "id", value = "订单id")
    private Long id;

    // 订单号
    @ApiModelProperty(name = "orderNumber", value = "订单号")
    private String orderNumber;

    // 订单用户
    @ApiModelProperty(name = "userId", value = "用户id")
    private Long userId;

    // 订单对应课程包id
    @ApiModelProperty(name = "coursePackageId", value = "订单对应课程包id")
    private Long coursePackageId;

    // 订单实付金额,单位是分
    @ApiModelProperty(name = "amount", value = "订单金额")
    private Long amount;

    // 订单原金额
    @ApiModelProperty(name = "originalAmount", value = "订单原金额")
    private Long originalAmount;

    //已经退费的金额,单位是分
    @ApiModelProperty(name = "refundAmount", value = "已经退费的金额")
    private Integer refundAmount;

    //有效金额=实付金额-已经退费的金额,单位是分
    @ApiModelProperty(name = "availableAmount", value = "有效金额")
    private Integer availableAmount;

    // 订单状态
    @ApiModelProperty(name = "orderNumber", value = "订单状态")
    private String status;

    // 订单时间
    @ApiModelProperty(name = "payTime", value = "订单id")
    private Long payTime;

    // 购买来源
    @ApiModelProperty(name = "channelName", value = "购买来源")
    private String marketChannel;

    //订单支付的渠道，如：微信-长泽，微信-聪明核桃，支付宝等
    @ApiModelProperty(name = "merchant", value = "订单支付的渠道，如：微信-长泽，微信-聪明核桃，支付宝等")
    private String merchant;

    @ApiModelProperty(name = "payChannel", value = "payChannel")
    private String payChannel;

    //电话号码
    @ApiModelProperty(name = "phoneNumber", value = "电话号码")
    private String phoneNumber;

    //购买来源id
    @ApiModelProperty(name = "activityId", value = "购买来源id")
    private Long activityId;

    //购买来源
    @ApiModelProperty(name = "internalName", value = "购买来源")
    private String internalName;
}
