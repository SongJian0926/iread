package com.web.webstart.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.web.webstart.business.entity.Model;


public interface ModelRepository extends
		PagingAndSortingRepository<Model, Long>,
		JpaSpecificationExecutor<Model> {
	public Model findByIdAndStatusNot(Long id,Integer status);
	public List<Model> findByProjectIdAndStatusNot(Long projectId,Integer status);
}
