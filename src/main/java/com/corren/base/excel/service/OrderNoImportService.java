package com.corren.base.excel.service;

import com.alibaba.excel.EasyExcel;
import com.corren.base.excel.exception.BusinessException;
import com.corren.base.excel.listener.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author ZhangGR
 * created on 2020/2/16
 * @description
 **/
@Service
public class OrderNoImportService {

    private static final String DIR_PATH = "/home/zhangguorui/data1/";
    private static final String WRITE_FILE_NAME_SUFFIX = "-processed";
    private static final String FILE_SUFFIX = ".xlsx";

    @Autowired
    private ClassUserAudit1Listener classUserAudit1Listener;

    @Autowired
    private ClassUserAudit2Listener classUserAudit2Listener;

    public void execute(String fileName) throws IOException {

        // 检查文件名
        int fileType = checkFileName(fileName);
        // 文件路径
        String filePath = DIR_PATH + fileName + FILE_SUFFIX;
        // 写入文件名
        String writeFileName = DIR_PATH + fileName + WRITE_FILE_NAME_SUFFIX + FILE_SUFFIX;
        // 创建文件对象
        File file = new File(filePath);

        // 执行数据处理
        switch (fileType) {
            case 1:
                EasyExcel.read(file, ClassUserAudit1.class, classUserAudit1Listener).sheet(0).doRead();
                break;
            case 2:
                EasyExcel.read(file, ClassUserAudit2.class, classUserAudit2Listener).sheet(0).doRead();
                break;
        }


        // 数据处理结果落盘
        File writeFile = new File(writeFileName);
        if (!writeFile.exists()) {
            writeFile.createNewFile();
        }

        switch (fileType) {
            case 1:
                List<ClassUserAudit1> list1 = classUserAudit1Listener.getList();
                EasyExcel.write(writeFile, ClassUserAudit1.class).sheet(0).doWrite(list1);
                break;
            case 2:
                List<ClassUserAudit2> list2 = classUserAudit2Listener.getList();
                EasyExcel.write(writeFile, ClassUserAudit2.class).sheet(0).doWrite(list2);
                break;
        }


    }

    /**
     * 文件名称检查
     * @param fileName
     */
    private int checkFileName(String fileName) {

        String[] splits = fileName.split("D");
        int length = splits.length;
        if (length == 0) {
            throw new BusinessException(10001, "the file name should not be blank");
        }

        String mark = splits[length - 1];
        int i = Integer.parseInt(mark);

        if (i < 1 || i > 2) {
            throw new BusinessException(10002, "illegal file format!");
        }
        return i;
    }

}
