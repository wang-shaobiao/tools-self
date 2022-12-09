package org.example.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.example.common.SqlSessionUtils;
import org.example.mapper.SelectMapper;

import java.util.List;
import java.util.Map;

/**
 * @description: Query-name and resid
 * @author: wshbiao
 * @create: 2022-12-08
 **/

public class QueryEntryDao {
    private SelectMapper selectMapper;


    public QueryEntryDao() {
        this.selectMapper = getMapper(SelectMapper.class);
    }
    public QueryEntryDao(String... customPath){
        this.selectMapper = getMapper(SelectMapper.class, customPath);
    }
    /**
    * @Description mapper执行，获取多语词条
    * @Param: [nameColumn, residColumn, tableName, condition]
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    * @throws:
    */
    public List<Map<String,Object>> getEntry(String tableName,String nameColumn, String residColumn, String condition) {
        //sql断点
        //org.apache.ibatis.executor.CachingExecutor#query
        //org.apache.ibatis.executor.SimpleExecutor#doQuery
        return selectMapper.getEntry(nameColumn, residColumn, tableName, condition);
    }

    /**
    * @Description 获取mapper对象
    * @Param: [clzz]
    * @return: T
    * @throws:
    */
    public <T> T getMapper(Class<T> clzz,String... customPath) {
        SqlSession sqlSession  = SqlSessionUtils.getSqlSession(customPath);
        T mapper = sqlSession.getMapper(clzz);
        return mapper;
    }
}
