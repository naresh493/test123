package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.ModulePermissions;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface ModulePermissionsService extends QTAdminService<ModulePermissions> {
	
	ModulePermissions findByModuleIdAndPermissionId(Integer moduleId,Integer permissionId);
	
	List<ModulePermissions> findByModuleId(Integer moduleId);
	
	int getPermissionsCountByModuleId(Integer moduleId);
	
	List<ModulePermissions> findByPermissionsId(Integer permId);
	
	
	int deleteByModuleId(Integer moduleId);
}
