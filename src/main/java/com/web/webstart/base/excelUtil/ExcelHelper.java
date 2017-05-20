package com.web.webstart.base.excelUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jodd.bean.BeanUtil;
import jodd.datetime.JDateTime;
import jodd.util.StringUtil;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * excel帮助类
 * @createTime: 2014-8-19 下午12:03:49
 * @author: zhanglin
 * @version: 0.1
 */
public class ExcelHelper {
	
	private static ExcelHelper helper = null;
    
    private ExcelHelper() {
         
    }
     
    public static synchronized ExcelHelper getInstanse() {
        if(helper == null) {
            helper = new ExcelHelper();
        }
        return helper;
    }
    
    /**
     * 将Excel文件导入到list对象
     * @param head  文件头信息
     * @param file  导入的数据源
     * @param cls   保存当前数据的对象
     * @return
     * @auther zhanglin
     * @return List
     * 2014-8-19 下午01:17:48
     */
    @SuppressWarnings("rawtypes")
	public List importToObjectList(ExcelHead head, File file, Class cls) {
        List contents = null;
        FileInputStream fis;
        // 根据excel生成list类型的数据
        List<List> rows;
        try {
            fis = new FileInputStream(file);
            rows = excelFileConvertToList(fis);
             
            // 删除头信息
            for (int i = 0; i < head.getRowCount(); i++) {
                rows.remove(0);
            }
             
            // 将表结构转换成Map
            Map<Integer, String> excelHeadMap = convertExcelHeadToMap(head.getColumns());
            // 构建为对象
            contents = buildDataObject(excelHeadMap, head.getColumnsConvertMap(), rows, cls);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return contents;
    }
    
    /**
     * 导出数据至Excel文件
     * @param excelColumns  报表头信息
     * @param excelHeadConvertMap   需要对数据进行特殊转换的列
     * @param modelFile  模板Excel文件
     * @param outputFile 导出文件
     * @param dataList  导入excel报表的数据来源
     * @auther zhanglin
     * @return void
     * 2014-8-19 下午01:17:48
     */
    public void exportExcelFile(HttpServletResponse response,ExcelHead head, List<?> dataList) {
    	//创建excel
        Workbook wb = new HSSFWorkbook();
        try {
            //创建一个工作区域
            Sheet sheet = wb.createSheet();
            // 生成导出数据
            buildExcelData(sheet, head, dataList);
            //设置默认文件名为当前时间：年月日时分秒 
            String fileName=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString(); 
            response.reset();           
            response.setContentType("application/vnd.ms-excel");        //改成输出excel文件 
            response.setHeader("Content-disposition","attachment; filename="+fileName+".xls" ); 
            // 导出到文件中
            OutputStream fileOut = response.getOutputStream();
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * 将报表结构转换成Map
     * @param excelColumns
     * @auther zhanglin
     * @return void
     * 2014-8-19 下午01:17:48
     */
    private Map<Integer, String> convertExcelHeadToMap(List<ExcelColumn> excelColumns) {
        Map<Integer, String> excelHeadMap = new HashMap<Integer, String>();
        for (ExcelColumn excelColumn : excelColumns) {
            if(StringUtil.isEmpty(excelColumn.getFieldName())) {
                continue;
            } else {
                excelHeadMap.put(excelColumn.getIndex(), excelColumn.getFieldName());
            }
        }
        return excelHeadMap;
    }
    
    /**
     * 生成导出至Excel文件的数据
     * @param sheet 工作区间
     * @param excelColumns  excel表头
     * @param excelHeadMap  excel表头对应实体属性
     * @param excelHeadConvertMap   需要对数据进行特殊转换的列
     * @param dataList      导入excel报表的数据来源
     * @auther zhanglin
     * @return void
     * 2014-8-19 下午01:17:48
     */
    @SuppressWarnings("rawtypes")
	private void buildExcelData(Sheet sheet, ExcelHead head, List<?> dataList) {
        List<ExcelColumn> excelColumns = head.getColumns(); 
        Map<String, Map> excelHeadConvertMap = head.getColumnsConvertMap();
         
        // 将表结构转换成Map
        Map<Integer, String> excelHeadMap = convertExcelHeadToMap(excelColumns);
        // 从第几行开始插入数据
        int startRow = head.getRowCount();
        int order = 1;
        for (Object obj : dataList) {
            Row row = sheet.createRow(startRow++);
            Row rowhead = sheet.createRow(0);//获取表头行
            //开始填充数据
            for (int j = 0; j < excelColumns.size(); j++) {
            	//为表头行设置标题
            	rowhead.createCell(j).setCellValue(excelColumns.get(j).getFieldDispName());
                Cell cell = row.createCell(j);
                cell.setCellType(excelColumns.get(j).getType());
                String fieldName = excelHeadMap.get(j);
                if(fieldName != null) {
                    Object valueObject = BeanUtil.getProperty(obj, fieldName);
                     
                    // 如果存在需要转换的字段信息，则进行转换
                    if(excelHeadConvertMap != null && excelHeadConvertMap.get(fieldName) != null) {
                        valueObject = excelHeadConvertMap.get(fieldName).get(valueObject);
                    }
                     
                    if(valueObject == null) {
                        cell.setCellValue("");
                    } else if (valueObject instanceof Integer) {
                        cell.setCellValue((Integer)valueObject);
                    } else if (valueObject instanceof String) {
                        cell.setCellValue((String)valueObject);
                    } else if (valueObject instanceof Date) {
                        cell.setCellValue(new JDateTime((Date)valueObject).toString("YYYY-MM-DD"));
                    } else {
                        cell.setCellValue(valueObject.toString());
                    }
                } else {
                    cell.setCellValue(order++);
                }
                sheet.autoSizeColumn((short)j);
            }
        }
    }
    
    /**
     * 将Excel文件内容转换为List对象
     * @param fis   excel文件
     * @return  List<List> list存放形式的内容
     * @throws IOException
     * @auther zhanglin
     * @return List<List>
     * 2014-8-19 下午01:17:48
     */
    @SuppressWarnings({ "rawtypes", "unused" })
	public List<List> excelFileConvertToList(FileInputStream fis) throws Exception {
        Workbook wb = WorkbookFactory.create(fis);
         
        Sheet sheet = wb.getSheetAt(0);
         
        List<List> rows = new ArrayList<List>();
        for (Row row : sheet) {
            List<Object> cells = new ArrayList<Object>();
            for (Cell cell : row) {
                Object obj = null;
                 
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
 
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        obj = cell.getRichStringCellValue().getString();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            obj = new JDateTime(cell.getDateCellValue());
                        } else {
                            obj = cell.getNumericCellValue();
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        obj = cell.getBooleanCellValue();
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        obj = cell.getNumericCellValue();
                        break;
                    default:
                        obj = null;
                }
                cells.add(obj);
            }
            rows.add(cells);
        }
        return rows;
    }
    
    /**
     * 根据Excel生成数据对象
     * @param excelHeadMap 表头信息
     * @param excelHeadConvertMap 需要特殊转换的单元
     * @param rows
     * @param cls 
     * @auther zhanglin
     * @return void
     * 2014-8-19 下午01:17:48
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private List buildDataObject(Map<Integer, String> excelHeadMap, Map<String, Map> excelHeadConvertMap, List<List> rows, Class cls) {
        List contents = new ArrayList();
        for (List list : rows) {
            // 如果当前第一列中无数据,则忽略当前行的数据
            if(list == null || list.get(0) == null) {
                break;
            }
            // 当前行的数据放入map中,生成<fieldName, value>的形式
            Map<String, Object> rowMap = rowListToMap(excelHeadMap, excelHeadConvertMap, list);
             
            // 将当前行转换成对应的对象
            Object obj = null;
            try {
                obj = cls.newInstance();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
            BeanUtil.populateBean(obj, rowMap);
             
            contents.add(obj);
        }
        return contents;
    }
    
    /**
     * 将行转行成map,生成<fieldName, value>的形式
     * @param excelHeadMap 表头信息
     * @param excelHeadConvertMap excelHeadConvertMap
     * @param list
     * @return
     * @auther zhanglin
     * @return Map<String,Object>
     * 2014-8-19 下午01:17:48
     */
    @SuppressWarnings("rawtypes")
	private Map<String, Object> rowListToMap(Map<Integer, String> excelHeadMap, Map<String, Map> excelHeadConvertMap, List list) {
        Map<String, Object> rowMap = new HashMap<String, Object>();
        for(int i = 0; i < list.size(); i++) {
            String fieldName =  excelHeadMap.get(i);
            // 存在所定义的列
            if(fieldName != null) {
                Object value = list.get(i);
                if(excelHeadConvertMap != null && excelHeadConvertMap.get(fieldName) != null) {
                    value = excelHeadConvertMap.get(fieldName).get(value);
                }
                rowMap.put(fieldName, value);
            }
        }
        return rowMap;
    }

}
