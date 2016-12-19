
package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Permissions;


/**
 * Contract for a type-safe implementation of AbstractQTAdminDao that adds additional search functionality.
 */
public interface PermissionsDao extends QTAdminDao<Permissions> {

	Permissions getById(String userId);
	
	Permissions findByName(String permissionName);
	
	List<Permissions> getPermissionsByRoleId(List<Integer> roleIds);
	
	List<Permissions> getPermissionsNotInRoleId(List<Integer> roleIds);
	
	void save(List<Permissions> permissionsList);
	
	
	int updatePermissions(Permissions permission);
	
	List<Permissions> getpermissionsByRoleId(Integer roleId);
	
	
	List<Permissions> getPermissionsNotIn(Integer id);
	
	Permissions getPermissionsByNameAndId(String name,Integer id);
	
	List<Permissions> getOrderBydesc();
	
	List<Permissions> searchByPettern(String name);
	
	List<Permissions> getActivePermissions();
	
	List<Permissions> getPermissionsNotInModule(Integer moduleId);
	
	List<Permissions> getByModuleId(Integer moduleId);
	
	int getMaxId();
	
	List<Permissions> getByroleId(Integer roleid);

	List<Permissions> getNotByRoleId(Integer roleid);
	
	List<Permissions> getPermissionsByRoleId(Integer roleId);
	
	List<Permissions> getByPage(Long page);
	
	List<Permissions> getByPageEdit(Long page);
	
	List<Permissions> getStartingRecords();
	
	List<Permissions> getPermissionsByPageAndCount(int page,int limitsPerPage);
	
	int permissionsCount();
	
	int permissionCountByPattern(String pattern);
	
	List<Permissions> getByPageByPattern(String pattern,Long page);
	
	List<Permissions> getPermissionsByPageAndCountByPattern(String pattern,int page,int limitsPerPage);
}
