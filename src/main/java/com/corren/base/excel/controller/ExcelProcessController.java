package com.corren.base.excel.controller;

import com.corren.base.excel.service.ExcelReaderService;
import com.hetao101.common.api.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author ZhangGR
 * created on 2019/12/28
 * @description
 **/
@RequestMapping("/excel")
@RestController
public class ExcelProcessController {

    @Autowired
    private ExcelReaderService excelReaderService;

    @RequestMapping("/process")
    public Resp process() throws IOException {
        try {
            excelReaderService.batchTransferFromExcel();
            return Resp.success();
        } catch (Exception e) {
            return Resp.error(50000, e.getMessage());
        }

    }
}
