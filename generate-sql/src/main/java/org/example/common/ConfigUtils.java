package org.example.common;

import org.example.exception.NoPathException;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.example.common.CommonConstants.*;


/**
 * @description: ConfigUtils
 * @author: wshbiao
 * @create: 2022-12-08
 **/


public class ConfigUtils {
    
    /** 
     * @Description 获取资源路径，无入参路径，则使用默认规则获取
     * 优先级：自定义路径->jvm参数conf->默认/config/路径 高->低
     * @Param: [type, customPath] 
     * @return: java.nio.file.Path 
     * @throws: NoPathException
     */
    public static Path getConfPath(String type,String... customPath) throws NoPathException {
        Path path = null;
        if (isNullOrEmpty(customPath)) {
            //默认规则路径
            /*
                fixme --存在bug和不灵活的地方，暂时不处理了
             */
            String conf = System.getProperty("conf");
            if (isNullOrEmpty(conf)) {
                conf = System.getProperty("user.dir") + File.separator + Default.CONF_DEFAULT;
            }
            if (PropertiesRelated.PROPERTIES_TYPE.equals(type)&&!conf.contains(".properties")) {//application.properties获取规则
                path = Paths.get(conf, PropertiesRelated.PROPERTIES_NAME);
            } else if (ExcelRelated.EXCEL_TYPE.equals(type) && !conf.contains(".xlsx") && !conf.contains("xlsx")) {
                path = Paths.get(conf, ExcelRelated.EXCEL_NAME);//源Excel获取
            } else {
                path = Paths.get(conf);
            }
        }else {
            //自定义路径
            path = Paths.get(customPath[0]);
        }

        if (!Files.exists(path)) {
            throw new NoPathException("文件/路径不存在");
        }
        return path;
    }

    /** 
     * @Description 获取生成目录地址 
     * @Param: [type, customPath] 
     * @return: java.nio.file.Path 
     * @throws: 
     */
    public static Path getDesPath(String type, String... customPath) {
        Path path = null;
        if (isNullOrEmpty(customPath)) {
            //默认规则路径
            String conf = System.getProperty("des");
            if (isNullOrEmpty(conf)) {
                conf = Default.DES_DEFAULT;
            }
            if (GenerateRelated.GENERATE_TYPE_SQL.equals(type)) {
                path = Paths.get(conf, LocalDate.now().toString());
            }
        } else {
            path = Paths.get(customPath[0], LocalDate.now().toString());
        }
        return path;
    }


    public static boolean isNullOrEmpty(String... str) {
        return str == null || str.length == 0;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;

    }

    /** 
     * @Description mybatis配置文件加载
     * @Param: []
     * @return: java.io.InputStream 
     * @throws: 
     */
    public static InputStream getMybatisConfig() {
        return ConfigUtils.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
    }
}
