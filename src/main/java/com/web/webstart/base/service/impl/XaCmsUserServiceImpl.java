package com.web.webstart.base.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.web.webstart.base.constant.XaConstant;
import com.web.webstart.base.entity.XaCmsResource;
import com.web.webstart.base.entity.XaCmsRole;
import com.web.webstart.base.entity.XaCmsUser;
import com.web.webstart.base.entity.XaCmsUserRole;
import com.web.webstart.base.repository.XaCmsResourceRepository;
import com.web.webstart.base.repository.XaCmsRoleRepository;
import com.web.webstart.base.repository.XaCmsUserRepository;
import com.web.webstart.base.repository.XaCmsUserRoleRepository;
import com.web.webstart.base.service.XaCmsUserService;
import com.web.webstart.base.util.DynamicSpecifications;
import com.web.webstart.base.util.MD5Util;
import com.web.webstart.base.util.SearchFilter;
import com.web.webstart.base.util.XaUtil;
import com.web.webstart.base.vo.FirstLevelMenu;
import com.web.webstart.base.vo.MenuData;
import com.web.webstart.base.vo.SecondLevelMenu;


/**
 * @Title: XaCmsUserServiceImpl.java
 * @Package com.web.shengmilu.business.service.impl
 * @Description: 后台用户服务类
 * @author eason.zt
 * @date 2014年8月2日 上午9:46:30
 * @version V1.0
 */
@Service("xaCmsUserService")
public class XaCmsUserServiceImpl extends BaseService<XaCmsUser> implements XaCmsUserService {

	private static final Logger log=Logger.getLogger(XaCmsUserServiceImpl.class);
	
	@Autowired
	private XaCmsUserRepository xaCmsUserRepository;
	
	@Autowired
	private XaCmsRoleRepository xaCmsRoleRepository;
	
	@Autowired
	private XaCmsUserRoleRepository xaCmsUserRoleRepository;
	
	@Autowired
	private XaCmsResourceRepository xaCmsResourceRepository;
	
	@Transactional(readOnly=false)
	public XaCmsUser saveCmsUser(XaCmsUser xaCmsUser,String roleIds) {
		xaCmsUser.setStatus(XaConstant.UserStatus.status_normal);
		xaCmsUser.setPassword(MD5Util.getMD5String(xaCmsUser.getPassword()));
		xaCmsUser =xaCmsUserRepository.save(xaCmsUser);
		log.info("增加一条用户记录："+xaCmsUser.getUserId());
		createUserRole(xaCmsUser.getUserId(), roleIds);
		
		return xaCmsUser;
	}

