	package com.web.iread.business.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.iread.business.entity.Animal;
import com.web.iread.business.repository.AnimalRepository;
import com.web.iread.business.service.AnimalService;
import com.web.webstart.base.constant.XaConstant;
import com.web.webstart.base.exception.BusinessException;
import com.web.webstart.base.service.impl.BaseService;
import com.web.webstart.base.util.DynamicSpecifications;
import com.web.webstart.base.util.SearchFilter;
import com.web.webstart.base.util.XaResult;
import com.web.webstart.base.util.XaUtil;
import com.web.webstart.base.util.SearchFilter.Operator;

@Service("AnimalService")
@Transactional(readOnly = false)
public class AnimalServiceImp extends BaseService<Animal> implements AnimalService{

	@Autowired
	private AnimalRepository animalRepository;
	
	/**
	 * 查询单条Animal信息
	 * @param tId
	 * @return 返回单个animal对象
	 * @throws BusinessException
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public XaResult<Animal> findOne(Long modelid) throws BusinessException {
		Animal obj=new Animal();
		if(modelid != 0)
		{
			obj = animalRepository.findByIdAndStatusNot(modelid, XaConstant.Status.delete);
		}
		XaResult<Animal> xr = new XaResult<Animal>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.setObject(obj);
		} else {
			throw new BusinessException(XaConstant.Message.object_not_find);
		}
		return xr;
	}
	/**
	 * 保存Animal信息
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public XaResult<Animal> saveOrUpdate(Animal model) throws BusinessException {
		Animal obj = null;
		if(XaUtil.isNotEmpty(model.getId())){
			obj = animalRepository.findOne(model.getId());
		}else{
			obj = new Animal();
		}
		obj.setAnimalName(model.getAnimalName());
		obj.setAnimalType(model.getAnimalType());
		obj.setAnimalNum(model.getAnimalNum());
		obj = animalRepository.save(obj);
		XaResult<Animal> xr = new XaResult<Animal>();
		xr.setObject(obj);
		return xr;
	}
	/**
	 * 修改Animal状态，可一次修改多条   3删除  -1锁定  1正常
	 * @param userId
	 * @param modelIds
	 * @param status
	 * @return 返回Animal对象
	 * @throws BusinessException
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public XaResult<Animal> multiOperate(String modelIds, Integer status)
			throws BusinessException {
		XaResult<Animal> xr = new XaResult<Animal>();
		if(status == null){
			status = XaConstant.Status.delete;
		}
		if(modelIds != null){
			String[] ids = modelIds.split(",");
			for(String id : ids){
				Animal obj = animalRepository.findByIdAndStatusNot(Long.parseLong(id), status);
				if (XaUtil.isNotEmpty(obj)) {
					obj.setStatus(status);
					obj = animalRepository.save(obj);
				} else {
					throw new BusinessException(XaConstant.Message.object_not_find);
				}
			}
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
	public XaResult<Page<Animal>> findListNEStatusByFilter(
			Integer status, Map<String, Object> filterParams, Pageable pageable) throws BusinessException {
		
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		if(status == null){// 默认显示非删除的所有数据
			status = XaConstant.Status.delete;
		}
		filters.put("status", new SearchFilter("status", Operator.NE, status));
		Page<Animal> page = animalRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Animal.class), pageable);
		XaResult<Page<Animal>> xr = new XaResult<Page<Animal>>();
		xr.setObject(page);
		return xr;
	}

	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public void export(HttpServletResponse response, Integer status,
			Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public XaResult<Page<Animal>> findListEQStatusByFilter(Integer status,
			Map<String, Object> filterParams, Pageable pageable)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
