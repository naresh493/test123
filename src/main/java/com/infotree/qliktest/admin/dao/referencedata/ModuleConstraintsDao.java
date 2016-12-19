package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.ModuleConstraints;

public interface ModuleConstraintsDao extends QTAdminDao<ModuleConstraints> {

	ModuleConstraints save(ModuleConstraints mc);
	
	int getCountOfPackages(Integer constraintId);
	
	int getCountOfConstraints(Integer moduleId);
	
	List<ModuleConstraints> findByConstraintId(Integer constraintId);
	
	List<ModuleConstraints> findByModuleId(Integer moduleId);
	
	int deleteByModuleId(Integer moduleId);
}
