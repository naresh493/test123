package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.RolePermissions;

public interface RolePermissionsDao extends QTAdminDao<RolePermissions> {

	List<RolePermissions> findByRoleId(Integer roleId);
	
	List<RolePermissions> findByPermissionId(Integer permissionId);

	Integer getPermissionsCountByRole(Integer roleId);
	
	
	
	List<RolePermissions> findByRoleIdAndPermissionId(Integer roleId,Integer permissionId);
	
	int deleteByRoleId(Integer roleId);
	
	
}
