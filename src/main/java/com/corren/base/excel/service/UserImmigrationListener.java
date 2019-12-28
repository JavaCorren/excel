package com.corren.base.excel.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.corren.base.excel.model.UserImmigration;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangGR
 * created on 2019/12/28
 * @description
 **/
@Component
@Data
@Slf4j
public class UserImmigrationListener implements ReadListener<UserImmigration> {

    private Map<Long,Long> targetClassMap = null;

    @Override
    public void onException(Exception e, AnalysisContext analysisContext) throws Exception {
        e.printStackTrace();
    }

    @Override
    public void invokeHead(Map<Integer, CellData> map, AnalysisContext analysisContext) {

    }

    @Override
    public void invoke(UserImmigration userImmigration, AnalysisContext analysisContext) {
        if (CollectionUtils.isEmpty(targetClassMap)) {
            targetClassMap = new HashMap<>();
        }

        targetClassMap.put(userImmigration.getOriginClassId(), userImmigration.getTargetClassId());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("映射关系读取完毕");
    }

    @Override
    public boolean hasNext(AnalysisContext analysisContext) {
        return true;
    }
}
