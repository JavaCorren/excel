package com.corren.base.excel.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import com.corren.base.excel.model.UserClassSave;
import com.corren.base.excel.model.UserInfo;
import com.hetao101.common.api.Resp;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangGR
 * created on 2019/12/28
 * @description
 **/
@Slf4j
@Data
@Component
public class UserInfoListener implements ReadListener<UserInfo>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private List<UserInfo> userInfoList;
    private Map<Long,Long> classInfoMap;
    private Long start = null;
    private Long end = null;

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {

    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        start = System.currentTimeMillis();
    }

    @Override
    public void invoke(UserInfo data, AnalysisContext context) {
        if (CollectionUtils.isEmpty(classInfoMap)) {
            log.info("班级映射关系为空, 不作任何处理");
            return;
        }

        Long originClassId = data.getOriginClassId();
        if (null == originClassId) {
            log.info(String.format("用户%s的班级信息为空, 不作任何处理", data.getUserId()));
            return;
        }
        Long targetClassId = classInfoMap.get(originClassId);
        data.setTargetClassId(targetClassId);
        if (CollectionUtils.isEmpty(userInfoList)) {
            userInfoList = new ArrayList<>();
        }

        log.info("------------start------------");
        ClassCenterService classCenterService = applicationContext.getBean(ClassCenterService.class);
        Resp transfer = classCenterService.transfer(data.getUserId(), data.getOriginClassId(), data.getTargetClassId());
        if (transfer == null) {
            data.setSuccess("N");
            data.setMessage(String.format("调班失败: CRM-API服务异常"));
        } else if (transfer.getErrCode() != 0) {
            data.setSuccess("N");
            data.setMessage(String.format("调班失败: %s", transfer.getErrMsg()));
        } else {
            data.setSuccess("Y");
            data.setMessage(String.format("调班成功"));
        }
        log.info("------------end------------");

        userInfoList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        end = System.currentTimeMillis();
        log.info(String.format("结果信息: %s", JSON.toJSON(userInfoList)));
        log.info(String.format("耗时: %s", end - start));
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
