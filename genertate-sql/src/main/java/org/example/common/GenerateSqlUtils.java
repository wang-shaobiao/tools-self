package org.example.common;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.example.common.CommonConstants.GENERATE_TYPE_SQL;

/**
 * @description: generate - tools
 * @author: wshbiao
 * @create: 2022-12-09
 */

public class GenerateSqlUtils {

    public static Boolean generateSqlFile(Path filePath, List<Map<String,Object>> entryList,String tableName,String nameColumn,String residColumn) {
        StringBuilder sqlStrBuilder = new StringBuilder();
        PrintWriter out = null;
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
}
