package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.RoleModule;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface RoleModuleService extends QTAdminService<RoleModule> {

	List<RoleModule> findByModuleId(Integer moduleId);
	
	int getRoleCountByModuleId(Integer moduleId);
	
	
	
	int deleteByModuleId(Integer moduleId);
	
	
	
}
