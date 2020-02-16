package com.corren.base.excel.retrofit;

import com.corren.base.excel.model.ClassResponseData;
import com.corren.base.excel.model.UserClassSave;
import com.corren.base.excel.service.SaveUserClassRequest;
import com.hetao101.common.api.Resp;
import com.hetao101.retrofit.boot.annotation.RetrofitService;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * @author ZhangGR
 * created on 2019/12/28
 * @description
 **/
@RetrofitService("crm-classcenter")
public interface ClassCenterClient {

    @PUT("/crm-api/v1/userclass/classes/from/{fromClassId}/to/{toClassId}")
    Call<Resp<List<UserClassSave>>> batchTransfer(@Path("fromClassId") Long fromClassId, @Path("toClassId") Long toClassId, @Header("Operator-UserId") Long operatorUserId, @Body SaveUserClassRequest request);

    @GET("/crmclasscenter/v1/classes/{classId}")
    Call<Resp<ClassResponseData>> getClassById(@Path("classId") Long classId);

    /**
     * 获取人员班级信息
     * @param userId 用户id
     * @return
     */
    @GET("/crmclasscenter/v1/userclass/users/{userId}")
    Call<Resp<ClassResponseData>> getUserClassInfo(@Path("userId") Long userId);

}
