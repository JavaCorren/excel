package com.corren.base.excel.service;

import com.corren.base.excel.User;
import com.corren.base.excel.model.UserClassSave;
import com.corren.base.excel.retrofit.ClassCenterClient;
import com.google.common.collect.Lists;
import com.hetao101.common.api.Resp;
import com.hetao101.retrofit.utils.RetrofitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;

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

    public Resp transfer(Long userId, Long originClassId, Long targetClassId) {
        SaveUserClassRequest saveUserClassRequest = new SaveUserClassRequest();
        User user = new User();
        user.setUserId(userId);
        ArrayList<User> users = Lists.newArrayList(user);
        saveUserClassRequest.setUsers(users);
        saveUserClassRequest.setBusinessType(1);
        Call<Resp<List<UserClassSave>>> call = classCenterClient.batchTransfer(originClassId, targetClassId, operatorUserId, saveUserClassRequest);
        Resp<List<UserClassSave>> response = RetrofitUtil.getResponse(call);
        return response;
    }
}
