package org.example;

import org.example.service.GenerateSqlService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.example.common.CommonConstants.*;

public class Main {
    public static void main(String[] args) throws Exception {
        //项目内自定义
        String proPath = null;
        String excelPath = null;
        String sqlPath = null;
        {
            proPath = Paths.get(Main.class.getClassLoader().getResource(PropertiesRelated.PROPERTIES_NAME).toURI()).toString();
            excelPath = Paths.get(Main.class.getClassLoader().getResource(ExcelRelated.EXCEL_NAME).toURI()).toString();
            Path sPath = Paths.get(Default.DES_DEFAULT);
            if (!Files.exists(sPath)) {
                Files.createDirectories(sPath);
            }
            sqlPath = sPath.toRealPath().toString();
            System.out.println("proPath:"+proPath);
            System.out.println("excelPath:"+excelPath);
            System.out.println("sqlPath:"+sqlPath);

        }

        String[] paths = new String[]{proPath, excelPath, sqlPath};
        //生成sql
        GenerateSqlService generateSqlService = new GenerateSqlService();
        generateSqlService.doGenerateSql(paths);

    }
}