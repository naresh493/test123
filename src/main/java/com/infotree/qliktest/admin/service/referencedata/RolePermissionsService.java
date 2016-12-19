package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.RolePermissions;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface RolePermissionsService extends QTAdminService<RolePermissions> {
	
	List<RolePermissions> findByRoleId(Integer roleId);
	
	List<RolePermissions> findByPermissionId(Integer permissionId);

	
	
	Integer getPermissionsCountByRole(Integer roleId);
	
	
	
	List<RolePermissions> findByRoleIdAndPermissionId(Integer roleId,Integer permissionId);
	
	int deleteByRoleId(Integer roleId);
	
	

}
