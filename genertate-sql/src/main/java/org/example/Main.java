package org.example;

import org.example.common.CommonConstants;
import org.example.dao.QueryEntryDao;
import org.example.service.GenerateSqlService;
import org.example.service.ReadExcelService;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.example.common.CommonConstants.*;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String proPath = Paths.get(Main.class.getClassLoader().getResource(PROPERTIES_NAME).toURI()).toString();
        String excelPath = Paths.get(Main.class.getClassLoader().getResource(EXCEL_TYPE).toURI()).toString();
        System.out.println(proPath);
        System.out.println(excelPath);
        /**
         * 1. 读取查询信息
         */
        //定义列信息
        String[] colDef = {"tableName","nameColumn","residColumn","condition"};
        ReadExcelService readExcelService = new ReadExcelService();
        //TODO excel正式
        //List<Map<String,Object>> excelList = readExcelService.doGetExcelInfo(columnDefines);
        //本地调用，自定义项目路径
        List<Map<String,Object>> excelList = readExcelService.doGetExcelInfo(colDef, excelPath);

        /**
         * 2. 执行词条信息查询
         */
        //TODO query正式
        //QueryEntryDao queryEntryDao = new QueryEntryDao();
        //本地调用，自定义项目路径
        QueryEntryDao queryEntryDao = new QueryEntryDao(proPath);
        for (Map map : excelList) {
            String tableName = (String)map.get(colDef[0]);
            String nameColumn = (String)map.get(colDef[1]);
            String residColumn = (String)map.get(colDef[2]);
            String condition = (String)map.get(colDef[3]);
            List<Map<String,Object>> list = queryEntryDao.getEntry(tableName, nameColumn, residColumn,condition);
        }


        /**
         * TODO 3. 生成Update脚本
         */

        GenerateSqlService generateSql = new GenerateSqlService();
        System.out.println("完成");







    }
}