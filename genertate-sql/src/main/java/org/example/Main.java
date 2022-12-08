package org.example;

import org.example.common.CommonConstants;
import org.example.dao.QueryEntryDao;
import org.example.service.GenerateSql;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        //TODO 读取查询信息
        String temp;

        //TODO 执行词条信息查询
        //QueryEntryDao queryEntryDao = new QueryEntryDao();
        //本地调用，自定义项目路径
        QueryEntryDao queryEntryDao = new QueryEntryDao(Main.class.getClassLoader().getResource(CommonConstants.PROPERTIES_NAME).getPath());
        queryEntryDao.getEntry();

        //TODO 生成Update脚本
        GenerateSql generateSql = new GenerateSql();
        System.out.println("完成");
//        System.out.println(System.getProperty("user.dir"));
//        String p = Main.class.getClassLoader().getResource(CommonConstants.PROPERTIES_NAME).getPath();
//        System.out.println(p);
//        String configPath = Paths.get(Main.class.getResource(File.separator+CommonConstants.PROPERTIES_NAME).getPath()).toString();
//        System.out.println(configPath);
    }
}