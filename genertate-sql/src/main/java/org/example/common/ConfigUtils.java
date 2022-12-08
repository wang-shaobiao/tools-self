package org.example.common;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * @description: ConfigUtils
 * @author: wshbiao
 * @create: 2022-12-08
 **/


public class ConfigUtils {

    /**
     * @Description  获取配置资源路径，无入参路径，则使用默认规则获取
     * 优先级：自定义路径->jvm参数conf->默认/config/路径 高->低
     * @Param: [customPath]
     * @return: java.nio.file.Path
     * @throws:
    */
    public static Path getPropertiesPath(String... customPath) {
        Path configPath = null;
        //默认规则路径
        if (customPath == null || customPath.length == 0) {
            String config = System.getProperty("conf");
            if (config == null || config.length() == 0) {
                config = System.getProperty("user.dir") + File.separator + "config";
            }
            configPath = Paths.get(config, CommonConstants.PROPERTIES_NAME);
            //调试使用
            if (!Files.exists(configPath)) {
                configPath = Paths.get(ConfigUtils.class.getClassLoader().getResource(CommonConstants.PROPERTIES_NAME).getPath());
            }
            return configPath;
        }
        //自定义路径
        configPath = Paths.get(customPath[0]);
        return configPath;

    }

    public static InputStream getMybatisConfig() {
        return ConfigUtils.class.getClassLoader().getResourceAsStream("mybatis.config.xml");
    }
}
