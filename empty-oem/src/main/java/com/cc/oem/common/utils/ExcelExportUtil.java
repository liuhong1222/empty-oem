package com.cc.oem.common.utils;

import com.cc.oem.common.annotation.ExcelField;
import com.cc.oem.common.exception.RRException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author chenzj
 * @since 2018/8/14
 */
public class ExcelExportUtil {

    public static Logger logger = LoggerFactory.getLogger(ExcelExportUtil.class);

    public static void exportList(String excelFileName, List data, Class<?> clazz, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        exportList(1,excelFileName,data,clazz,response);
    }

    public static void exportList(Integer roleId,String excelFileName, List data, Class<?> clazz, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        if (StringUtils.isBlank(excelFileName)) {
            DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            excelFileName = dateTimeFormat.format(new Date());
        }
        try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {
            SXSSFSheet sheet = workbook.createSheet("sheet1");
            Field[] fields = clazz.getDeclaredFields();
            //获取导出对象的字段信息
            List<Field> fieldList = new ArrayList<Field>();
            for (Field field : fields) {
                Annotation[] annotations = field.getDeclaredAnnotations();
                //过滤属性，只获取ExcelField注解的属性
                if (field.isAnnotationPresent(ExcelField.class)) {
                    if(roleId==2&&field.getAnnotation(ExcelField.class).needChange()==1){
                        //权限为代理商，且是可变字段，则去除
                        continue;
                    }
                    fieldList.add(field);
                }
            }
            fieldList.sort(new Comparator<Field>() {
                @Override
                public int compare(Field o1, Field o2) {
                    int i = o1.getAnnotation(ExcelField.class).order() - o2.getAnnotation(ExcelField.class).order();
                    return i;
                }
            });
            //设置表头
            int index = 0;
            SXSSFRow row = sheet.createRow(index);
            for (int i = 0; i < fieldList.size() + 1; i++) {
                SXSSFCell cell = row.createCell(i);
                String textString;
                if (i == 0) {
                    textString = "序号";
                } else {
                    textString = fieldList.get(i - 1).getAnnotation(ExcelField.class).value();
                }
                cell.setCellValue(textString);
            }
            //表格中填充数据
            for (Object obj : data) {
                index++;
                row = sheet.createRow(index);
                for (int i = 0; i < fieldList.size() + 1; i++) {
                    SXSSFCell cellData = row.createCell(i);
                    String textData;
                    if (i == 0) {
                        textData = String.valueOf(index);
                    } else {
                        String value;
                        String fieldName = fieldList.get(i - 1).getName();
                        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Method getMethod = clazz.getMethod(getMethodName, new Class[]{});
                        Object object = getMethod.invoke(obj, null);
                        if (object == null) {
                            value = "";
                        } else if (Date.class.equals(object.getClass())) {
                            //如果为Date类型，则格式化成 yyyy-MM-dd HH:mm:ss
                            value = new String(DateTimeUtil.formatDate((Date) object, DateTimeUtil.DEFAULTFORMAT) + "");
                        } else {
                            value = new String(object + "");
                        }
                        if (!value.equals("null")) {
                            textData = value;
                        } else {
                            textData = "";
                        }
                    }
                    cellData.setCellValue(textData);
                }
            }
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelFileName + ".xlsx", "utf-8"));
            workbook.write(response.getOutputStream());
        } catch (NoSuchMethodException e) {
            logger.error("出错信息：{}",e);
            new RRException("无法获取数据，请联系管理员");
        } catch (IOException e) {
            new RRException("生成文件出错了");
        }
    }

