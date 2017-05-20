package com.web.webstart.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.webstart.base.constant.XaConstant;
import com.web.webstart.base.entity.XaCmsResource;
import com.web.webstart.base.entity.XaCmsRole;
import com.web.webstart.base.entity.XaCmsRoleResource;
import com.web.webstart.base.entity.XaCmsUserRole;
import com.web.webstart.base.repository.XaCmsResourceRepository;
import com.web.webstart.base.repository.XaCmsRoleRepository;
import com.web.webstart.base.repository.XaCmsRoleResourceRepository;
import com.web.webstart.base.repository.XaCmsUserRoleRepository;
import com.web.webstart.base.service.XaCmsRoleService;
import com.web.webstart.base.util.DynamicSpecifications;
import com.web.webstart.base.util.SearchFilter;
import com.web.webstart.base.util.XaUtil;
import com.web.webstart.base.vo.NodeStatus;
import com.web.webstart.base.vo.TreeNode;

/**
 * @Title: XaCmsRoleServiceImpl.java
 * @Package com.web.shengmilu.business.service.impl
 * @Description: 角色服务类
 * @author eason.zt
 * @date 2014年8月2日 上午10:25:17
 * @version V1.0
 */
@Service("xaCmsRoleService")
public class XaCmsRoleServiceImpl extends BaseService<XaCmsRole> implements XaCmsRoleService {

	private static final Logger log=Logger.getLogger(XaCmsRoleServiceImpl.class);
	@Autowired
	private XaCmsRoleRepository xaCmsRoleRepository;
	
	@Autowired
	private XaCmsRoleResourceRepository xaCmsRoleResourceRepository;
	
	@Autowired
	private XaCmsResourceRepository xaCmsResourceRepository;
	
	@Autowired
	private XaCmsUserRoleRepository xaCmsUserRoleRepository;
	
	
	@Transactional(readOnly=false)
	public XaCmsRole saveXaCmsRole(XaCmsRole xaCmsRole,String resourceIds) {
		xaCmsRole.setStatus(XaConstant.RoleStatus.status_normal);
		xaCmsRole =xaCmsRoleRepository.save(xaCmsRole);
		createRoleResource(xaCmsRole.getRoleId(), resourceIds);
		return xaCmsRole;
	}

	/**
	 * @Title: createRoleResource
	 * @Description: 创建角色和资源关系
	 * @param roleId
	 * @param resourceIds    
	 */
	@Transactional(readOnly=false)
	private void createRoleResource(long roleId, String resourceIds) {
		String[] ids=resourceIds.split(",");
		//拼接新增的SQL语句,查询父资源的SQL语句
		StringBuffer sql = new StringBuffer();
		StringBuffer parentSql = new StringBuffer();
		parentSql.append("select parent_id from tb_cms_resource where parent_id is not null and resource_id in ");
		sql.append("insert into tb_cms_role_resource (resource_id, role_id) values ");
		for (int i = 0; i < ids.length; i++) {
			//如果是第一个资源ID，拼接查询语句in的第一个括号
			if(i == 0){
				parentSql.append("(");
			}
			//拼接查询的资源id内容
			parentSql.append(ids[i]);
			//拼接新增的资源内容
			if(!"0".equals(ids[i])){
				sql.append("(").append(ids[i]).append(",").append(roleId).append(")");
			}
			//如果不是最后一个资源ID，并且下一个资源ID不为0时新增语句拼接逗号链接符
			if(i != ids.length - 1 && !"0".equals(ids[i+1])){
				sql.append(",");
			}
			//如果不是最后一个资源ID，查询语句拼接逗号链接符
			if(i != ids.length - 1){
				parentSql.append(",");
			}
			//如果是最后一个资源ID，拼接查询语句的最后一个括号
			if(i == ids.length - 1){
				parentSql.append(")");
			}
		}
		parentSql.append(" GROUP BY parent_id");
		List<Object[]> list = this.query(parentSql.toString(), null, null);
		//把查询的父资源信息也添加到表中
		if(XaUtil.isNotEmpty(list) && list.size() > 0){
			for(int i=0;i<list.size();i++){
				if(!sql.toString().contains("("+list.get(i)+","+roleId+")")){
					sql.append(",");
					sql.append("(").append(Long.valueOf(list.get(i)+"")).append(",").append(roleId).append(")");
				}
			}
		}
		//执行添加语句
		this.insert(sql.toString());
	}

