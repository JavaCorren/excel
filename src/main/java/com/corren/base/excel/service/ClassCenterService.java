package com.corren.base.excel.service;

import com.corren.base.excel.model.ClassInfo;
import com.corren.base.excel.model.ClassResponseData;
import com.corren.base.excel.retrofit.ClassCenterClient;
import com.hetao101.common.api.Resp;
import com.hetao101.retrofit.utils.RetrofitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import retrofit2.Call;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * @author ZhangGR
 * created on 2019/12/28
 * @description
 **/
@Service
public class ClassCenterService {

    @Autowired
    private ClassCenterClient classCenterClient;

    @Value("${operator.userId}")
    private Long operatorUserId;

    public Map<Long, ClassInfo> getClassInfoMap(Long userId) {
        Call<Resp<ClassResponseData>> call = classCenterClient.getUserClassInfo(userId);
        Resp<ClassResponseData> response = RetrofitUtil.getResponse(call);
        ClassResponseData data = response.getData();
        List<ClassInfo> classes = data.getClasses();
        if (CollectionUtils.isEmpty(classes)) {
            return null;
        }
        return classes.stream().collect(toMap(ClassInfo::getClassId, s -> s));
    }
}
