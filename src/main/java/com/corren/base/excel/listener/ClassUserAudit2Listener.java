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
public class ClassUserAudit2Listener implements ReadListener<ClassUserAudit2> {

    private List<ClassUserAudit2> list;

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
    public void invoke(ClassUserAudit2 data, AnalysisContext context) {

        long start = System.currentTimeMillis();
        String orderNumber1 = data.getOrderNumber1();
        Long classId1 = data.getClassId1();

        String orderNumber2 = data.getOrderNumber2();
        Long classId2 = data.getClassId2();

        Long userId = data.getUserId();

        List<String> validate1 = classUserAuditCommonService.validate(orderNumber1, classId1, userId);
        List<String> validate2 = classUserAuditCommonService.validate(orderNumber2, classId2, userId);
        data.setMessage1(validate1.get(0)); data.setSql1(validate1.get(1));
        data.setMessage2(validate2.get(0)); data.setSql2(validate2.get(1));

        long end = System.currentTimeMillis();

        System.out.println("时间:"+(end -start)+"ms");

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
