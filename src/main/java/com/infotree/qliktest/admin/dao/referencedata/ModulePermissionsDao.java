package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.ModulePermissions;

public interface ModulePermissionsDao extends QTAdminDao<ModulePermissions> {
	
	ModulePermissions findByModuleIdAndPermissionId(Integer moduleId,Integer permissionId);
	
	List<ModulePermissions> findByModuleId(Integer moduleId);
	
	int getPermissionsCountByModuleId(Integer moduleId);
	
	List<ModulePermissions> findByPermissionsId(Integer permId);
	
	
	
	
	
	int deleteByModuleId(Integer moduleId);
}
