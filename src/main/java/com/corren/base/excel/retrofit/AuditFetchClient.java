package com.corren.base.excel.retrofit;

import com.corren.base.excel.model.GetClassUserAuditResponse;
import com.hetao101.common.api.Resp;
import com.hetao101.retrofit.boot.annotation.RetrofitService;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import java.util.Map;

/**
 * @author ZhangGR
 * created on 2020/2/16
 * @description
 **/
@RetrofitService("crm-audit")
public interface AuditFetchClient {

    /**
     * 查询进出班日志
     * @param queryMap
     * @return
     */
    @GET("/crmaudit/v1/classuser/logging")
    Call<Resp<GetClassUserAuditResponse>> getClassUserAudit(@QueryMap Map<String,Object> queryMap);
}
