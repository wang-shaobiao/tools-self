package org.example.manager;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.exception.NoPathException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.example.common.CommonConstants.*;

/**
 * @description: mybatis-sqlSession
 * @author: wshbiao
 * @create: 2022-12-08
 **/

public class SqlSessionUtils {

    /** 
     * @Description 获取sqlSession 
     * @Param: [customPath] 
     * @return: org.apache.ibatis.session.SqlSession 
     * @throws: 
     */
    public static SqlSession getSqlSession(String... customPath) {
        InputStream wholeSetting = null;
        BufferedReader br = null;
        Path propertiesPath = null;
        Properties properties = new Properties();
        SqlSession sqlSession = null;
        Charset utf8Charset = Charset.forName("UTF-8");

        try {
            //1. 加载mybatis全局配置
            wholeSetting = ConfigUtils.getMybatisConfig();
            //2. 加载配置文件
            propertiesPath = ConfigUtils.getConfPath(PropertiesRelated.PROPERTIES_TYPE, customPath);
            br = Files.newBufferedReader(propertiesPath);
            properties.load(br);
            //3. 创建sqlSessionFactory对象
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(wholeSetting, properties);
            sqlSession = sqlSessionFactory.openSession();

        } catch (NoPathException n) {
            System.out.printf(n.getMessage());
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

        return sqlSession;
    }
}
