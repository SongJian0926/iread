package com.web.webstart.business.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web.webstart.base.util.XaResult;
import com.web.webstart.business.entity.Project;

public interface ProjectService {
	
	public XaResult<Project> findProject(Long modelId);
	
	public XaResult<Page<Project>> findProjectByFilter(Map<String, Object> filterParams,
			Pageable pageable);
	
	public XaResult<Project> createProject(Project model);
	
	public XaResult<Project> updateProject(Project model,Long modifyUser);
	
	public XaResult<Project> deleteProject(Long modelId,Long modifyUser);
}
