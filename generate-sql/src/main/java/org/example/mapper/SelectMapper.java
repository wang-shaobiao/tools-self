package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SelectMapper {


    /**
     * @Description  获取多语 词条信息
     * @Param: [nameColumn, residColumn, tableName, condition]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @throws:
     */
    @Select("select id, ${nameColumn},${residColumn} from ${tableName} where ${condition} and ${nameColumn} is not null and ${residColumn} is not null")
    List<Map<String,Object>> getEntry(@Param("nameColumn")String nameColumn,@Param("residColumn")String residColumn,
                                      @Param("tableName")String tableName,@Param("condition")String condition);


    @Select("select a.tenant_id ,a.cSubId ,a.cStyle ,a.cName ,a.cFieldName ,a.cDataSourceName ,b.cBillNo  from billitem_base a left join bill_base b on a.iBillId  = b.id " +
            "where b.tenant_id = '0'  AND b.isDeleted = 0 AND b.sysid IS NULL  and a.cStyle like '%resid%'")
    List<Map<String, Object>> getBillItemCstyleEntry();
}

