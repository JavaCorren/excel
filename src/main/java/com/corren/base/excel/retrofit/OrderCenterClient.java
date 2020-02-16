package com.corren.base.excel.retrofit;

import com.corren.base.excel.model.UserOrderResponse;
import com.hetao101.common.api.Resp;
import com.hetao101.retrofit.boot.annotation.RetrofitService;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author ZhangGR
 * created on 2019/12/28
 * @description
 **/
@RetrofitService("order-center")
public interface OrderCenterClient {

    @GET("/order-center/v1/order?pageNum=1&pageSize=100&status=PAID")
    Call<Resp<UserOrderResponse>> getOrderInfoByUserId(@Query("userId")Long userId);

}
