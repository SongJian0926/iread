package com.web.iread.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.iread.business.entity.Book;
import com.web.iread.business.repository.BookRepository;
import com.web.iread.business.service.BookService;
import com.web.webstart.base.constant.XaConstant;
import com.web.webstart.base.excelUtil.ExcelColumn;
import com.web.webstart.base.excelUtil.ExcelHead;
import com.web.webstart.base.excelUtil.ExcelHelper;
import com.web.webstart.base.exception.BusinessException;
import com.web.webstart.base.service.impl.BaseService;
import com.web.webstart.base.util.DynamicSpecifications;
import com.web.webstart.base.util.SearchFilter;
import com.web.webstart.base.util.SearchFilter.Operator;
import com.web.webstart.base.util.XaResult;
import com.web.webstart.base.util.XaUtil;

@Service("BookService")
@Transactional(readOnly = false)
public class BookServiceImpl extends BaseService<Book> implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
	/**
	 * 查询单条Book信息
	 * @param tId
	 * @return 返回单个Book对象
	 * @throws BusinessException
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public XaResult<Book> findOne(Long modelId) throws BusinessException {
		Book obj = new Book();
		if(modelId != 0){
			obj = bookRepository.findByIdAndStatusNot(modelId,XaConstant.Status.delete);
		}
		XaResult<Book> xr = new XaResult<Book>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	/**
	 * 分页查询状态非status的Book数据
	 * @param status
	 * @param filterParams
	 * @param pageable
	 * @return 返回对象Book集合
	 * @throws BusinessException
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public XaResult<Page<Book>> findListNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		if(status == null){// 默认显示非删除的所有数据
			status = XaConstant.Status.delete;
		}
		filters.put("status", new SearchFilter("status", Operator.NE, status));
		Page<Book> page = bookRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Book.class), pageable);
		XaResult<Page<Book>> xr = new XaResult<Page<Book>>();
		xr.setObject(page);
		return xr;
	}

	/**
	 * 分页查询状态status的Book数据
	 * @param status
	 * @param filterParams
	 * @param pageable
	 * @return 返回对象Book集合
	 * @throws BusinessException
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public XaResult<Page<Book>> findListEQStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		if(status == null){// 默认显示正常数据
			status = XaConstant.Status.valid;
		}
		filters.put("status", new SearchFilter("status", Operator.EQ, status));
		Page<Book> page = bookRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Book.class), pageable);
		XaResult<Page<Book>> xr = new XaResult<Page<Book>>();
		xr.setObject(page);
		return xr;
	}

	/**
	 * 保存Book信息
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor = Exception.class)
	public XaResult<Book> saveOrUpdate(Book model) throws BusinessException {
		Book obj = null;
		if(XaUtil.isNotEmpty(model.getId())){
			obj = bookRepository.findOne(model.getId());
		}else{
			obj = new Book();
		}
		obj.setBookName(model.getBookName());
		obj.setAutor(model.getAutor());
		obj.setNavication(model.getNavication());
		obj.setAutorDesc(model.getAutorDesc());
		obj = bookRepository.save(obj);
		XaResult<Book> xr = new XaResult<Book>();
		xr.setObject(obj);
		return xr;
	}

	/**
	 * 修改Book状态，可一次修改多条   3删除  -1锁定  1正常
	 * @param userId
	 * @param modelIds
	 * @param status
	 * @return 返回Book对象
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor = Exception.class)
	public XaResult<Book> multiOperate(
			String modelIds,Integer status) throws BusinessException {
		XaResult<Book> xr = new XaResult<Book>();
		if(status == null){
			status = XaConstant.Status.delete;
		}
		if(modelIds != null){
			String[] ids = modelIds.split(",");
			for(String id : ids){
				Book obj = bookRepository.findByIdAndStatusNot(Long.parseLong(id),status);
				if (XaUtil.isNotEmpty(obj)) {
					obj.setStatus(status);
					obj = bookRepository.save(obj);
				} else {
					throw new BusinessException(XaConstant.Message.object_not_find);
				}
			}
		}
		return xr;
	}

	@Override
	public void export(HttpServletResponse response,Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException{
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		if(status == null){// 默认显示非删除的所有数据
			status = XaConstant.Status.delete;
		}
		filters.put("status", new SearchFilter("status", Operator.NE, status));
		Page<Book> page = bookRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Book.class), pageable);
		List<Book> books = page.getContent();
		// excel结构
        List<ExcelColumn> excelColumns = new ArrayList<ExcelColumn>();
        excelColumns.add(new ExcelColumn(0, "", "序号"));
        excelColumns.add(new ExcelColumn(1, "bookName", "书籍名称", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(2, "autor", "作者", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(3, "navication", "国籍", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(4, "autorDesc", "作者介绍", Cell.CELL_TYPE_STRING));
        ExcelHead head = new ExcelHead();
        head.setRowCount(1); // 模板中头部所占行数
        head.setColumns(excelColumns);  // 列的定义
//        head.setColumnsConvertMap(excelHeadConvertMap); // 列的转换
        ExcelHelper.getInstanse().exportExcelFile(response,head, books);
	}
	
}
