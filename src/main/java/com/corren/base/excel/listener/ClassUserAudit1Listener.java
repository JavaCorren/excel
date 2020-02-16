package com.corren.base.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.corren.base.excel.service.ClassUserAuditCommonService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangGR
 * created on 2020/2/16
 * @description
 **/
@Data
@Component
@Slf4j
public class ClassUserAudit1Listener implements ReadListener<ClassUserAudit1> {

    private List<ClassUserAudit1> list;

    @Autowired
    private ClassUserAuditCommonService classUserAuditCommonService;

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        exception.printStackTrace();
    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        if (list == null) list = new ArrayList<>();
        if (!list.isEmpty()) list = new ArrayList<>();
    }

    @Override
    public void invoke(ClassUserAudit1 data, AnalysisContext context) {

        String orderNumber = data.getOrderNumber();
        Long classId = data.getClassId();
        Long userId = data.getUserId();

        final List<String> validate = classUserAuditCommonService.validate(orderNumber, classId, userId);
        data.setMessage(validate.get(0)); data.setSql(validate.get(1));

        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("scan finished");
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        return true;
    }
}
