package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.ModuleConstraints;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface ModuleConstraintsService extends
		QTAdminService<ModuleConstraints> {

	int getCountOfPackages(Integer constraintId);
	
	int getCountOfConstraints(Integer moduleId);
	
	int deleteByModuleId(Integer moduleId);
	
	List<ModuleConstraints> findByConstraintId(Integer constraintId);
	
	List<ModuleConstraints> findByModuleId(Integer moduleId);
}
