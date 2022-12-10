package org.example.common;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.example.common.CommonConstants.*;

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
    public static Boolean generateSqlFile(Path filePath, List<Map<String,Object>> entryList,String tableName,String nameColumn,String residColumn,String condition,String note ) {
        StringBuilder noteBuilder = new StringBuilder();
        StringBuilder sqlStrBuilder = new StringBuilder();
        PrintWriter out = null;
        //注释说明
        noteBuilder.append("-- ").append("International\r\n")
                .append("-- -------------------------------\r\n")
                .append("-- ").append("Creation Time: ").append(LocalDateTime.now()).append("\r\n")
                .append("-- ").append("Table: ").append(tableName).append("\r\n")
                .append("-- ").append("Column: ").append("[" + nameColumn + "]").append("&").append("[" + residColumn + "]").append("\r\n")
                .append("-- ").append("Condition: ").append(condition).append("\r\n")
                .append("-- ").append("Note: ").append(note).append("\r\n");
        //脚本内容
        entryList.stream().forEach(m -> {
            sqlStrBuilder.append("update ").append(tableName)
                    .append(" set ").append(residColumn).append("=").append("'" + m.get(residColumn) + "'")
                    .append(" where ").append(nameColumn).append("=").append("'" + m.get(nameColumn) + "'")
                    .append(";")
                    .append("\r\n");
        });

        try {
            File file = new File(filePath.toString());
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new PrintWriter(file);
            out.write(noteBuilder.toString());
            out.println();
            out.write(sqlStrBuilder.toString());
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
}
