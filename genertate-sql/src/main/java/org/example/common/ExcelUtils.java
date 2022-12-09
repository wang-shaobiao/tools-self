package org.example.common;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.exception.NoPathException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.common.CommonConstants.*;

public class ExcelUtils {

    public static Workbook getWorkBook(String path,InputStream in) throws IOException {
        //定义Workbook
        Workbook workbook = null;
        //substring入参为起点，包含
        if (EXCEL_XLS.equals(path.substring(path.lastIndexOf(".") + 1))) {
            workbook = new HSSFWorkbook(in);
        }else if(EXCEL_XLSX.equals(path.substring(path.lastIndexOf(".")+1))){
            workbook = new XSSFWorkbook(in);
        }
        return workbook;
    }

    public static List<Map<String, Object>> doGetExcelInfo(String[] columnDefines, String... strings) {
        List<Map<String,Object>> list = new ArrayList<>();
        String path = null;
        Workbook workbook = null;
        InputStream in = null;
        try {
            path = ConfigUtils.getConfPath(EXCEL_TYPE, strings).toString();
            in = new BufferedInputStream(new FileInputStream(path));
            workbook = ExcelUtils.getWorkBook(path,in);
            //excel行信息获取
            list = getExcelInfo(workbook, 0, columnDefines);
        } catch (NoPathException noPathException) {
            System.out.println(noPathException.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return list;

    }

    public static List<Map<String, Object>> getExcelInfo(Workbook workbook,int index,String[] columnDefines) {
        List<Map<String, Object>> list = new ArrayList<>();

        //获取第几个sheet
        Sheet sheet = workbook.getSheetAt(index);
        //获取总行数
        int rowSum = sheet.getPhysicalNumberOfRows();
        //从第二行开始取值
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            Map<String, Object> map = new HashMap<>();
            //正常可遍历列row.getPhysicalNumberOfCells()
            for (int j = 0; j < columnDefines.length; j++) {
                Cell cell = row.getCell(j);
                if (cell != null) {
                    map.put(columnDefines[j], cell.getStringCellValue());
                } else {
                    map.put(columnDefines[j], null);
                }
            }
            list.add(map);
        }
        return list;
    }

}
