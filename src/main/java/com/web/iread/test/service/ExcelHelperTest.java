package com.web.iread.test.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;

import com.web.webstart.base.excelUtil.ExcelColumn;
import com.web.webstart.base.excelUtil.ExcelHead;
import com.web.webstart.base.excelUtil.ExcelHelper;

/**
 * poi包操作excel测试
 * @createTime: 2014-8-19 下午12:03:49
 * @author: zhanglin
 * @version: 0.1
 */
public class ExcelHelperTest {
	
	@Test
	@SuppressWarnings({ "rawtypes", "unused" })
	public void excelHelperImportTest() {
        // excel列结构
        List<ExcelColumn> excelColumns = new ArrayList<ExcelColumn>();
        excelColumns.add(new ExcelColumn(1, "此处是实体类字段名", "此处是Excel中列的标题名称", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(2, "此处是实体类字段名", "此处是Excel中列的标题名称", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(3, "此处是实体类字段名", "此处是Excel中列的标题名称", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(4, "此处是实体类字段名", "此处是Excel中列的标题名称", Cell.CELL_TYPE_STRING));
         
        // 需要特殊转换的单元
        Map<String, Map> excelColumnsConvertMap = new HashMap<String, Map>();
        Map<String, Integer> isReceive = new HashMap<String, Integer>();
        isReceive.put("是", 1);
        isReceive.put("否", 0);
        excelColumnsConvertMap.put("此处是实体类字段名", isReceive);
        Map<String, Integer> orderType = new HashMap<String, Integer>();
        orderType.put("新订单", 1);
        orderType.put("续订订单", 2);
        excelColumnsConvertMap.put("此处是实体类字段名", orderType);
         
        //根据导入的文件路径创建一个文件
        File sourceFile = new File("D:\\20151127120920.xls");
         
        //根据导入的文件设置头部信息
        ExcelHead head = new ExcelHead();
        head.setRowCount(1); // 头所占行数
        head.setColumns(excelColumns);  // 列的定义
        head.setColumnsConvertMap(excelColumnsConvertMap); // 列的转换
         
        //构建一个List集合对象，调用ExcelHelper.getInstanse().importToObjectList(head, sourceFile, Class)
        //把excel中的数据转化为对象集合
    }
	
	@SuppressWarnings({ "rawtypes" })
//	@Test
    public void excelHelperExportTest() {
        //此处构造一个对象的集合
		
        // excel结构
        List<ExcelColumn> excelColumns = new ArrayList<ExcelColumn>();
        excelColumns.add(new ExcelColumn(1, "此处是实体类字段名", "此处是Excel中列的标题名称", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(2, "此处是实体类字段名", "此处是Excel中列的标题名称", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(3, "此处是实体类字段名", "此处是Excel中列的标题名称", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(4, "此处是实体类字段名", "此处是Excel中列的标题名称", Cell.CELL_TYPE_STRING));
        // 需要特殊转换的单元
        Map<String, Map> excelColumnsConvertMap = new HashMap<String, Map>();
        Map<String, Integer> isReceive = new HashMap<String, Integer>();
        isReceive.put("是", 1);
        isReceive.put("否", 0);
        excelColumnsConvertMap.put("此处是实体类字段名", isReceive);
        Map<String, Integer> orderType = new HashMap<String, Integer>();
        orderType.put("新订单", 1);
        orderType.put("续订订单", 2);
        excelColumnsConvertMap.put("此处是实体类字段名", orderType);
        //设置头部
        ExcelHead head = new ExcelHead();
        head.setRowCount(1); // 模板中头部所占行数
        head.setColumns(excelColumns);  // 列的定义
        head.setColumnsConvertMap(excelColumnsConvertMap); // 列的转换
        //执行导出,第一个null是response参数，用来输出到浏览器，第二个null是要导出的数据集合
        ExcelHelper.getInstanse().exportExcelFile(null,head, null);
    }
	
	/**
	 * 测试把excel文件转换为List集合
	 * @throws Exception
	 */
//  @Test
    @SuppressWarnings("rawtypes")
	public void excelHelperExcelFileConvertToList() throws Exception {
        FileInputStream fis = new FileInputStream("D:\\20151126092326.xls");
        List<List> dataList = ExcelHelper.getInstanse().excelFileConvertToList(fis);
        for (List list : dataList) {
            for (Object object : list) {
                System.out.print(object);
                System.out.print("\t\t\t");
            }
            System.out.println("\n");
        }
    }

}
