package com.web.iread.business.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import com.web.iread.business.entity.Book;
import com.web.webstart.base.exception.BusinessException;
import com.web.webstart.base.service.BaseServiceInterFace;

public interface BookService extends BaseServiceInterFace<Book>{
	
	public void export(HttpServletResponse response,Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException;

}
