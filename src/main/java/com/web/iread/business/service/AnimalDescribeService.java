package com.web.iread.business.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import com.web.iread.business.entity.AnimalDescribe;
import com.web.webstart.base.exception.BusinessException;
import com.web.webstart.base.service.BaseServiceInterFace;

public interface AnimalDescribeService extends BaseServiceInterFace<AnimalDescribe>
{
	public void export(HttpServletResponse response,Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException;
	
	
}
