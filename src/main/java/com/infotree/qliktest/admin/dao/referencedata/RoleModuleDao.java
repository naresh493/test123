package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.RoleModule;

public interface RoleModuleDao extends QTAdminDao<RoleModule> {
	
	List<RoleModule> findByModuleId(Integer moduleId);

	int getRoleCountByModuleId(Integer moduleId);

	int deleteByModuleId(Integer moduleId);
	
	
	
}
