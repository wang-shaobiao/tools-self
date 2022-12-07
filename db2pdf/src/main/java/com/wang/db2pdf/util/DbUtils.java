package com.wang.db2pdf.util;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;
import java.util.Properties;

public class DbUtils {
    public static SqlSession getSqlSession() {
        InputStream pro = null;
        InputStream is = null;
        Properties properties = new Properties();
        SqlSession sqlSession= null;
        try {
            //1、加载 mybatis 全局配置文件
            is = DbUtils.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
            //2、加载 数据库配置文件
            pro = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir") + File.separator+"db.properties"));
            properties.load(pro);
            //3、创建SqlSessionFactory对象
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is, properties);
            sqlSession =  sqlSessionFactory.openSession();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                pro.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sqlSession;
    }

}
