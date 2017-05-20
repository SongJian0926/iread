package com.web.webstart.base.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.web.webstart.base.exception.BusinessException;
import com.web.webstart.base.util.XaResult;

/**
 * @Autor: zhangl
 * @times: 2015-5-15下午07:07:05
 * 类的说明：
 **/
public interface ApiBaseService<T,T2> {
	
	/**
	 * 查询单条实体信息
	 * @param modelId
	 * @return 返回单个对象
	 * @throws BusinessException
	 */
	public XaResult<T> findOne(Long id) throws BusinessException;
	
	/**
	 * 分页查询状态非status的数据
	 * @param status
	 * @param filterParams
	 * @param pageable
	 * @return 返回对象集合
	 * @throws BusinessException
	 */
	public XaResult<List<T>> findListNEStatusByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	/**
	 * 分页查询状态status的数据
	 * @param status
	 * @param filterParams
	 * @param pageable
	 * @return 返回对象集合
	 * @throws BusinessException
	 */
	public XaResult<List<T>> findListEQStatusByFilter(Integer status, Map<String, Object> filterParams,
			Pageable pageable) throws BusinessException;
	
	/**
	 * 新增实体数据
	 * @param userId
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	public XaResult<T> createModel(T2 model) throws BusinessException;
	
	/**
	 * 修改实体名称
	 * @param tId
	 * @return
	 * @throws BusinessException
	 */
	public XaResult<T> updateModel(T2 model) throws BusinessException;
	
	/**
	 * 修改实体状态，可一次修改多条   3删除  -1锁定  1正常
	 * @param userId
	 * @param modelIds
	 * @param status
	 * @return 返回实体对象
	 * @throws BusinessException
	 */
	public XaResult<T> multiOperate(String ids,Integer status) throws BusinessException;


}
