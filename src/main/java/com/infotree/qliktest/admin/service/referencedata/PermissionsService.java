package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.Permissions;
import com.infotree.qliktest.admin.service.QTAdminService;


public interface PermissionsService extends QTAdminService<Permissions>{

	List<Permissions> getPermissionsByRoleId(List<Integer> roldIds);
	
	List<Permissions> getpermissionsByRoelId(Integer roleId);
	
	List<Permissions> getPermissionsNotInRoleId(List<Integer> roldIds);
	
	Permissions findByName(String name);
	
	void save(List<Permissions> permissionsList);
	
	List<Permissions> getByRoleId(Integer roleid);

	List<Permissions> getNotByRoleId(Integer roleid);
	
	List<Permissions> getPermissionsByRoleId(Integer roleId);
	
	
	int getMaxId();
	/**
	 * Sets the collection of system functions that users asigned to the given user profile have access to.
	 * @return the updated instance of UserProfile that has been persisted to the database
	 */
	//UserProfile updatePermissions(Integer userProfileId, Set<Function> permissions);
	
	List<Permissions> getPermissionsNotIn(Integer id);
	
	Permissions getPermissionsByNameAndId(String name,Integer id);
	
	int updatePermissions(Permissions permission);
	
	List<Permissions> getOrderBydesc();
	
	List<Permissions> searchByPettern(String name);
	
	List<Permissions> getPermissionsNotInModule(Integer moduleId);
	
	List<Permissions> getActivePermissions();
	
	List<Permissions> getByModuleId(Integer moduleId);
	
	List<Permissions> getByPage(Long page);
	
	List<Permissions> getByPageEdit(Long page);
	
	List<Permissions> getStartingRecords();
	
	int permissionsCount();
	
	int permissionsCountByPattern(String pattern);
	
	List<Permissions> getPermissionsByPageAndCount(int page,int limitsPerPage);
	
	List<Permissions> getByPageByPattern(String pattern,Long page);
	
	List<Permissions> getPermissionsByPageAndCountByPattern(String pattern,int page,int limitsPerPage);
}
