package org.example.dao;

import com.mysql.cj.util.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.example.common.SqlSessionUtils;
import org.example.dto.TableInfoDTO;
import org.example.exception.MyExecption;
import org.example.mapper.SelectMapper;

import java.util.ArrayList;
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
    public List<Map<String,Object>> getEntry(TableInfoDTO info) {
        //sql断点
        //org.apache.ibatis.executor.CachingExecutor#query
        //org.apache.ibatis.executor.SimpleExecutor#doQuery
        String tableName = info.getTableName();
        String nameColumn = info.getNameColumn();
        String residColumn = info.getResidColumn();
        String condition = info.getCondition();
        List<Map<String, Object>> entryList = new ArrayList<>();
        try {
            if (tableName == null || nameColumn == null || residColumn == null) {
                throw new MyExecption("tableName||nameColumn||residColumn not recognized");
            }
            if (StringUtils.isNullOrEmpty(condition)||" ".equals(condition)) {
                condition = " 1=1 ";
            }
            entryList = this.selectMapper.getEntry(nameColumn, residColumn, tableName, condition);
        } catch (MyExecption myExecption) {
            System.out.println(myExecption.getMessage());
            myExecption.printStackTrace();
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println(QueryEntryDao.class + "#getEntry"+"{ "+tableName+"}");
            e.printStackTrace();
            return new ArrayList<>();
        }
        return entryList;
    }


    /**
     * @Description  获取mapper对象
     * @Param: [clzz, customPath]
     * @return: T
     * @throws:
     */
    public <T> T getMapper(Class<T> clzz,String... customPath) {
        SqlSession sqlSession  = SqlSessionUtils.getSqlSession(customPath);
        T mapper = sqlSession.getMapper(clzz);
        return mapper;
    }
}
