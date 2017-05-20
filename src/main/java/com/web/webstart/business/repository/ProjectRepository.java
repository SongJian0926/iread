package com.web.webstart.business.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.web.webstart.business.entity.Project;


public interface ProjectRepository extends
		PagingAndSortingRepository<Project, Long>,
		JpaSpecificationExecutor<Project> {
	public Project findByIdAndStatusNot(Long id,Integer status);
}
