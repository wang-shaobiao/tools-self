package org.example.service;

import org.apache.commons.collections4.CollectionUtils;

import org.example.common.ConfigUtils;
import org.example.common.ExcelUtils;
import org.example.common.GenerateSqlUtils;
import org.example.dao.QueryEntryDao;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Map;

import static org.example.common.CommonConstants.*;


/**
 * @description: generate sql script related
 * @author: wshbiao
 * @create: 2022-12-08
 **/

public class GenerateSqlService {

    /** 
     * @Description 执行生成sql脚本操作
     * @Param: [strings] 
     * @return: void 
     * @throws: 
     */
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

        List<Map<String,Object>> excelList = null;
        if (excelPath == null || excelPath.isEmpty()) {
            excelList = ExcelUtils.doGetExcelInfo(ExcelRelated.colDef);
        } else {
            //本地调用，自定义项目路径
            excelList = ExcelUtils.doGetExcelInfo(ExcelRelated.colDef, excelPath);
        }

        if (CollectionUtils.isEmpty(excelList)) {
            System.out.println("excel不存在或未定义抽取条件，请检查");
            return;
        }
        /**
         * 2. 执行词条信息查询
         */
        QueryEntryDao queryEntryDao = null;
        Path desPath = null;
        if (proPath == null || proPath.isEmpty()) {
            queryEntryDao = new QueryEntryDao();
        } else {
            //本地调用，自定义项目路径
            queryEntryDao = new QueryEntryDao(proPath);
        }
        if (sqlPath == null || sqlPath.isEmpty()) {
            desPath = ConfigUtils.getDesPath(GenerateRelated.GENERATE_TYPE_SQL);
        } else {
            desPath = ConfigUtils.getDesPath(GenerateRelated.GENERATE_TYPE_SQL, sqlPath);
        }

        if (!Files.exists(desPath)) {
            Files.createDirectories(desPath);
        }
        int i = 0;
        for (Map map : excelList) {
            String tableName = (String)map.get(ExcelRelated.colDef[0]);
            String nameColumn = (String)map.get(ExcelRelated.colDef[1]);
            String residColumn = (String)map.get(ExcelRelated.colDef[2]);
            String condition = (String)map.get(ExcelRelated.colDef[3]);
            String note = (String) map.get(ExcelRelated.colDef[4]);
            List<Map<String, Object>> entryList = queryEntryDao.getEntry(tableName, nameColumn, residColumn, condition);
            //无值跳过
            if (CollectionUtils.isEmpty(entryList)) {
                continue;
            }
            String fileName = GenerateSqlUtils.getGenerateFileName(tableName, nameColumn);
            Path filePath = Paths.get(desPath.toRealPath().toString(),fileName);
            Boolean flag = GenerateSqlUtils.generateSqlFile(filePath, entryList, tableName, nameColumn, residColumn,condition,note);
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
