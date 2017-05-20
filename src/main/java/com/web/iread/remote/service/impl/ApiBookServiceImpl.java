package com.web.iread.remote.service.impl;

import com.web.iread.business.entity.Book;
import com.web.iread.remote.vo.BookVo;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.web.iread.business.repository.BookRepository;
import com.web.iread.remote.service.ApiBookService;
import com.web.webstart.base.constant.XaConstant;
import com.web.webstart.base.exception.BusinessException;
import com.web.webstart.base.util.DynamicSpecifications;
import com.web.webstart.base.util.SearchFilter;
import com.web.webstart.base.service.impl.BaseService;
import com.web.webstart.base.util.SearchFilter.Operator;
import com.web.webstart.base.util.XaResult;
import com.web.webstart.base.util.XaUtil;

/**
 * @Autor: zhangl
 * @times: 2015-5-15下午06:46:35
 * 类的说明：前端APIBook接口实现类
 **/
@Service("ApiBookService")
@Transactional(readOnly = false)
public class ApiBookServiceImpl extends BaseService<Book> implements ApiBookService{

	@Autowired
	BookRepository bookRepository;
	
	@Override
	public XaResult<BookVo> findOne(Long tId) throws BusinessException {
		Book obj = bookRepository.findByIdAndStatusNot(tId,XaConstant.Status.delete);
		XaResult<BookVo> xr = new XaResult<BookVo>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(JSON.parseObject(JSON.toJSONString(obj),BookVo.class));
			xr.getObject().setId(obj.getId());
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<List<BookVo>> findListNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		if(status == null){// 默认显示非删除的所有数据
			status = XaConstant.Status.delete;
		}
		filters.put("status", new SearchFilter("status", Operator.NE, status));
		Page<Book> page = bookRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Book.class), pageable);
		XaResult<List<BookVo>> xr = new XaResult<List<BookVo>>();
		xr.setObject(JSON.parseArray(JSON.toJSONString(page.getContent()), BookVo.class));
		for(int i=0;i<page.getContent().size();i++){
			xr.getObject().get(i).setId(page.getContent().get(i).getId());
		}
		return xr;
	}

	@Override
	public XaResult<List<BookVo>> findListEQStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		if(status == null){// 默认显示正常数据
			status = XaConstant.Status.valid;
		}
		filters.put("status", new SearchFilter("status", Operator.EQ, status));
		Page<Book> page = bookRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Book.class), pageable);
		XaResult<List<BookVo>> xr = new XaResult<List<BookVo>>();
		xr.setObject(JSON.parseArray(JSON.toJSONString(page.getContent()), BookVo.class));
		for(int i=0;i<page.getContent().size();i++){
			xr.getObject().get(i).setId(page.getContent().get(i).getId());
		}
		return xr;
	}

	@Override
	public XaResult<BookVo> multiOperate(String modelIds,
			Integer status) throws BusinessException {
		XaResult<BookVo> xr = new XaResult<BookVo>();
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
					xr.setObject(JSON.parseObject(JSON.toJSONString(obj), BookVo.class));
					xr.getObject().setId(obj.getId());
				} else {
					throw new BusinessException(XaConstant.Message.object_not_find);
				}
			}
		}
		return xr;
	}

	@Override
	public XaResult<BookVo> createModel(Book model)
			throws BusinessException {
		XaResult<BookVo> xr = new XaResult<BookVo>();
		Book obj = bookRepository.save(model);
		xr.setObject(JSON.parseObject(JSON.toJSONString(obj), BookVo.class));
		xr.getObject().setId(obj.getId());
		return xr;
	}

	@Override
	public XaResult<BookVo> updateModel(Book model)
			throws BusinessException {
		Book obj = bookRepository.findOne(model.getId());
		XaResult<BookVo> xr = new XaResult<BookVo>();
		if (XaUtil.isNotEmpty(obj)) {
		obj.setBookName(model.getBookName());
		obj.setAutor(model.getAutor());
		obj.setNavication(model.getNavication());
		obj.setAutorDesc(model.getAutorDesc());
			obj = bookRepository.save(obj);
			xr.setObject(JSON.parseObject(JSON.toJSONString(obj), BookVo.class));
			xr.getObject().setId(obj.getId());
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}

}
