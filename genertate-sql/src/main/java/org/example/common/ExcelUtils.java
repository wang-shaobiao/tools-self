package org.example.common;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

import static org.example.common.CommonConstants.EXCEL_XLS;
import static org.example.common.CommonConstants.EXCEL_XLSX;

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


}
