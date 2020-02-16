package com.corren.base.excel.controller;

import com.corren.base.excel.service.ExcelReaderService;
import com.corren.base.excel.service.OrderNoImportService;
import com.hetao101.common.api.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private OrderNoImportService orderNoImportService;

    @RequestMapping("/orderNo/from/{fileName}")
    public Resp orderNoImport(@PathVariable String fileName) throws IOException {

        orderNoImportService.execute(fileName);
        return Resp.success();
    }
}
