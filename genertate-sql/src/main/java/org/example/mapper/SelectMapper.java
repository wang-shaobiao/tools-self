package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SelectMapper {

    /** 
    * @Description 获取此条和资源id 
    * @Param: [name, resid, tableName, condition] 
    * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>> 
    * @throws: 
    */

    @Select("select ${nameColumn},${residColumn} from ${tableName} where 1=1 and ${condition} and ${nameColumn} is not null and ${residColumn} is not null")
    List<Map<String,Object>> getEntry(@Param("nameColumn")String nameColumn,@Param("residColumn")String residColumn,
                                      @Param("tableName")String tableName,@Param("condition")String condition);
}

