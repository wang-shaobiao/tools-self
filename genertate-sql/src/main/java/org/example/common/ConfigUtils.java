package org.example.common;

import org.example.exception.NoPathException;
import sun.util.resources.LocaleData;

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
        if (isEmpty(customPath)) {
            //默认规则路径
            String conf = System.getProperty("conf");
            if (isEmpty(conf)) {
                conf = System.getProperty("user.dir") + File.separator + conf_DEFAULT;
            }
            if (PROPERTIES_TYPE.equals(type)) {//application.properties获取规则
                path = Paths.get(conf, PROPERTIES_NAME);
            } else if (EXCEL_TYPE.equals(type)) {
                path = Paths.get(conf, EXCEL_NAME);//源Excel获取
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

    public static Path getDesPath(String type, String... customPath) {
        Path path = null;
        if (isEmpty(customPath)) {
            //默认规则路径
            String conf = System.getProperty("des");
            if (isEmpty(conf)) {
                conf = DES_DEFAULT;
            }
            if (GENERATE_TYPE_SQL.equals(type)) {
                path = Paths.get(conf, LocalDate.now().toString());
            }
        } else {
            path = Paths.get(customPath[0], LocalDate.now().toString());
        }
        return path;
    }


    public static boolean isEmpty(String... str) {
        return str == null || str.length == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;

    }

    public static InputStream getMybatisConfig() {
        return ConfigUtils.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
    }
}