	/**
	 * @Title: createUserRole
	 * @Description: 创建用户角色关系
	 * @param userId
	 * @param roleIds    
	 */
	private void createUserRole(long userId, String roleIds) {
		String[] ids=roleIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			long roleId=Long.parseLong(ids[i]);
			XaCmsRole xcr = xaCmsRoleRepository.findOne(roleId);
			if(xcr!=null && XaConstant.RoleStatus.status_normal==xcr.getStatus()){
				XaCmsUserRole xur=new XaCmsUserRole();
				xur.setRoleId(roleId);
				xur.setUserId(userId);
				xaCmsUserRoleRepository.save(xur);
				log.info("增加一条用户角色关系记录："+xur.getId());
			}
		}
	}

	public Page<XaCmsUser> getCmsUserByCondition(
			Map<String, Object> filterParams, Pageable pageable) {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		return xaCmsUserRepository.findAll(DynamicSpecifications.bySearchFilter(filters.values(), XaCmsUser.class), pageable);
	}

	@Transactional(readOnly=false)
	public void delCmsUserByIds(String uids) {
		String[] ids=uids.split(",");
		for (int j = 0; j < ids.length; j++) {			
			XaCmsUser xaCmsUser=xaCmsUserRepository.findOne(Long.parseLong(ids[j]));
			if(xaCmsUser!=null){
				xaCmsUser.setStatus(XaConstant.UserStatus.status_delete);
				xaCmsUserRepository.save(xaCmsUser);
			}
		}
	}

	public int updateCmsUserPassword(long uid, String oldPassword,
			String newPassword) {
		XaCmsUser xaCmsUser=xaCmsUserRepository.findOne(uid);
		if(xaCmsUser==null) return -1;
		if(MD5Util.getMD5String(oldPassword).equals(xaCmsUser.getPassword())){

			xaCmsUser.setPassword(MD5Util.getMD5String(newPassword));
			xaCmsUserRepository.save(xaCmsUser);
			return 1;
		}else{			
			return 0;
		}
	}

	public XaCmsUser updateCmsUserNotPassword(XaCmsUser xaCmsUser,String rids) {
		XaCmsUser oldXaCmsUser=xaCmsUserRepository.findOne(xaCmsUser.getUserId());
		if(oldXaCmsUser!=null){
			oldXaCmsUser.setDescription(xaCmsUser.getDescription());
			oldXaCmsUser.setEmail(xaCmsUser.getEmail());
			oldXaCmsUser.setMobile(xaCmsUser.getMobile());
			oldXaCmsUser.setRealName(xaCmsUser.getRealName());
			xaCmsUserRepository.save(oldXaCmsUser);
			log.info("修改用户："+oldXaCmsUser.getUserId()+"成功");
			//删除原来的角色关系
			List<XaCmsUserRole> xcurList=xaCmsUserRoleRepository.findXacmsUserRoleByUserId(oldXaCmsUser.getUserId());
			
			System.out.println(JSON.toJSONString(xcurList));
			
			if(xcurList.size()>0) xaCmsUserRoleRepository.delete(xcurList);
			log.info("删除用户角色关系："+oldXaCmsUser.getUserId()+"成功");
			createUserRole(xaCmsUser.getUserId(), rids);
		}
		return oldXaCmsUser;
	}

	public List<XaCmsRole> getMyXaCmsRoleListByUserId(long uid) {
		return null;
	}

	public List<XaCmsResource> getMyXaCmsResourceListByUserId(long uid) {
		// TODO Auto-generated method stub
		return null;
	}

	public int saveUserRoleList(int uid, Integer[] roleIds) {
		return 0;
	}

	public Map<String, Object> getUserAndRole(long userId) {
		Map<String, Object> map=new HashMap<String, Object>();
		if(userId==0){
			map.put("user", null);
			map.put("userRole", null);
		}else{
			XaCmsUser xcu=xaCmsUserRepository.findOne(userId);
			map.put("user", xcu);
			List<XaCmsUserRole> xcurList=xaCmsUserRoleRepository.findXacmsUserRoleByUserId(userId);
			map.put("userRole", xcurList);
		}
		List<XaCmsRole> xcrList= xaCmsRoleRepository.findAllXaCmsRole(XaConstant.RoleStatus.status_normal);
		map.put("role", xcrList);
		return map;
	}

	public XaCmsUser findXaCmsUserByUserName(String userName, int status) {
		XaCmsUser user=xaCmsUserRepository.findByUserName(userName,status);
		return user;
	}

	public MenuData createUserResourceByUserId(long userId) {
		//获取该用户拥有的资源信息
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT r.* ");
		sql.append("FROM tb_cms_user_role ur INNER JOIN tb_cms_role_resource rr on ur.role_id=rr.role_id ");
		sql.append("INNER JOIN tb_cms_resource r on r.resource_id=rr.resource_id ");
		sql.append("where ur.user_id=").append(userId).append(" and r.show_type=").append(XaConstant.ResourceShowType.menu_level);
		sql.append(" GROUP BY r.resource_id");
		List<Object[]> list = this.query(sql.toString(), null, null);
		List<FirstLevelMenu> fisrstList=new ArrayList<FirstLevelMenu>();
		List<Long> resourceIdList = new ArrayList<Long>();
		for(Object[] obj : list){
			FirstLevelMenu flmenu=new FirstLevelMenu();
			//如果父资源为空，则加入到一级菜单中
			if(XaUtil.isEmpty(obj[2])){
				flmenu.setId((BigInteger)obj[0]+"");
				flmenu.setText((String)obj[3]);
				flmenu.setIcon("");
				flmenu.setUrl((String)obj[4]);
				fisrstList.add(flmenu);
			}
			//存放该用户拥有的所有资源ID，用于和下面的二级菜单进行比较
			resourceIdList.add(((BigInteger)obj[0]).longValue());
		}
		for(FirstLevelMenu levMenu : fisrstList){
			List<SecondLevelMenu> slMenuList=new ArrayList<SecondLevelMenu>();
			//查找该一级菜单下的所有可用的二级菜单
			List<XaCmsResource>  sencondXaCmsResourceList=xaCmsResourceRepository.findResourceByParentIdAndStatus(Long.valueOf(levMenu.getId()), XaConstant.ResourcesStatus.status_normal);
			for (XaCmsResource xaCmsResource2 : sencondXaCmsResourceList) {
				if(resourceIdList.contains(xaCmsResource2.getResourceId())){
					//和拥有的资源ID比较，如果包含，将该资源转换成二级菜单
					SecondLevelMenu slmenu=new SecondLevelMenu(xaCmsResource2.getResourceId()+"",xaCmsResource2.getResourceName(),xaCmsResource2.getResourceUrl());
					slMenuList.add(slmenu);
				}
			}
			levMenu.setList(slMenuList);
		}
		
		//要返回的菜单对象
		MenuData menuData=new MenuData();
		menuData.setTitle("导航栏");
		menuData.setItems(fisrstList);
		return menuData;
	}

	
}