	@Transactional(readOnly=false)
	public void delXaCmsRole(String rids) {
		String[] ids=rids.split(",");
		for (int i = 0; i < ids.length; i++) {	
			long roleId=Long.parseLong(ids[i]);
			XaCmsRole xaCmsRole=xaCmsRoleRepository.findOne(roleId);
			if(xaCmsRole!=null){
				xaCmsRole.setStatus(XaConstant.RoleStatus.status_delete);
				xaCmsRoleRepository.save(xaCmsRole);
				List<XaCmsUserRole> urList = xaCmsUserRoleRepository.findXacmsUserRoleByRoleId(roleId);
				List<XaCmsRoleResource> rrList=xaCmsRoleResourceRepository.findRoleResourceByRoleId(roleId);
				if(urList!=null && urList.size()>0) xaCmsUserRoleRepository.delete(urList);
				if(XaUtil.isNotEmpty(rrList)) xaCmsRoleResourceRepository.delete(rrList);
				log.info("删除角色："+xaCmsRole.getRoleName()+" 成功");
			}
		}
	}

	@Transactional(readOnly=false)
	public XaCmsRole updateXaCmsRole(XaCmsRole xaCmsRole,String resourceIds) {
		XaCmsRole oldXaCmsRole=xaCmsRoleRepository.findOne(xaCmsRole.getRoleId());
		if(oldXaCmsRole!=null){
			oldXaCmsRole.setRoleName(xaCmsRole.getRoleName());
			oldXaCmsRole.setRoleDesc(xaCmsRole.getRoleDesc());
			xaCmsRole =xaCmsRoleRepository.save(oldXaCmsRole);
		}
		if(!XaUtil.isEmpty(resourceIds)){
			//删除该角色原有的资源
			List<Object> delparams = new ArrayList<Object>();
			String del_sql = "delete from tb_cms_role_resource where role_id=?";
			delparams.add(xaCmsRole.getRoleId());
			this.delete(del_sql, delparams);
			//添加现有的资源
			createRoleResource(xaCmsRole.getRoleId(),resourceIds);
		}
		return xaCmsRole;
	}

	public Page<XaCmsRole> findXaCmsRoleByConditon(
			Map<String, Object> filterParams, Pageable pageable) {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		return xaCmsRoleRepository.findAll(DynamicSpecifications.bySearchFilter(filters.values(), XaCmsRole.class),pageable);
	}

	public int saveRoleResourceList(long rid, Integer[] resourceIds) {
		return 0;
	}

	public List<XaCmsResource> getMyResourceByRoldId(long rid) {
		return null;
	}

