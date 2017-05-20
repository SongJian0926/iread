package com.web.iread.business.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.iread.business.entity.AnimalDescribe;
import com.web.iread.business.repository.AnimalDescribeRepository;
import com.web.iread.business.service.AnimalDescribeService;
import com.web.webstart.base.exception.BusinessException;
import com.web.webstart.base.util.MyPage;
import com.web.webstart.base.util.XaResult;


@Service("AnimalDescribeService")
@Transactional(readOnly = false)
public class AnimalDescribeServiceImp implements AnimalDescribeService{
	
	@Autowired
	private AnimalDescribeRepository  animalDescribeRepository;

	@Override
	public XaResult<AnimalDescribe> findOne(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*public XaResult<Page<DescribeVo>> findListNEStatusByFilter(Integer status,
			Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		
		if(status == null){// 默认显示非删除的所有数据
			status = XaConstant.Status.delete;
		}
		
		filters.put("status", new SearchFilter("status", Operator.NE, status));
		//filterParams.get("EQ_bookName");
		System.out.println(animalDescribeRepository.findListAnimalManager().get(0).getStatus());
		//System.out.println(animalDescribeRepository.finListAnimalPage());
		Page<DescribeVo> page=new MyPage<DescribeVo>(pageable.getPageNumber(),pageable.getPageSize(),animalDescribeRepository.findListAnimalManager(),animalDescribeRepository.finListAnimalPage());
		
		XaResult<Page<DescribeVo>> xa= new XaResult<Page<DescribeVo>>();
		
		xa.setObject(page);
		
		return xa;
	}*/
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public XaResult<Page<AnimalDescribe>> findListNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		Page<AnimalDescribe> page=new MyPage<AnimalDescribe>(pageable.getPageNumber(),pageable.getPageSize(),animalDescribeRepository.findListAnimalManager(),animalDescribeRepository.finListAnimalPage());
		
		XaResult<Page<AnimalDescribe>> xa= new XaResult<Page<AnimalDescribe>>();
		
		xa.setObject(page);
		
		return xa;
	}

	
	
	
	@Override
	public XaResult<Page<AnimalDescribe>> findListEQStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XaResult<AnimalDescribe> saveOrUpdate(AnimalDescribe t)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XaResult<AnimalDescribe> multiOperate(String ids, Integer status)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void export(HttpServletResponse response, Integer status,
			Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	

	
	
	
	
	
	
}
