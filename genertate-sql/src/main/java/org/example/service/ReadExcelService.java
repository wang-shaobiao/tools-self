package org.example.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.common.ConfigUtils;
import org.example.common.ExcelUtils;
import org.example.exception.NoPathException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.common.CommonConstants.EXCEL_TYPE;

public class ReadExcelService {

    public List<Map<String, Object>> doGetExcelInfo(String[] columnDefines,String... strings) {
        List<Map<String,Object>> list = new ArrayList<>();
        String path = null;
        Workbook workbook = null;
        InputStream in = null;
        try {
            path = ConfigUtils.getPath(EXCEL_TYPE, strings).toString();
            in = new BufferedInputStream(new FileInputStream(path));
            workbook = ExcelUtils.getWorkBook(path,in);
            list = getExcelInfo(workbook, 0, columnDefines);
        } catch (NoPathException noPathException) {
            System.out.println(noPathException.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return list;

    }

    public List<Map<String, Object>> getExcelInfo(Workbook workbook,int index,String[] columnDefines) {
        List<Map<String, Object>> list = new ArrayList<>();
        //获取第几个sheet
        Sheet sheet = workbook.getSheetAt(index);
        //获取总行数
        int rowSum = sheet.getPhysicalNumberOfRows();
        //从第二行开始取值
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            //正常可遍历列row.getPhysicalNumberOfCells()
            for (int j = 0; j < columnDefines.length; j++) {
                Map<String, Object> map = new HashMap<>();
                Cell cell = row.getCell(j);
                if (cell != null) {
                    map.put(columnDefines[j], cell.getStringCellValue());
                } else {
                    map.put(columnDefines[j], null);
                }
                list.add(map);
            }
        }
        return list;
    }
}
