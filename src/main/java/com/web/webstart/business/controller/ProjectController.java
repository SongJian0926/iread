package com.web.webstart.business.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.webstart.base.util.WebUitl;
import com.web.webstart.base.util.XaResult;
import com.web.webstart.base.util.XaUtil;
import com.web.webstart.base.util.ZipUtils;
import com.web.webstart.business.entity.Project;
import com.web.webstart.business.service.ProjectService;
import com.web.webstart.business.service.XaVelocityService;

/**
 * @Title: ModelController.java
 * @Package com.web.webstart.business.controller
 * @Description: 模型控制器
 * @author eason.zt
 * @date 2015年3月23日 下午1:00:00
 * @version V1.0
 */
@Controller
@RequestMapping("project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private XaVelocityService xaVelocityService;

	private static String PROJECTS_PATH = "D://template//projects//";

	/**
	 * @Title: findProjectPage
	 * @Description: 分页查询project
	 * @param nextPage
	 * @param pageSize
	 * @param sortDate
	 * @param jsonFilter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public XaResult<Page<Project>> findProjectPage(
			@RequestParam(defaultValue = "0") Integer nextPage,
			@RequestParam(defaultValue = "10") Integer pageSize,
			String sortDate, String jsonFilter) {
		
		Pageable pageable = WebUitl.buildPageRequest(nextPage, pageSize,sortDate);
		Map<String, Object> filterParams = WebUitl.getParametersStartingWith(jsonFilter, "search_");
		return projectService.findProjectByFilter(filterParams, pageable);
	}

	/**
	 * @Title: findModelById
	 * @Description: 根据ID查找单条实体
	 * @param modelId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "{modelId}", method = RequestMethod.GET)
	public XaResult<Project> findProjectById(@PathVariable Long modelId) {
		return projectService.findProject(modelId);
	}

	/**
	 * @Title: save
	 * @Description: 新增一条实体
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public XaResult<Project> save(@RequestBody Project model,
			HttpServletRequest request) {
		Long userId = WebUitl.getCmsLoginedUserId(request);
		model.setModifyUser(userId);
		model.setCreateUser(userId);
		return projectService.createProject(model);
	}

	/**
	 * @Title: updateModel
	 * @Description: 修改一条实体
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public XaResult<Project> updateProject(@RequestBody Project model,
			HttpServletRequest request) {
		Long userId = WebUitl.getCmsLoginedUserId(request);
		return projectService.updateProject(model, userId);
	}

	/**
	 * @Title: deleteModelById
	 * @Description: 删除一个实体
	 * @param modelId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "{modelId}", method = RequestMethod.DELETE)
	public XaResult<Project> deleteProjectById(@PathVariable Long modelId,
			HttpServletRequest request) {
		Long userId = WebUitl.getCmsLoginedUserId(request);
		return projectService.deleteProject(modelId, userId);
	}

	/**
	 * @Title: deleteModelById
	 * @Description: 发布一个项目,将项目的实体和属性打包成zip文件下载下来
	 * @param modelId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "pub/{projectId}", method = RequestMethod.GET)
	public void pubProjectById(@PathVariable Long projectId,
			HttpServletRequest request, HttpServletResponse response) {
		OutputStream ops = null;
		try {
			ops = response.getOutputStream();
			Map<String, String> fm = null;
			fm = xaVelocityService.publishProject(projectId);
			if (XaUtil.isEmpty(fm)) {
				return;
			}
			Project prj = projectService.findProject(projectId).getObject();
			String root = PROJECTS_PATH + prj.getIdentify();
			XaUtil.makeDir(new File(root + "/com/web/"
					+ prj.getIdentify().toLowerCase() + "/business/entity/"));
			XaUtil.makeDir(new File(root + "/com/web/"
					+ prj.getIdentify().toLowerCase() + "/business/controller/"));
			XaUtil.makeDir(new File(root + "/com/web/"
					+ prj.getIdentify().toLowerCase() + "/business/repository/"));
			XaUtil.makeDir(new File(root + "/com/web/"
					+ prj.getIdentify().toLowerCase()
					+ "/business/service/impl/"));
			XaUtil.makeDir(new File(root + "/com/web/"
					+ prj.getIdentify().toLowerCase()
					+ "/test/"));
			Set<String> fset = fm.keySet();
			for (String fn : fset) {
				wirteToFile(prj.getIdentify().toLowerCase(), root, fn,
						fm.get(fn));
			}
			String fileName = prj.getIdentify() + ".zip";
			try {
				ZipUtils.zip(root, PROJECTS_PATH + fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			File zip = new File(PROJECTS_PATH + fileName);
			FileInputStream in = new FileInputStream(zip);
			byte[] buffer = new byte[(int) zip.length()];
			in.read(buffer);
			ops.write(buffer);
			response.setContentType("application/zip; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName);
			ops.flush();
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ops != null) {
					ops.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// return projectService.deleteProject(modelId,userId);
	}
	
	private void wirteToFile(String projIde, String rootpath, String fileName,
			String content) throws IOException {
		if(fileName.indexOf("Vo.java") > -1){
			XaUtil.WriteFile(content, rootpath + "/com/web/" + projIde
					+ "/remote/vo/", fileName);
		}else if (fileName.indexOf("Api") > -1 && fileName.indexOf("Controller.java") > -1) {
			XaUtil.WriteFile(content, rootpath + "/com/web/" + projIde
					+ "/remote/controller/", fileName);
		}else if (fileName.indexOf("Api") > -1 && fileName.indexOf("Service.java") > -1) {
			XaUtil.WriteFile(content, rootpath + "/com/web/" + projIde
					+ "/remote/service/", fileName);
		}else if (fileName.indexOf("Api") > -1 && fileName.indexOf("ServiceImpl.java") > -1) {
			XaUtil.WriteFile(content, rootpath + "/com/web/" + projIde
					+ "/remote/service/impl/", fileName);
		}else if (fileName.indexOf("Test") > -1 && fileName.indexOf("Controller.java") > -1) {
			XaUtil.WriteFile(content, rootpath + "/com/web/" + projIde
					+ "/test/controller/", fileName);
		}else if (fileName.indexOf("Test") > -1 && fileName.indexOf("Service.java") > -1) {
			XaUtil.WriteFile(content, rootpath + "/com/web/" + projIde
					+ "/test/service/", fileName);
		}else if (fileName.indexOf("ServiceImpl.java") > -1) {
			XaUtil.WriteFile(content, rootpath + "/com/web/" + projIde
					+ "/business/service/impl/", fileName);
		} else if (fileName.indexOf("Service.java") > -1) {
			XaUtil.WriteFile(content, rootpath + "/com/web/" + projIde
					+ "/business/service", fileName);
		} else if (fileName.indexOf("Controller.java") > -1) {
			XaUtil.WriteFile(content, rootpath + "/com/web/" + projIde
					+ "/business/controller/", fileName);
		} else if (fileName.indexOf("Repository.java") > -1) {
			XaUtil.WriteFile(content, rootpath + "/com/web/" + projIde
					+ "/business/repository/", fileName);
		} else if (fileName.indexOf("List.jsp") > -1) {
			XaUtil.WriteFile(content, rootpath + "/jsp/", fileName);
		} else if (fileName.indexOf("Edit.jsp") > -1) {
			XaUtil.WriteFile(content, rootpath + "/jsp/", fileName);
		} else {
			XaUtil.WriteFile(content, rootpath + "/com/web/" + projIde
					+ "/business/entity/", fileName);
		}


	}
}
