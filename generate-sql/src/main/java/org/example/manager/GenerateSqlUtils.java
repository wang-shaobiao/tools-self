package org.example.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.example.entity.CstyleHandleDTO;
import org.example.entity.TableInfoDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.example.common.CommonConstants.*;
import static org.example.manager.CommonUtils.isNullOrEmpty;

/**
 * @description: generate - tools
 * @author: wshbiao
 * @create: 2022-12-09
 */

public class GenerateSqlUtils {

    /**
     * @Description  generate sql script file
     * @Param: [filePath, entryList, tableName, nameColumn, residColumn, condition, note]
     * @return: java.lang.Boolean
     * @throws:
     */
    public static Boolean generateSqlFile(Path filePath, List<Map<String,Object>> entryList, TableInfoDTO infoDTO) {
        AtomicInteger sum = new AtomicInteger();

        //生成脚本内容
        String sqlContent = Optional.ofNullable(generateSqlContent(infoDTO, sum, entryList)).orElse(null);

        // 生成注释文本
        String note = Optional.ofNullable(generateNote(infoDTO, sum.get())).orElse(null) ;

        // 写入输出流
        return outPutFile(filePath,note,sqlContent);

    }

    /**
     * @Description  get sqlScript fileName
     * @Param: [tableName, columnName]
     * @return: java.lang.String
     * @throws:
     */
    public static String getGenerateFileName(String tableName,String columnName) {
        StringBuilder strb = new StringBuilder();
        strb.append(GenerateRelated.GENERATE_FILE_PREFIX).append("-")
                .append(tableName).append("-").append(columnName).append("-")
                .append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .append(".sql");
        return strb.toString();
    }
    /**
     * 生成具体sql脚本内容
     * @param infoDTO
     * @param sum
     * @param entryList
     * @return
     */
    private static String generateSqlContent(TableInfoDTO infoDTO,AtomicInteger sum,List<Map<String,Object>> entryList) {
        StringBuilder sqlStrBuilder = new StringBuilder();

        if ("cStyle".equals(infoDTO.getNameColumn())) {
            Set<CstyleHandleDTO> dtoSet = entryList.stream().map(item->{
                CstyleHandleDTO dto = JSON.parseObject(JSON.toJSONString(item), CstyleHandleDTO.class);
                return dto;
            }).collect(Collectors.toSet());
            dtoSet.stream().forEach(m->{
                sqlStrBuilder.append("update ").append(infoDTO.getTableName() + " a ")
                        .append("set ").append("a.cStyle = ").append("'" + m.getCStyle() + "'") // set content
                        .append("where ")
                        .append("a.cSubId = ").append("'" + m.getCSubId() + "'").append(" and ")
                        .append("a.cFieldName = ").append("'" + m.getCFieldName() + "'").append(" and ")
                        .append("a.cDataSourceName = ").append("'" + m.getCDataSourceName() + "'").append(" and ")
                        .append("exists (").append(" select 1 from bill_base b where b.id = a.iBillId and b.cBillNo = ").append("'" + m.getCBillNo() + "'")
                        .append(");").append("-- " + m.getCStyle())
                        .append("\r\n");
                sum.getAndIncrement();
            });
        } else {
            Set<Map<String,Object>> dtoSet = entryList.stream().collect(Collectors.toSet());
            dtoSet.stream().forEach(m -> {
                sqlStrBuilder.append("update ").append(infoDTO.getTableName())
                        .append(" set ").append(infoDTO.getResidColumn()).append("=").append("'" + m.get(infoDTO.getResidColumn()) + "'")
                        .append(" where ").append(infoDTO.getNameColumn()).append("=").append("'" + m.get(infoDTO.getNameColumn()) + "'")
                        .append(";")
                        .append("\r\n");
                sum.getAndIncrement();
            });
        }
        return sqlStrBuilder.toString();
    }

    /**
     * 组装脚本注释文本
     * @param infoDTO
     * @param sum
     * @return
     */
    private static String generateNote(TableInfoDTO infoDTO,int sum) {
        StringBuilder noteBuilder = new StringBuilder();
        //注释说明
        noteBuilder.append("-- ").append("International\r\n")
                .append("-- -------------------------------\r\n")
                .append("-- ").append("Creation Time: ").append(LocalDateTime.now()).append("\r\n")
                .append("-- ").append("Table: ").append(infoDTO.getTableName()).append("\r\n")
                .append("-- ").append("Column: ").append("[" + infoDTO.getNameColumn() + "]").append("&").append("[" + infoDTO.getResidColumn() + "]").append("\r\n")
                .append("-- ").append("Condition: ").append(infoDTO.getCondition()).append("\r\n")
                .append("-- ").append("SUM: ").append(sum).append("条\r\n")
                .append("-- ").append("Note: ").append(infoDTO.getNote()).append("\r\n");
        return noteBuilder.toString();
    }

    /**
     * 写入输出流，生成文件
     * @param filePath
     * @param note
     * @param sql
     * @return
     */
    private static boolean outPutFile(Path filePath,String note,String sql) {
        if (isNullOrEmpty(sql)) {
            return false;
        }
        PrintWriter out = null;
        try {
            File file = new File(filePath.toString());
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            out.write(note);
            out.println();
            out.write(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally{
            if (out != null) {
                out.close();
            }
        }
        return true;
    }

}
