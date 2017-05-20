package com.web.iread.business.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.iread.business.entity.Animal;
import com.web.iread.business.service.AnimalService;
import com.web.webstart.base.controller.BaseController;
import com.web.webstart.base.exception.BusinessException;
import com.web.webstart.base.util.WebUitl;
import com.web.webstart.base.util.XaResult;

@Controller
@RequestMapping("/cms/animal")
public class AnimalController  extends BaseController
{
	@Autowired
	private AnimalService animalService;
	

	//按照id查询
	@ResponseBody
	@RequestMapping(value="findAnimalById",method=RequestMethod.POST)
	public XaResult<Animal> findAnimalById(
			@RequestParam Long modelId
		) throws BusinessException{
			return animalService.findOne(modelId);
		}
	
	//批量删除
	@ResponseBody
	@RequestMapping(value="operateAnimalByIds",method=RequestMethod.POST)
	public XaResult<Animal> operateAnimalByIds(
			HttpServletRequest request,
			@RequestParam String modelIds,
			@RequestParam(defaultValue = "3") Integer status
		) throws BusinessException{
			return animalService.multiOperate(modelIds,status);
		}
	
	//保存（插入的时候，修改时候）
	@ResponseBody
	@RequestMapping(value="saveAnimal",method=RequestMethod.POST )
	public XaResult<Animal> saveAnimal(
			Animal model
		) throws BusinessException{
			return animalService.saveOrUpdate(model);
		}
	
	
	/**
	 * @Title: findBookNEStatusPage
	 * @Description: 分页查询Animal	 * @param nextPage
	 * @param pageSize
	 * @param status		//默认查询非删除3的数据,具体类型参照XaConstant.Status
	 * @param sortDate
	 * @param jsonFilter
	 * @return    
	 */
	@ResponseBody
	@RequestMapping(value="findAnimalNEStatusPage",method=RequestMethod.POST)
	public XaResult<Page<Animal>> findBookNEStatusPage(
		@RequestParam(defaultValue = "0") Integer nextPage,
		@RequestParam(defaultValue = "10") Integer pageSize,
		@RequestParam(defaultValue = "3") Integer status,
		@RequestParam(defaultValue = "[{property:'createTime',direction:'DESC'}]") String sortData,
		//过滤字段,字段名:jsonFilter,选填,默认:{},示例:{'search_EQ_field1':'value1','search_EQ_field2':'value2'},字段名称拼接规则search_为固定查询标识,EQ为等于,filed为字段名
		//EQ等于, IN包含, ISNULL空, LIKE, GT大于, LT小于, GTE大于等于, LTE小于等于, NE不等于,LIKEIGNORE非like 
		@RequestParam(defaultValue = "{}") String jsonFilter
	) throws BusinessException{
		Pageable pageable = WebUitl.buildPageRequest(nextPage, pageSize, sortData);
		Map<String,Object> filterParams =  WebUitl.getParametersStartingWith(jsonFilter, "search_");
		return animalService.findListNEStatusByFilter(status, filterParams, pageable);		
	}
		
}
