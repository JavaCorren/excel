package com.corren.base.excel;

import com.hetao101.retrofit.boot.RetrofitServiceScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@RetrofitServiceScan
@ServletComponentScan("com.hetao101.common")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ExcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelApplication.class, args);
    }

}
