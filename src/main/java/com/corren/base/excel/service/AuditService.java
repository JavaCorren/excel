package com.corren.base.excel.service;

import com.corren.base.excel.model.ClassUserAuditVO;
import com.corren.base.excel.model.GetClassUserAuditResponse;
import com.corren.base.excel.retrofit.AuditFetchClient;
import com.hetao101.common.api.Resp;
import com.hetao101.retrofit.utils.RetrofitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangGR
 * created on 2020/2/16
 * @description
 **/
@Service
public class AuditService {

    @Autowired
    private AuditFetchClient auditFetchClient;

    public List<ClassUserAuditVO> getClassUserAuditVOListByOrderNo(String orderNo) {
        Map map = new HashMap<>();
        map.put("orderNo", orderNo);
        map.put("pageIndex", 1);
        map.put("pageSize", 100);
        Call<Resp<GetClassUserAuditResponse>> call = auditFetchClient.getClassUserAudit(map);
        Resp<GetClassUserAuditResponse> response = RetrofitUtil.getResponse(call);
        GetClassUserAuditResponse data = response.getData();
        return data.getAudits();
    }

    public List<ClassUserAuditVO> getClassUserAuditVOListByClassIdAndUserId(Long classId, Long userId) {
        Map map = new HashMap<>();
        map.put("classId", classId);
        map.put("userId", userId);
        map.put("pageIndex", 1);
        map.put("pageSize", 100);
        Call<Resp<GetClassUserAuditResponse>> call = auditFetchClient.getClassUserAudit(map);
        Resp<GetClassUserAuditResponse> response = RetrofitUtil.getResponse(call);
        GetClassUserAuditResponse data = response.getData();
        return data.getAudits();
    }
}
