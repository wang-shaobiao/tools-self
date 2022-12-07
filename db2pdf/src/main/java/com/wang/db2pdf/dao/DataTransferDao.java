package com.wang.db2pdf.dao;

import com.wang.db2pdf.mapper.DemoMapper;
import com.wang.db2pdf.util.DbUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataTransferDao {
    private String dbName;
    private DemoMapper demoMapper;

    public DataTransferDao() {
        this.demoMapper = getDemoMapper();
    }

    public DemoMapper getDemoMapper() {
        SqlSession sqlSession = DbUtils.getSqlSession();
        DemoMapper mapper = sqlSession.getMapper(DemoMapper.class);
        return mapper;
    }

    public List<Map<String, Object>> getDetail(String tableName) {
        return demoMapper.getTableColumnDetail(tableName, this.dbName);
    }


    public List<Map<String, Object>> getAllName(String dbName) {
        this.dbName = dbName;
        return demoMapper.getAllTableNames(dbName);
    }
}
