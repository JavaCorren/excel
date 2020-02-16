package com.corren.base.excel.service;

import com.alibaba.excel.EasyExcel;
import com.corren.base.excel.listener.UserClass;
import com.corren.base.excel.listener.UserClassListener;
import com.corren.base.excel.model.UserImmigration;
import com.corren.base.excel.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author ZhangGR
 * created on 2019/12/28
 * @description
 **/
@Service
public class ExcelReaderService {

    private static final String DIR_PATH = "/Users/zhanggr/Desktop/";
    private static final String FILE_NAME = "query-hive-57008.xlsx";
    private static final String FILE_NAME_NEW = "query-hive-57008-processed.xlsx";

    @Autowired
    private UserClassListener userClassListener;

    public void exec() throws IOException {
        String filePath = DIR_PATH + FILE_NAME;
        File file = new File(filePath);

        // 读取班级映射关系
        EasyExcel.read(file, UserClass.class, userClassListener).sheet(0).doRead();

        // 写入调班结果
        File writeFile = new File(DIR_PATH + FILE_NAME_NEW);
        if (!writeFile.exists()) {
            writeFile.createNewFile();
        }
        List<UserClass> list = userClassListener.getList();
        EasyExcel.write(writeFile, UserClass.class).sheet(0).doWrite(list);
    }
}