	public TreeNode getResourceTreeNode(long roleId) {
		//当roleId为0时，表示新增，新增时，所有的资源都不会被选中。
		List<XaCmsRoleResource>  roleResourceList=xaCmsRoleResourceRepository.findRoleResourceByRoleId(roleId);
		List<Long>  myResourceIdList=new ArrayList<Long>();	//存放当前角色的资源ID，如1，2，3，4
		for (XaCmsRoleResource xaCmsRoleResource : roleResourceList) {
			long resourceId=xaCmsRoleResource.getResourceId();
			if(!myResourceIdList.contains(resourceId)) myResourceIdList.add(resourceId);
		}
		
		
		List<TreeNode> children1=new ArrayList<TreeNode>();	//一级资源
		
		List<XaCmsResource>  pageResourceList=xaCmsResourceRepository.findParentResourceByStatus( XaConstant.ResourcesStatus.status_normal,XaConstant.ResourceShowType.page_level);	//页面级资源
		List<XaCmsResource> rootMenuResourceList=xaCmsResourceRepository.findParentResourceByStatus(XaConstant.ResourcesStatus.status_normal,XaConstant.ResourceShowType.menu_level);	//一级菜单资源
		List<XaCmsResource> firstResourceList=new ArrayList<XaCmsResource>();
		firstResourceList.addAll(pageResourceList);
		firstResourceList.addAll(rootMenuResourceList);
		for (XaCmsResource xaCmsResource : firstResourceList) {
			
			
			boolean firstNsSelected=true;   //如果父资源下所有的子资源都勾选了，则该父资源才能被勾选,一级资源是否被勾选标识位
			List<TreeNode> children2=new ArrayList<TreeNode>();		//菜单级资源
			List<XaCmsResource> secondResourceList=xaCmsResourceRepository.findResourceByParentIdAndStatus(xaCmsResource.getResourceId(), XaConstant.ResourcesStatus.status_normal);	//获取二级菜单
			for (XaCmsResource xaCmsResource2 : secondResourceList) {
				
				List<TreeNode> children3=new ArrayList<TreeNode>();		//action级资源
				List<XaCmsResource> thirdResourceList=xaCmsResourceRepository.findResourceByParentIdAndStatus(xaCmsResource2.getResourceId(), XaConstant.ResourcesStatus.status_normal);	//获取三级资源
				boolean secondNsSelected=true;
				for (XaCmsResource xaCmsResource3 : thirdResourceList) {
					//如果子节点有一个不包含，所父节点不能被选中。
					if(!myResourceIdList.contains(xaCmsResource3.getResourceId())){
						secondNsSelected=false;
					}
					
					NodeStatus thirdNs=new NodeStatus(true,false,false);
					if(myResourceIdList.contains(xaCmsResource3.getResourceId())) thirdNs.setSelected(true);
					String icon3="";
					if(XaConstant.ResourceShowType.page_level==xaCmsResource3.getShowType()){
						icon3=XaConstant.TreeNodeIcon.html_24;
					}else if(XaConstant.ResourceShowType.menu_level==xaCmsResource3.getShowType()){
						icon3=XaConstant.TreeNodeIcon.menu_24;
					}else if(XaConstant.ResourceShowType.button_level==xaCmsResource3.getShowType()){
						icon3=XaConstant.TreeNodeIcon.action_24;
					}
					TreeNode thirdTn=new TreeNode(xaCmsResource3.getResourceId()+"",xaCmsResource3.getResourceName(),thirdNs,icon3,null,null,null);
					children3.add(thirdTn);
				}
				if(!myResourceIdList.contains(xaCmsResource2.getResourceId())){
					firstNsSelected=false;
				}
				
				
				//构造二级
				NodeStatus secondNs=new NodeStatus(true,false,false);
				
				
				if(myResourceIdList.contains(xaCmsResource2.getResourceId()) && secondNsSelected) secondNs.setSelected(true);
				String icon2="";
				if(XaConstant.ResourceShowType.page_level==xaCmsResource2.getShowType()){
					icon2=XaConstant.TreeNodeIcon.html_24;
				}else if(XaConstant.ResourceShowType.menu_level==xaCmsResource2.getShowType()){
					icon2=XaConstant.TreeNodeIcon.menu_24;
				}else if(XaConstant.ResourceShowType.button_level==xaCmsResource2.getShowType()){
					icon2=XaConstant.TreeNodeIcon.action_24;
				}
				TreeNode secondTn=new TreeNode(xaCmsResource2.getResourceId()+"",xaCmsResource2.getResourceName(),secondNs,icon2,children3,null,null);
				children2.add(secondTn);
			}
			
			
			
			
			NodeStatus firstNs=new NodeStatus(true,false,false);
			if(myResourceIdList.contains(xaCmsResource.getResourceId()) && firstNsSelected ) firstNs.setSelected(true);
			
			String icon1="";
			if(XaConstant.ResourceShowType.page_level==xaCmsResource.getShowType()){
				icon1=XaConstant.TreeNodeIcon.html_24;
			}else if(XaConstant.ResourceShowType.menu_level==xaCmsResource.getShowType()){
				icon1=XaConstant.TreeNodeIcon.menu_24;
			}else if(XaConstant.ResourceShowType.button_level==xaCmsResource.getShowType()){
				icon1=XaConstant.TreeNodeIcon.action_24;
			}
			TreeNode firstTn=new TreeNode(xaCmsResource.getResourceId()+"", xaCmsResource.getResourceName(), firstNs, icon1, children2,null,null);
			children1.add(firstTn);
		}
		
		
		//资源树,根目录，默认不会选中
		NodeStatus rootStatus=new NodeStatus(true,false,false);
		TreeNode root=new TreeNode("0","资源列表",rootStatus,"",children1,null,null);
		return root;
	}

	public XaCmsRole findRoleById(long roleId) {
		return xaCmsRoleRepository.findOne(roleId);
	}

	public XaCmsRole getRoleByName(String roleName) {
		
		return xaCmsRoleRepository.findRoleByRoleName(roleName);
	}
	
	

}

