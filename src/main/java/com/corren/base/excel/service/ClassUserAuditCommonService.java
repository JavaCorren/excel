package com.corren.base.excel.service;

import com.corren.base.excel.model.ClassInfo;
import com.corren.base.excel.model.ClassUserAuditVO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * @author ZhangGR
 * created on 2020/2/16
 * @description
 **/
@Service
public class ClassUserAuditCommonService {

    private static final String SQL_TEMPLATE = "UPDATE class_user_audit SET order_number = '%s' " +
            "WHERE class_id = %s AND user_id = %s AND (order_number = '' OR order_number = 'NOT EXISTS');";

    @Autowired
    private AuditService auditService;

    @Autowired
    private ClassCenterService classCenterService;

    public List<String> validate (String orderNumber, Long classId, Long userId) {

        String message = "";
        String sql = "";

        if (StringUtils.isEmpty(orderNumber) || orderNumber.equalsIgnoreCase("null")) {
            message = "订单号不存在";
        } else {
            Map<Long, ClassInfo> classInfoMap = classCenterService.getClassInfoMap(userId);
            if (CollectionUtils.isEmpty(classInfoMap)) {
                message = "用户已不在班, 不作处理";
            } else {
                List<ClassUserAuditVO> classUserAuditVOListByOrderNo = auditService.getClassUserAuditVOListByOrderNo(orderNumber);
                if (!CollectionUtils.isEmpty(classUserAuditVOListByOrderNo)) {

                    Map<Integer, Map<Long, List<ClassUserAuditVO>>> collect = classUserAuditVOListByOrderNo.stream().collect(groupingBy(ClassUserAuditVO::getOperationType, groupingBy(ClassUserAuditVO::getClassId)));

                    if (collect.get(11).containsKey(classId)) {// 凭订单号查询得到的进班记录已有对应班级
                        message = "预刷在班和预刷订单已经关联上";
                    } else {
                        message = "订单号出现在其它在班中";
                    }
                } else {
                    List<ClassUserAuditVO> classUserAuditVOListByClassIdAndUserId = auditService.getClassUserAuditVOListByClassIdAndUserId(classId, userId);
                    if (CollectionUtils.isEmpty(classUserAuditVOListByClassIdAndUserId)) {
                        message = "未查到在班日志记录, 此日志不应该出现";
                    } else {
                        Optional<ClassUserAuditVO> any = classUserAuditVOListByClassIdAndUserId.stream().filter(s -> !StringUtils.isEmpty(s.getOrderNo()) && !s.getOrderNo().equals("NOT EXISTS")).findAny();
                        if (any.isPresent()) {
                            message = "在班中存在其它订单号";
                        }
                    }
                    if (StringUtils.isEmpty(message)) {
                        sql = String.format(SQL_TEMPLATE, orderNumber, classId, userId);
                    }
                }
            }
        }

        return Lists.newArrayList(message, sql);
    }
}
