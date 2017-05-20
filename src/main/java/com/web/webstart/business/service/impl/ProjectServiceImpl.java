package com.web.webstart.business.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.webstart.base.constant.XaConstant;
import com.web.webstart.base.util.DynamicSpecifications;
import com.web.webstart.base.util.SearchFilter;
import com.web.webstart.base.util.SearchFilter.Operator;
import com.web.webstart.base.util.XaDbStatus;
import com.web.webstart.base.util.XaResult;
import com.web.webstart.base.util.XaUtil;
import com.web.webstart.business.entity.Project;
import com.web.webstart.business.repository.ProjectRepository;
import com.web.webstart.business.service.ProjectService;

@Service("ProjectService")
@Transactional(readOnly = false)
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	private ProjectRepository projectRepository;
	
	@Transactional(readOnly = true)
	public XaResult<Page<Project>> findProjectByFilter(
			Map<String, Object> filterParams, Pageable pageable) {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		filters.put("status", new SearchFilter("status", Operator.NE,
				XaDbStatus.DB_STATUS_DELETE));// 不显示状态为删除的项
		Page<Project> page = projectRepository.findAll(DynamicSpecifications
				.bySearchFilter(filters.values(), Project.class), pageable);
		XaResult<Page<Project>> xr = new XaResult<Page<Project>>();
		xr.success(page);
		return xr;
	}

	public XaResult<Project> createProject(Project project) {

		XaResult<Project> xr = new XaResult<Project>();
		project.setStatus(XaDbStatus.DB_STATUS_NOMAL);
		Project obj = projectRepository.save(project);
		String createTime= XaUtil.getToDayStr();
		obj.setCreateTime(createTime);
		obj.setModifyTime(createTime);
		obj.setModifyDescription("初始创建");
		xr.success(obj);
		return xr;
	}

	public XaResult<Project> updateProject(Project project,Long modifyUser) {
		Project obj = projectRepository.findOne(project.getId());
		XaResult<Project> xr = new XaResult<Project>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setName(project.getName());
			obj.setIdentify(project.getIdentify());
			obj.setDescription(project.getDescription());
			obj.setModifyUser(modifyUser);
			obj.setModifyTime(XaUtil.getToDayStr());
			obj.setModifyDescription(project.getDescription());
			obj = projectRepository.save(obj);
			xr.success(obj);
		} else {
			xr.error(XaConstant.Message.object_not_find);
		}

		return xr;
	}

	public XaResult<Project> deleteProject(Long projectId,Long modifyUser) {
		Project obj = projectRepository.findByIdAndStatusNot(projectId,XaDbStatus.DB_STATUS_DELETE);
		XaResult<Project> xr = new XaResult<Project>();
		if (XaUtil.isNotEmpty(obj)) {
			obj.setStatus(XaDbStatus.DB_STATUS_DELETE);
			obj.setModifyTime(XaUtil.getToDayStr());
			obj.setModifyUser(modifyUser);
			obj.setModifyDescription("删除一条project");
			obj = projectRepository.save(obj);
			xr.success(obj);
		} else {
			xr.error(XaConstant.Message.object_not_find);
		}
		return xr;
	}

	@Override
	public XaResult<Project> findProject(Long projectId) {
		Project obj = projectRepository.findByIdAndStatusNot(projectId,XaDbStatus.DB_STATUS_DELETE);
		XaResult<Project> xr = new XaResult<Project>();
		if (XaUtil.isNotEmpty(obj)) {
			xr.success(obj);
		} else {
			xr.error(XaConstant.Message.object_not_find);
		}
		return xr;
	}

 
 
	
}
