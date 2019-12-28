package com.corren.base.excel.service;

import com.alibaba.excel.EasyExcel;
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
    private static final String FILE_NAME = "班级信息.xlsx";
    private static final String FILE_NAME_NEW = "批量调班结果.xlsx";

    @Autowired
    private UserInfoListener userInfoListener;

    public void batchTransferFromExcel() throws IOException {
        String filePath = DIR_PATH + FILE_NAME;
        File file = new File(filePath);

        // 读取班级映射关系
        UserImmigrationListener userImmigrationListener = new UserImmigrationListener();
        EasyExcel.read(file, UserImmigration.class, userImmigrationListener).sheet("map").doRead();

        // 读取所有用户信息
        userInfoListener.setClassInfoMap(userImmigrationListener.getTargetClassMap());
        EasyExcel.read(file, UserInfo.class, userInfoListener).sheet("全部").doRead();

        // 写入调班结果
        File writeFile = new File(DIR_PATH + FILE_NAME_NEW);
        if (!writeFile.exists()) {
            writeFile.createNewFile();
        }
        List<UserInfo> data = userInfoListener.getUserInfoList();
        EasyExcel.write(writeFile, UserInfo.class).sheet(1).doWrite(data);
    }
}
