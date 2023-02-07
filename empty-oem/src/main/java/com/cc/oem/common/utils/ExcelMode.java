package com.cc.oem.common.utils;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.List;

/**
 * copyright (C), 2018-2018, 创蓝253
 * fileName ExcelMode
 * author   zhangx
 * date     2018/12/6 10:18
 * description excel导出扩展，未来适应大量数据导出
 */
public class ExcelMode {
    private int maxRowPerSheet=50*10000;

    private SXSSFWorkbook workbook;
    private SXSSFSheet currentSheet;

    /**从0开始*/
    private int currentSheetIndex=0;
    private HttpServletResponse response;
    private List<Field> fieldList ;
    private String excelFileName;

    private  Class<?> clazz;
    /**
     * 从0开始
     * */
    private int currentRowIndex;

    public int getMaxRowPerSheet() {
        return maxRowPerSheet;
    }

    public void setMaxRowPerSheet(int maxRowPerSheet) {
        this.maxRowPerSheet = maxRowPerSheet;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public SXSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(SXSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public SXSSFSheet getCurrentSheet() {
        return currentSheet;
    }

    public void setCurrentSheet(SXSSFSheet currentSheet) {
        this.currentSheet = currentSheet;
    }

    public int getCurrentSheetIndex() {
        return currentSheetIndex;
    }

    public void setCurrentSheetIndex(int currentSheetIndex) {
        this.currentSheetIndex = currentSheetIndex;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public String getExcelFileName() {
        return excelFileName;
    }

    public void setExcelFileName(String excelFileName) {
        this.excelFileName = excelFileName;
    }

    public int getCurrentRowIndex() {
        return currentRowIndex;
    }

    public void setCurrentRowIndex(int currentRowIndex) {
        this.currentRowIndex = currentRowIndex;
    }
}
