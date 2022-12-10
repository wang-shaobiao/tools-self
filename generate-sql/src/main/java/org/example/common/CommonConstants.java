package org.example.common;

public interface CommonConstants {

    interface Default{
        /**
         * 生成文件路径-默认
         */
        String DES_DEFAULT = "destination";
        /**
         * 配置文件路径-默认
         */
        String CONF_DEFAULT = "config";

    }
    interface ExcelRelated{
        /**
         * excel后缀
         */
        String EXCEL_XLS = "xls";
        String EXCEL_XLSX = "xlsx";
        /**
         * excel类型
         */
        String EXCEL_TYPE = "excel";
        /**
         * Excel抽取配置文件
         */
        String EXCEL_NAME = "resource.xlsx";
        /**
         * excel配置列信息
         */
        String[] colDef = {"tableName","nameColumn","residColumn","condition","note"};

    }
    interface PropertiesRelated{
        /**
         * Properties文件类型
         */
        String PROPERTIES_TYPE = "properties";
        /**
         * 基础配置文件
         */
        String PROPERTIES_NAME = "application.properties";

    }


    interface GenerateRelated{
        /**
         * 生成脚本类型
         */
        String GENERATE_TYPE_SQL = "sql";
        /**
         * 多语sql文件前缀
         */
        String GENERATE_FILE_PREFIX = "ui_multi_language";
    }











}
