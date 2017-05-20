package com.web.webstart.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.web.webstart.business.entity.Property;

/**
 * @Title: PropertyRepository.java
 * @Package com.web.webstart.business.repository
 * @Description: 属性接口
 * @author eason.zt
 * @date 2014年10月15日 上午11:51:31
 * @version V1.0
 */
public interface PropertyRepository extends JpaSpecificationExecutor<Property>,
		PagingAndSortingRepository<Property, Long> {

	@Query(" from Property p where p.modelId=?1")
	public List<Property> findByModelId(Long modelId);
}

