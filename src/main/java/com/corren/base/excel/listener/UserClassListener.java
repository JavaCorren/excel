package com.corren.base.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.corren.base.excel.model.ClassInfo;
import com.corren.base.excel.model.ClassResponseData;
import com.corren.base.excel.model.UserOrderInfo;
import com.corren.base.excel.model.UserOrderResponse;
import com.corren.base.excel.retrofit.ClassCenterClient;
import com.corren.base.excel.retrofit.OrderCenterClient;
import com.hetao101.common.api.Resp;
import com.hetao101.retrofit.utils.RetrofitUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ZhangGR
 * created on 2020/2/12
 * @description
 **/
@Component
@Data
@Slf4j
public class UserClassListener implements ReadListener<UserClass>{

    private List<UserClass> list;

    @Autowired
    private OrderCenterClient orderCenterClient;

    @Autowired
    private ClassCenterClient classCenterClient;

    private static String SQL_TEMPLATE = "INSERT INTO class_user_audit " +
            "(class_id, class_name, user_id, operator_uid, operator_name, " +
            "operation_type, operation_detail, ctime, class_course_group, " +
            "business_type, order_number, remark) VALUES (" +
            "%s,'%s',%s,%s,'%s',%s,'%s','%s',%s,%s,'%s','%s'); ";

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
        }

    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        if (list == null) {
            list = new ArrayList<>();
        }
    }

    @Override
    public void invoke(UserClass data, AnalysisContext context) {
        Long userId = data.getUserId();
        if (null == userId) {
            log.info("userId为空");
            return;
        }

        Long classId = data.getClassId();
        if (null == classId) {
            log.info("classId为空");
            return;
        }

        Call<Resp<ClassResponseData>> call = classCenterClient.getClassById(classId);
        Resp<ClassResponseData> classResponse = RetrofitUtil.getResponse(call);
        ClassResponseData classResponseData = classResponse.getData();
        List<ClassInfo> classes = classResponseData.getClasses();
        ClassInfo target = null;
        if (CollectionUtils.isEmpty(classes)) {
            log.info("class信息为空");
            return;
        }

        target = classes.get(0);

        Call<Resp<UserOrderResponse>> orderInfoByUserId = orderCenterClient.getOrderInfoByUserId(userId);
        Resp<UserOrderResponse> response = RetrofitUtil.getResponse(orderInfoByUserId);
        UserOrderResponse responseData = response.getData();
        List<UserOrderInfo> userOrderInfoList = responseData.getList();
        if (CollectionUtils.isEmpty(userOrderInfoList)) {
            log.info("订单信息为空");
        }

        ClassInfo finalTarget = target;
        Optional<UserOrderInfo> any = userOrderInfoList.stream().filter(s -> s.getCoursePackageId().equals(finalTarget.getCoursePackageId())).findAny();
        if (any.isPresent()) {
            data.setOrderNo(any.get().getOrderNumber());
        }

        String sql = generateSql(data, target);
        data.setSql(sql);

        this.list.add(data);
    }

    private String generateSql (UserClass userClass, ClassInfo classInfo) {

        StringBuffer stringBuffer = new StringBuffer();

        Long classId = userClass.getClassId();
        String className = classInfo.getClassName();
        Long userId = userClass.getUserId();
        Long operatorUserId = 0L;
        String operatorName = "系统后台";
        Integer operationType = null;
        String operationDetail = "订单号长度增加后订单号字段无法容纳报错-数据补偿";
        String ctime = "";
        Integer classCourseGroup = classInfo.getCourseGroup();
        Integer businessType = 2;
        String orderNumber = userClass.getOrderNo();
        String remark = "订单号长度增加后订单号字段无法容纳报错-数据补偿";

        Integer status = userClass.getStatus();
        if (status.equals(1)) {
            operationType = 11;
            ctime = userClass.getCtime();

            if (StringUtils.isEmpty(orderNumber)) {
                businessType = 1;
                operatorUserId = 3000088L;
                operatorName = "张国瑞-数据补偿";
                orderNumber = "";
                ctime = userClass.getCtime();
            }

            stringBuffer.append(String.format(SQL_TEMPLATE, classId, className, userId, operatorUserId, operatorName, operationType, operationDetail, ctime, classCourseGroup, businessType, orderNumber, remark));
        } else {

            operationType = 11;

            if (StringUtils.isEmpty(orderNumber)) {
                businessType = 1;
                operatorUserId = 3000088L;
                operatorName = "张国瑞-数据补偿";
                orderNumber = "";
                ctime = userClass.getCtime();
            } else {
                businessType = 2;
                operatorUserId = 0L;
                operatorName = "系统后台";
                ctime = userClass.getCtime();
            }

            stringBuffer.append(String.format(SQL_TEMPLATE, classId, className, userId, operatorUserId, operatorName, operationType, operationDetail, ctime, classCourseGroup, businessType, orderNumber, remark));

            operationType = 12;
            businessType = 1;
            operatorUserId = 3000088L;
            operatorName = "张国瑞-数据补偿";
            ctime = userClass.getUtime();
            stringBuffer.append(String.format(SQL_TEMPLATE, classId, className, userId, operatorUserId, operatorName, operationType, operationDetail, ctime, classCourseGroup, businessType, orderNumber, remark));

        }

        return stringBuffer.toString();
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("扫描完毕");
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        return true;
    }

}
