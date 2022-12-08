package org.example.common;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @description: mybatis-sqlSession
 * @author: wshbiao
 * @create: 2022-12-08
 **/

public class SqlSessionUtils {

    public static SqlSession getSqlSession(String... customPath) {
        InputStream wholeSetting = null;
        BufferedReader br = null;
        Properties properties = new Properties();
        SqlSession sqlSession = null;
        Charset utf8Charset = Charset.forName("UTF-8");
        Path propertiesPath = ConfigUtils.getPropertiesPath(customPath);
        if (Files.exists(propertiesPath)) {
            try {
                //1. 加载mybatis全局配置
                wholeSetting = ConfigUtils.getMybatisConfig();
                //2. 加载配置文件
                br = Files.newBufferedReader(propertiesPath, utf8Charset);
                properties.load(br);
                //3. 创建sqlSessionFactory对象
                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(wholeSetting, properties);
                sqlSession = sqlSessionFactory.openSession();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    wholeSetting.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return sqlSession;
    }
}
