package org.example.service;

import org.example.Main;
import org.example.common.ConfigUtils;
import org.example.common.ExcelUtils;
import org.example.common.GenerateSqlUtils;
import org.example.dao.QueryEntryDao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.example.common.CommonConstants.*;
import static org.example.common.CommonConstants.GENERATE_TYPE_SQL;

/**
 * @description: generate sql script related
 * @author: wshbiao
 * @create: 2022-12-08
 **/

public class GenerateSqlService {

    public void doGenerateSql(String...strings) throws Exception {
        String excelPath = null;
        String proPath = null;
        String sqlPath = null;
        if (strings != null && strings.length != 0) {
            proPath = strings[0];
            excelPath = strings[1];
            sqlPath = strings[2];
        }
        /**
         * 1. 读取查询信息
         */
        //定义列信息
        String[] colDef = {"tableName","nameColumn","residColumn","condition"};
        //TODO excel正式
        //List<Map<String,Object>> excelList = readExcelService.doGetExcelInfo(columnDefines);
        //本地调用，自定义项目路径
        List<Map<String,Object>> excelList = ExcelUtils.doGetExcelInfo(colDef, excelPath);

        /**
         * 2. 执行词条信息查询
         */
        //TODO query正式
        //QueryEntryDao queryEntryDao = new QueryEntryDao();
        //本地调用，自定义项目路径
        QueryEntryDao queryEntryDao = new QueryEntryDao(proPath);
        Path desPath = ConfigUtils.getDesPath(GENERATE_TYPE_SQL, sqlPath);
        if (!Files.exists(desPath)) {
            Files.createDirectories(desPath);
        }
        int i = 0;
        for (Map map : excelList) {
            String tableName = (String)map.get(colDef[0]);
            String nameColumn = (String)map.get(colDef[1]);
            String residColumn = (String)map.get(colDef[2]);
            String condition = (String)map.get(colDef[3]);
            List<Map<String,Object>> entryList = queryEntryDao.getEntry(tableName, nameColumn, residColumn,condition);
            String fileName = FILE_PREFIX + "-" + tableName + "-" + LocalDate.now() + ".sql";
            Path filePath = Paths.get(desPath.toRealPath().toString(),fileName);
            /**
             * TODO 3. 生成Update脚本
             */

            Boolean flag = GenerateSqlUtils.generateSqlFile(filePath, entryList, tableName, nameColumn, residColumn);
            if (!flag) {
                System.out.printf("Sql脚本生成失败,%s",tableName);
                return ;
            }
            i++;
            System.out.println(i+". Sql脚本生成："+filePath.toRealPath());

        }
        System.out.println("sum: " + i);
    }

}