//    ////---------------------------------------------------------下面为第二版excel分页，分sheet导出,待完善  20181206---------------------------------------------------------------------------------------------------/////
//    public static ExcelMode exportListFirstStepTitle(String excelFileName, Class<?> clazz, HttpServletResponse response) {
//        if (StringUtils.isBlank(excelFileName)) {
//            DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//            excelFileName = dateTimeFormat.format(new Date());
//        }
//        SXSSFWorkbook workbook = null;
//        workbook = new SXSSFWorkbook();
//        SXSSFSheet sheet = workbook.createSheet("sheet0");
//        Field[] fields = clazz.getDeclaredFields();
//        //获取导出对象的字段信息
//        List<Field> fieldList = new ArrayList<Field>();
//        for (Field field : fields) {
//            Annotation[] annotations = field.getDeclaredAnnotations();
//            //过滤属性，只获取ExcelField注解的属性
//            if (field.isAnnotationPresent(ExcelField.class)) {
//                fieldList.add(field);
//            }
//        }
//        fieldList.sort(new Comparator<Field>() {
//            @Override
//            public int compare(Field o1, Field o2) {
//                int i = o1.getAnnotation(ExcelField.class).order() - o2.getAnnotation(ExcelField.class).order();
//                return i;
//            }
//        });
//        //设置表头
//        int index = 0;
//        SXSSFRow row = sheet.createRow(index);
//        for (int i = 0; i < fieldList.size() + 1; i++) {
//            SXSSFCell cell = row.createCell(i);
//            String textString;
//            if (i == 0) {
//                textString = "序号";
//            } else {
//                textString = fieldList.get(i - 1).getAnnotation(ExcelField.class).value();
//            }
//            cell.setCellValue(textString);
//        }
//        ExcelMode excelMode = new ExcelMode();
//        excelMode.setExcelFileName(excelFileName);
//        excelMode.setWorkbook(workbook);
//        excelMode.setFieldList(fieldList);
//        excelMode.setCurrentRowIndex(index);
//        excelMode.setResponse(response);
//        excelMode.setCurrentSheet(sheet);
//        excelMode.setCurrentSheetIndex(0);
//        excelMode.setClazz(clazz);
//        return excelMode;
//    }
//
//    public static void createNewSheet(ExcelMode excelMode) {
//        SXSSFWorkbook workbook = excelMode.getWorkbook();
//        int currentSheetIndex = excelMode.getCurrentSheetIndex();
//        currentSheetIndex++;
//
//        SXSSFSheet sheet = workbook.createSheet("sheet" + currentSheetIndex);
//        List<Field> fieldList = excelMode.getFieldList();
//
//        //设置表头
//        int index = 0;
//        SXSSFRow row = sheet.createRow(index);
//        for (int i = 0; i < fieldList.size() + 1; i++) {
//            SXSSFCell cell = row.createCell(i);
//            String textString;
//            if (i == 0) {
//                textString = "序号";
//            } else {
//                textString = fieldList.get(i - 1).getAnnotation(ExcelField.class).value();
//            }
//            cell.setCellValue(textString);
//        }
//        excelMode.setCurrentRowIndex(0);
//        excelMode.setCurrentSheet(sheet);
//        excelMode.setCurrentSheetIndex(currentSheetIndex);
//    }
//
//    public static void exportListSecondData(ExcelMode excelMode, List data) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        SXSSFSheet sheet = excelMode.getCurrentSheet();
//        List<Field> fieldList = excelMode.getFieldList();
//        Class clazz = excelMode.getClazz();
//        int index = excelMode.getCurrentRowIndex();
//        SXSSFRow row = null;
//        //表格中填充数据
//        for (Object obj : data) {
//
//            if (index >= excelMode.getMaxRowPerSheet()) {
//                createNewSheet(excelMode);
//                sheet = excelMode.getCurrentSheet();
//                index = excelMode.getCurrentRowIndex();
//            }
//
//            index++;
//            excelMode.setCurrentRowIndex(index);
//
//            row = sheet.createRow(index);
//            for (int i = 0; i < fieldList.size() + 1; i++) {
//                SXSSFCell cellData = row.createCell(i);
//                String textData;
//                if (i == 0) {
//                    textData = String.valueOf(index);
//                } else {
//                    String value;
//                    String fieldName = fieldList.get(i - 1).getName();
//                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
//                    Method getType = clazz.getType(getMethodName, new Class[]{});
//                    Object object = getType.invoke(obj, null);
//                    if (object == null) {
//                        value = "";
//                    } else if (Date.class.equals(object.getClass())) {
//                        //如果为Date类型，则格式化成 yyyy-MM-dd HH:mm:ss
//                        value = new String(DateTimeUtil.formatDate((Date) object, DateTimeUtil.DEFAULTFORMAT) + "");
//                    } else {
//                        value = new String(object + "");
//                    }
//                    if (!value.equals("null")) {
//                        textData = value;
//                    } else {
//                        textData = "";
//                    }
//                }
//                cellData.setCellValue(textData);
//            }
//        }
//    }
//
//
//    public static void exportExcelThirdFileList(ExcelMode excelMode) throws IOException {
//        excelMode.getResponse().addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelMode.getExcelFileName() + ".xlsx", "utf-8"));
//        excelMode.getWorkbook().write(excelMode.getResponse().getOutputStream());
//    }

}


//    //demo
//    @ApiOperation("导出客户列表")
//    @RequiresPermissions("user:user:info:list:export")
//    @RequestMapping(value = "/custListExport")
//    public void custListExport(CustInfoParam param, HttpServletResponse response) throws IOException {
//        try {
//
//            ExcelMode excelMode=ExcelExportUtil.exportListFirstStepTitle("test", CustExportData.class, response);
//            List<Map> mapList =null;
//
//            int currentPage=1;
//            int pages=0;
//
//            do{
//                param.setCurrentPage(currentPage);
//                param.setPageSize(10);
//                R result = authInfoService.custList(param);
//
//                List<CustExportData> list = new ArrayList<>();
//                PageInfo<Map> pageInfo = (PageInfo<Map>) result.get("data");
//
//                if(pages==0){
//                    pages= (int) (pageInfo.getTotal()/param.getPageSize()+1);
//                }
//
//                mapList = pageInfo.getList();
//
//                for (Map map : mapList) {
//                    CustExportData custExportData = new CustExportData();
//                    custExportData.setNumber(map.get("number") != null ? map.get("number").toString() : "");
//                    custExportData.setAccount(map.get("account") != null ? map.get("account").toString() : "");
//                    custExportData.setCompanyName(map.get("company_name") != null ? map.get("company_name").toString() : "");
//                    custExportData.setCreateTime(map.get("create_time") != null ? map.get("create_time").toString() : "");
//                    custExportData.setCustId(map.get("custId") != null ? map.get("custId").toString() : "");
//                    custExportData.setCustName(map.get("custName") != null ? map.get("custName").toString() : "");
//                    custExportData.setMoney(map.get("money") != null ? map.get("money").toString() : "");
//                    custExportData.setUserPhone(map.get("user_phone") != null ? map.get("user_phone").toString() : "");
//                    custExportData.setUserType(map.get("userType") != null ? map.get("userType").toString() : "");
//                    list.add(custExportData);
//                }
//                ExcelExportUtil.exportListSecondData(excelMode,list);
//                currentPage++;
//                if(currentPage>pages){
//                    break;
//                }
//
//            }while (mapList!=null && mapList.size()>0);
//
//            ExcelExportUtil.exportExcelThirdFileList(excelMode);
//
//        } catch (Exception e) {
//            excelExportErrorService.error(response, e);
//        }
//    }